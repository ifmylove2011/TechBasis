package com.xter.algorithm.exercise;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/4
 * 描述:实现一个排除系统，让每个进入队的用户都能牛二民在队列中的所处的位置和变化，队列可能随时有人加入和退出；
 * 当有人退出影响到用户的位置排名时，需要及时反馈到用户。
 */
public class QueueUp {
	public static void main(String[] args) {
		UserQueue userQueue = new UserQueue();
		for (int i = 97; i < 101; i++) {
			User user = new User(i, String.valueOf((char) i));
			userQueue.join(user);
		}
		System.out.println(userQueue);
		userQueue.leave();
		System.out.println("队列头用户离开后：");
		System.out.println(userQueue);

		for (int i = 101; i < 103; i++) {
			User user = new User(i, String.valueOf((char) i));
			userQueue.join(user);
		}
		System.out.println("又来了2个用户：");
		System.out.println(userQueue);
		userQueue.leave(new User(100, String.valueOf((char) 100)));
		System.out.println("id为100的用户离开后：");
		System.out.println(userQueue);
		System.out.println("id为103的用户插队插到第2个去啦：");
		userQueue.join(2, new User(103, String.valueOf((char) 103)));
		System.out.println(userQueue);
	}

	static class UserQueue {

		LinkedList<User> mQueue;

		public UserQueue() {
			mQueue = new LinkedList<>();
		}

		public void join(User user) {
			user.sn = mQueue.size() + 1;
			mQueue.offer(user);
		}

		public void join(int sn, User user) {
			if (sn <= 0 || sn > mQueue.size() + 1) {
				System.out.println("无法插队");
				return;
			}
			user.sn = sn;
			mQueue.add(sn - 1, user);
			Iterator<User> iterator = mQueue.descendingIterator();
			while (iterator.hasNext()) {
				User u = iterator.next();
				if (u.equals(user)) {
					break;
				}
				u.sn = ++u.sn;
			}
		}

		public void leave() {
			mQueue.poll();
			for (User u : mQueue) {
				u.sn = --u.sn;
			}
		}

		public void leave(User user) {
			Iterator<User> iterator = mQueue.descendingIterator();
			while (iterator.hasNext()) {
				User u = iterator.next();
				if (u.equals(user)) {
					mQueue.remove(user);
					break;
				}
				u.sn = --u.sn;
			}
		}

		@Override
		public String toString() {
			return "UserQueue{" +
					"mQueue=" + mQueue +
					'}';
		}
	}

	static class User {
		int id;
		String name;
		int sn;

		public User(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			User user = (User) o;
			return id == user.id;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}

		@Override
		public String toString() {
			return "User{" +
					"id=" + id +
					", name='" + name + '\'' +
					", sn=" + sn +
					'}';
		}
	}
}
