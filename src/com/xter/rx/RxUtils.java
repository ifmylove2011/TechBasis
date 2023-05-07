package com.xter.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/4/26
 * 描述:
 */
public class RxUtils {

	static class CheckObservable extends Observable<Integer>{

		private int checkCode;

		public CheckObservable(int checkCode){
			this.checkCode = checkCode;
		}

		@Override
		protected void subscribeActual(Observer<? super Integer> observer) {

		}

		private class Listener implements Disposable {
			private Observer observer;

			public Listener(Observer<? super Integer> observer){
				this.observer = observer;
			}

			@Override
			public void dispose() {

			}

			@Override
			public boolean isDisposed() {
				return false;
			}
		}
	}

	public static void main(String[] args) {
		check("a");
		check("b");
	}

	private static void check(String code){
		Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
				observableEmitter.onNext(code);
				observableEmitter.onNext(code);
			}
		}).throttleFirst(200, TimeUnit.MILLISECONDS)
				.subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Exception {
				System.out.println(s);
			}
		});
	}
}
