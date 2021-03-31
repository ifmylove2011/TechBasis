package com.xter.map;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class TreeExMap<K,V> extends TreeMap<K,V> {

	private final Comparator<? super K> comparatorKey;
	private final Comparator<? super V> comparatorValue;

	public TreeExMap() {
		comparatorKey = null;
		comparatorValue = null;
	}

	private static final boolean RED   = false;
	private static final boolean BLACK = true;

	private transient Entry<K,V> root;
	private transient EntrySet entrySet;
	/**
	 * The number of entries in the tree
	 */
	private transient int size = 0;

	/**
	 * The number of structural modifications to the tree.
	 */
	private transient int modCount = 0;

	public V put(K key, V value) {
		Entry<K,V> t = root;
		if (t == null) {
//			compare(key, key); // type (and possibly null) check

			root = new Entry<>(key, value, null);
			size = 1;
			modCount++;
			return null;
		}
		int cmp;
		Entry<K,V> parent;
		// split comparator and comparable paths
		Comparator<? super V> cpr = comparatorValue;
		if (cpr != null) {
			do {
				parent = t;
				cmp = cpr.compare(value, t.value);
				if (cmp < 0)
					t = t.left;
				else if (cmp > 0)
					t = t.right;
				else
					return t.setValue(value);
			} while (t != null);
		}
		else {
			if (key == null)
				throw new NullPointerException();
			@SuppressWarnings("unchecked")
			Comparable<? super V> v = (Comparable<? super V>) value;
			do {
				parent = t;
				cmp = v.compareTo(t.value);
				if (cmp < 0)
					t = t.left;
				else if (cmp > 0)
					t = t.right;
				else
					return t.setValue(value);
			} while (t != null);
		}
		Entry<K,V> e = new Entry<>(key, value, parent);
		if (cmp < 0)
			parent.left = e;
		else
			parent.right = e;
		fixAfterInsertion(e);
		size++;
		modCount++;
		return null;
	}

	/**
	 * Returns the first Entry in the TreeMap (according to the TreeMap's
	 * key-sort function).  Returns null if the TreeMap is empty.
	 */
	final Entry<K,V> getFirstEntry() {
		Entry<K,V> p = root;
		if (p != null)
			while (p.left != null)
				p = p.left;
		return p;
	}

	final Entry<K,V> getLastEntry() {
		Entry<K,V> p = root;
		if (p != null)
			while (p.right != null)
				p = p.right;
		return p;
	}

	public Set<Map.Entry<K,V>> entrySet() {
		EntrySet es = entrySet;
		return (es != null) ? es : (entrySet = new EntrySet());
	}

	class EntrySet extends AbstractSet<Map.Entry<K,V>> {
		public Iterator<Map.Entry<K,V>> iterator() {
			return new EntryIterator(getFirstEntry());
		}

		public int size() {
			return TreeExMap.this.size();
		}

		public void clear() {
			TreeExMap.this.clear();
		}

	}

	public int size() {
		return size;
	}

	abstract class PrivateEntryIterator<T> implements Iterator<T> {
		Entry<K,V> next;
		Entry<K,V> lastReturned;
		int expectedModCount;

		PrivateEntryIterator(Entry<K,V> first) {
			expectedModCount = modCount;
			lastReturned = null;
			next = first;
		}

		public final boolean hasNext() {
			return next != null;
		}

		final Entry<K,V> nextEntry() {
			Entry<K,V> e = next;
			if (e == null)
				throw new NoSuchElementException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			next = successor(e);
			lastReturned = e;
			return e;
		}

		final Entry<K,V> prevEntry() {
			Entry<K,V> e = next;
			if (e == null)
				throw new NoSuchElementException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			next = predecessor(e);
			lastReturned = e;
			return e;
		}

		public void remove() {
			if (lastReturned == null)
				throw new IllegalStateException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			// deleted entries are replaced by their successors
			if (lastReturned.left != null && lastReturned.right != null)
				next = lastReturned;
			deleteEntry(lastReturned);
			expectedModCount = modCount;
			lastReturned = null;
		}
	}

	final class EntryIterator extends PrivateEntryIterator<Map.Entry<K,V>> {
		EntryIterator(Entry<K,V> first) {
			super(first);
		}
		public Map.Entry<K,V> next() {
			return nextEntry();
		}
	}

	/**
	 * Delete node p, and then rebalance the tree.
	 */
	private void deleteEntry(Entry<K,V> p) {
		modCount++;
		size--;

		// If strictly internal, copy successor's element to p and then make p
		// point to successor.
		if (p.left != null && p.right != null) {
			Entry<K,V> s = successor(p);
			p.key = s.key;
			p.value = s.value;
			p = s;
		} // p has 2 children

		// Start fixup at replacement node, if it exists.
		Entry<K,V> replacement = (p.left != null ? p.left : p.right);

		if (replacement != null) {
			// Link replacement to parent
			replacement.parent = p.parent;
			if (p.parent == null)
				root = replacement;
			else if (p == p.parent.left)
				p.parent.left  = replacement;
			else
				p.parent.right = replacement;

			// Null out links so they are OK to use by fixAfterDeletion.
			p.left = p.right = p.parent = null;

			// Fix replacement
			if (p.color == BLACK)
				fixAfterDeletion(replacement);
		} else if (p.parent == null) { // return if we are the only node.
			root = null;
		} else { //  No children. Use self as phantom replacement and unlink.
			if (p.color == BLACK)
				fixAfterDeletion(p);

			if (p.parent != null) {
				if (p == p.parent.left)
					p.parent.left = null;
				else if (p == p.parent.right)
					p.parent.right = null;
				p.parent = null;
			}
		}
	}

	/**
	 * Returns the successor of the specified Entry, or null if no such.
	 */
	static <K,V> Entry<K,V> successor(Entry<K,V> t) {
		if (t == null)
			return null;
		else if (t.right != null) {
			Entry<K,V> p = t.right;
			while (p.left != null)
				p = p.left;
			return p;
		} else {
			Entry<K,V> p = t.parent;
			Entry<K,V> ch = t;
			while (p != null && ch == p.right) {
				ch = p;
				p = p.parent;
			}
			return p;
		}
	}

	/**
	 * Returns the predecessor of the specified Entry, or null if no such.
	 */
	static <K,V> Entry<K,V> predecessor(Entry<K,V> t) {
		if (t == null)
			return null;
		else if (t.left != null) {
			Entry<K,V> p = t.left;
			while (p.right != null)
				p = p.right;
			return p;
		} else {
			Entry<K,V> p = t.parent;
			Entry<K,V> ch = t;
			while (p != null && ch == p.left) {
				ch = p;
				p = p.parent;
			}
			return p;
		}
	}

	private static <K,V> boolean colorOf(Entry<K,V> p) {
		return (p == null ? BLACK : p.color);
	}

	private static <K,V> Entry<K,V> parentOf(Entry<K,V> p) {
		return (p == null ? null: p.parent);
	}

	private static <K,V> void setColor(Entry<K,V> p, boolean c) {
		if (p != null)
			p.color = c;
	}

	private static <K,V> Entry<K,V> leftOf(Entry<K,V> p) {
		return (p == null) ? null: p.left;
	}

	private static <K,V> Entry<K,V> rightOf(Entry<K,V> p) {
		return (p == null) ? null: p.right;
	}

	/** From CLR */
	private void fixAfterInsertion(Entry<K,V> x) {
		x.color = RED;

		while (x != null && x != root && x.parent.color == RED) {
			if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
				Entry<K,V> y = rightOf(parentOf(parentOf(x)));
				if (colorOf(y) == RED) {
					setColor(parentOf(x), BLACK);
					setColor(y, BLACK);
					setColor(parentOf(parentOf(x)), RED);
					x = parentOf(parentOf(x));
				} else {
					if (x == rightOf(parentOf(x))) {
						x = parentOf(x);
						rotateLeft(x);
					}
					setColor(parentOf(x), BLACK);
					setColor(parentOf(parentOf(x)), RED);
					rotateRight(parentOf(parentOf(x)));
				}
			} else {
				Entry<K,V> y = leftOf(parentOf(parentOf(x)));
				if (colorOf(y) == RED) {
					setColor(parentOf(x), BLACK);
					setColor(y, BLACK);
					setColor(parentOf(parentOf(x)), RED);
					x = parentOf(parentOf(x));
				} else {
					if (x == leftOf(parentOf(x))) {
						x = parentOf(x);
						rotateRight(x);
					}
					setColor(parentOf(x), BLACK);
					setColor(parentOf(parentOf(x)), RED);
					rotateLeft(parentOf(parentOf(x)));
				}
			}
		}
		root.color = BLACK;
	}

	/** From CLR */
	private void fixAfterDeletion(Entry<K,V> x) {
		while (x != root && colorOf(x) == BLACK) {
			if (x == leftOf(parentOf(x))) {
				Entry<K,V> sib = rightOf(parentOf(x));

				if (colorOf(sib) == RED) {
					setColor(sib, BLACK);
					setColor(parentOf(x), RED);
					rotateLeft(parentOf(x));
					sib = rightOf(parentOf(x));
				}

				if (colorOf(leftOf(sib))  == BLACK &&
						colorOf(rightOf(sib)) == BLACK) {
					setColor(sib, RED);
					x = parentOf(x);
				} else {
					if (colorOf(rightOf(sib)) == BLACK) {
						setColor(leftOf(sib), BLACK);
						setColor(sib, RED);
						rotateRight(sib);
						sib = rightOf(parentOf(x));
					}
					setColor(sib, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(rightOf(sib), BLACK);
					rotateLeft(parentOf(x));
					x = root;
				}
			} else { // symmetric
				Entry<K,V> sib = leftOf(parentOf(x));

				if (colorOf(sib) == RED) {
					setColor(sib, BLACK);
					setColor(parentOf(x), RED);
					rotateRight(parentOf(x));
					sib = leftOf(parentOf(x));
				}

				if (colorOf(rightOf(sib)) == BLACK &&
						colorOf(leftOf(sib)) == BLACK) {
					setColor(sib, RED);
					x = parentOf(x);
				} else {
					if (colorOf(leftOf(sib)) == BLACK) {
						setColor(rightOf(sib), BLACK);
						setColor(sib, RED);
						rotateLeft(sib);
						sib = leftOf(parentOf(x));
					}
					setColor(sib, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(leftOf(sib), BLACK);
					rotateRight(parentOf(x));
					x = root;
				}
			}
		}

		setColor(x, BLACK);
	}

	static final class Entry<K,V> implements Map.Entry<K,V> {
		K key;
		V value;
		Entry<K,V> left;
		Entry<K,V> right;
		Entry<K,V> parent;
		boolean color = BLACK;

		/**
		 * Make a new cell with given key, value, and parent, and with
		 * {@code null} child links, and BLACK color.
		 */
		Entry(K key, V value, Entry<K,V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		/**
		 * Returns the key.
		 *
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns the value associated with the key.
		 *
		 * @return the value associated with the key
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Replaces the value currently associated with the key with the given
		 * value.
		 *
		 * @return the value associated with the key before this method was
		 *         called
		 */
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<?,?> e = (Map.Entry<?,?>)o;

			return Objects.equals(key,e.getKey()) && Objects.equals(value,e.getValue());
		}

		public int hashCode() {
			int keyHash = (key==null ? 0 : key.hashCode());
			int valueHash = (value==null ? 0 : value.hashCode());
			return keyHash ^ valueHash;
		}

		public String toString() {
			return key + "=" + value;
		}
	}
	/** From CLR */
	private void rotateLeft(Entry<K,V> p) {
		if (p != null) {
			Entry<K,V> r = p.right;
			p.right = r.left;
			if (r.left != null)
				r.left.parent = p;
			r.parent = p.parent;
			if (p.parent == null)
				root = r;
			else if (p.parent.left == p)
				p.parent.left = r;
			else
				p.parent.right = r;
			r.left = p;
			p.parent = r;
		}
	}

	/** From CLR */
	private void rotateRight(Entry<K,V> p) {
		if (p != null) {
			Entry<K,V> l = p.left;
			p.left = l.right;
			if (l.right != null) l.right.parent = p;
			l.parent = p.parent;
			if (p.parent == null)
				root = l;
			else if (p.parent.right == p)
				p.parent.right = l;
			else p.parent.left = l;
			l.right = p;
			p.parent = l;
		}
	}


}
