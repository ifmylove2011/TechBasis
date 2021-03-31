package com.xter.concurrent;

import com.xter.util.L;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureDemo {

	public static void main(String[] args) {
		Callable<ResultA> getA1 = new Callable<ResultA>() {
			@Override
			public ResultA call() throws Exception {
				TimeUnit.SECONDS.sleep(4);
				L.d(Thread.currentThread().getName());
				return new ResultA(1,"没有错1");
			}
		};

		Callable<ResultA> getA2 = new Callable<ResultA>() {
			@Override
			public ResultA call() throws Exception {
				TimeUnit.SECONDS.sleep(2);
				L.d(Thread.currentThread().getName());
				return new ResultA(2,"没有错2");
			}
		};

		FutureTask<ResultA> taskA1 = new FutureTask<>(getA1);
		new Thread(taskA1).start();
		FutureTask<ResultA> taskA2 = new FutureTask<>(getA2);
		new Thread(taskA2).start();


		if(!taskA1.isDone()||!taskA2.isDone()){
			L.d("尚未就绪");
		}

		try {
			ResultA r1 = taskA2.get();
			L.d(System.currentTimeMillis()+"");
			ResultA r2 = taskA1.get();
			L.d(System.currentTimeMillis()+"");
			System.out.println(r1.toString()+","+r2.toString());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static class ResultA{
		private int type;
		private String desc;

		public ResultA(int type, String desc) {
			this.type = type;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return "ResultA{" +
					"type=" + type +
					", desc='" + desc + '\'' +
					'}';
		}
	}
}
