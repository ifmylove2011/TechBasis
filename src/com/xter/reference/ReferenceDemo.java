package com.xter.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceDemo {

	public static void main(String[] args) {
//		checkSoftReference();
//		checkWeakReference();
		checkPhantomReference();
	}

	public static void checkPhantomReference(){
		ReferenceQueue<C> referenceQueue = new ReferenceQueue<>();
		C temp = new C();
		PhantomReference<C> cSoftReference = new PhantomReference<>(temp,referenceQueue);

		System.out.println(cSoftReference.get());

		temp = null;
		System.gc();

		System.out.println(cSoftReference.get());
	}

	public static void checkWeakReference(){
		ReferenceQueue<C> referenceQueue = new ReferenceQueue<>();
		C temp = new C();
		WeakReference<C> cSoftReference = new WeakReference<>(temp,referenceQueue);

		System.out.println(cSoftReference.get());

		temp = null;
		System.gc();

		System.out.println(cSoftReference.get());
	}

	public static void checkSoftReference(){
		ReferenceQueue<C> referenceQueue = new ReferenceQueue<>();
		C temp = new C();
		SoftReference<C> cSoftReference = new SoftReference<>(temp,referenceQueue);

		temp = null;
		System.gc();

		System.out.println(cSoftReference.get());

		byte[] hugeBlock = new byte[4*1024*885];
		System.out.println(cSoftReference.get());
	}

	static class C{
		@Override
		protected void finalize() throws Throwable {
			super.finalize();
			System.out.println("so ... alloced");
		}

		@Override
		public String toString() {
			return "It's a C :"+super.toString();
		}
	}
}
