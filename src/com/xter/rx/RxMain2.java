package com.xter.rx;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxMain2 {

	public static void main(String[] args) {
//		useCreate();
//		useJust();
//		useRepeat();
//		useRetry();

//		useInterval();

//		useMap();

//		useConcat();

//		useFilter();

//		useTake();

		useThrottle();

		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void useCreate() {
		Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
				String s = "I'm RxJava";
				observableEmitter.onNext(s);
				observableEmitter.onComplete();
			}
		}).subscribe(new DisposableObserver<String>() {

			@Override
			protected void onStart() {
				System.out.println("----start----");
			}

			@Override
			public void onNext(String s) {
				System.out.println("onNext: " + s);
			}

			@Override
			public void onError(Throwable throwable) {
				throwable.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("----end----");
			}
		});
	}

	public static void useJust() {
//		Observable.just("a");
//		Observable.just("a","b");
//		String[] strings = {"a","b","c"};
//		Observable.fromArray(1, 2, 3, 4);
	}

	public static void useRepeat() {
//		Observable.fromArray(1, 2, 3, 4).repeat(5).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});
//		Observable.fromArray(1, 2, 3, 4).repeatUntil(new BooleanSupplier() {
//			@Override
//			public boolean getAsBoolean() throws Exception {
//				//自定判断条件，为true即可停止
//				return true;
//			}
//		}).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});

		Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
				observableEmitter.onNext("a");
				observableEmitter.onNext("b");
				observableEmitter.onComplete();
			}
		}).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
			@Override
			public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
				System.out.println(objectObservable.toString());
				return objectObservable.timeInterval(TimeUnit.SECONDS);
			}
		}).subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Exception {
				System.out.println(s);
			}
		});
	}

	public static void useRetry() {
		Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
				for (int i = 0; i < 10; i++) {
					observableEmitter.onNext(i + "");
					if (i == 8) {
						observableEmitter.onError(new Exception("It's my fault"));
					}
				}
				observableEmitter.onComplete();
			}
		});

		observable.retry();
		observable.retry(3);
		observable.retryUntil(new BooleanSupplier() {
			@Override
			public boolean getAsBoolean() throws Exception {
				//自定判断条件，为true则停止重复
				return false;
			}
		});

		observable.retry().subscribe(new Observer<String>() {
			@Override
			public void onSubscribe(Disposable disposable) {

			}

			@Override
			public void onNext(String s) {
				System.out.println(s);
			}

			@Override
			public void onError(Throwable throwable) {
				throwable.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("---end----");
			}
		});
	}

	public static void useInterval() {
//		Observable.intervalRange(10, 5, 2, 1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
//			@Override
//			public void onSubscribe(Disposable disposable) {
//
//			}
//
//			@Override
//			public void onNext(Long aLong) {
//				System.out.println(System.currentTimeMillis() + "," + aLong);
//			}
//
//			@Override
//			public void onError(Throwable throwable) {
//				throwable.printStackTrace();
//			}
//
//			@Override
//			public void onComplete() {
//				System.out.println("----end----");
//			}
//		});
//
//		Observable.interval(1, 3, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
//			@Override
//			public void accept(Long aLong) throws Exception {
//				System.out.println(aLong);
//			}
//		});

//		Observable.timer(3, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
//			@Override
//			public void onSubscribe(Disposable disposable) {
//
//			}
//
//			@Override
//			public void onNext(Long aLong) {
//				System.out.println(System.currentTimeMillis() + "," + aLong);
//			}
//
//			@Override
//			public void onError(Throwable throwable) {
//
//			}
//
//			@Override
//			public void onComplete() {
//				System.out.println("--------");
//			}
//		});

		Observable.just("1", "2", "3").delay(3, TimeUnit.SECONDS).subscribe(new Observer<String>() {
			@Override
			public void onSubscribe(Disposable disposable) {

			}

			@Override
			public void onNext(String s) {
				System.out.println(s);
			}

			@Override
			public void onError(Throwable throwable) {

			}

			@Override
			public void onComplete() {
				System.out.println("-----");
			}
		});
	}

	public static void useMap() {
//		Observable.just("1", "2", "3").map(new Function<String, Integer>() {
//			@Override
//			public Integer apply(String s) throws Exception {
//				return Integer.valueOf(s) * 10;
//			}
//		}).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});

//		Observable.just("123", "456").flatMap(new Function<String, ObservableSource<Integer>>() {
//			@Override
//			public ObservableSource<Integer> apply(String s) throws Exception {
//				System.out.println("apply:" + s);
//				String[] strings = s.split("");
//				return Observable.fromArray(strings).map(new Function<String, Integer>() {
//					@Override
//					public Integer apply(String strings) throws Exception {
//						return Integer.valueOf(strings) * 100;
//					}
//				});
//			}
//		}).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});

		Observable.just("123", "456").concatMap(new Function<String, ObservableSource<Integer>>() {
			@Override
			public ObservableSource<Integer> apply(String s) throws Exception {
				return null;
			}
		});
	}

	public static void useConcat() {
//		Observable.merge(Observable.just("1", "2", "3").delay(100, TimeUnit.MILLISECONDS), Observable.just("11", "22", "33")).subscribe(new DisposableObserver<String>() {
//			@Override
//			public void onNext(String s) {
//				System.out.println(s);
//			}
//
//			@Override
//			public void onError(Throwable throwable) {
//
//			}
//
//			@Override
//			public void onComplete() {
//				System.out.println("--------");
//			}
//		});
//		Observable.concat(Observable.just("1", "2", "3").delay(3, TimeUnit.SECONDS), Observable.just("11", "22", "33")).subscribe(new DisposableObserver<String>() {
//
//			@Override
//			protected void onStart() {
//				System.out.println(System.currentTimeMillis());
//			}
//
//			@Override
//			public void onNext(String s) {
//				System.out.println(System.currentTimeMillis());
//				System.out.println(s);
//			}
//
//			@Override
//			public void onError(Throwable throwable) {
//
//			}
//
//			@Override
//			public void onComplete() {
//				System.out.println("--------");
//			}
//		});

		Observable.zip(Observable.just("1", "2", "3"), Observable.just(10, 100, 1000), new BiFunction<String, Integer, Integer>() {
			@Override
			public Integer apply(String s, Integer integer) throws Exception {
				return Integer.valueOf(s) * integer;
			}
		}).subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				System.out.println(integer);
			}
		});
	}

	public static void useFilter() {
//		Observable.just(1, 2, 3, 4).scan(new BiFunction<Integer, Integer, Integer>() {
//			@Override
//			public Integer apply(Integer integer1, Integer integer2) throws Exception {
//				System.out.println("i1=" + integer1 + ",i2=" + integer2);
//				return integer1 + integer2;
//			}
//		}).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});

//		Observable.just(1, 2, 3, 4).filter(new Predicate<Integer>() {
//			@Override
//			public boolean test(Integer integer) throws Exception {
//				return integer % 2 == 0;
//			}
//		}).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});

		Observable.just("aa", "bb", "ccc", "dd", "bb").distinct().subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Exception {
				System.out.println(s);
			}
		});

//		Observable.just("aa", "bb", "ccc", "dd", "bb").distinct(new Function<String, Integer>() {
//			@Override
//			public Integer apply(String s) throws Exception {
//				return s.length();
//			}
//		}).subscribe(new Consumer<String>() {
//			@Override
//			public void accept(String s) throws Exception {
//				System.out.println(s);
//			}
//		});

//		Observable.just("aa", "bb", "ccc", "dd", "bb").distinctUntilChanged().subscribe(new Consumer<String>() {
//			@Override
//			public void accept(String s) throws Exception {
//				System.out.println(s);
//			}
//		});

//		Observable.just("aa", "bb", "ccc", "dd", "bb").distinctUntilChanged(new Function<String, Integer>() {
//			@Override
//			public Integer apply(String s) throws Exception {
//				return s.length();
//			}
//		}).subscribe(new Consumer<String>() {
//			@Override
//			public void accept(String s) throws Exception {
//				System.out.println(s);
//			}
//		});

//		Observable.just("aa", "bb", "ccc", "dd", "bb").distinctUntilChanged(new BiPredicate<String, String>() {
//			@Override
//			public boolean test(String s1, String s2) throws Exception {
//				System.out.println("s1="+s1+",s2="+s2);
//				return s1.length() == s2.length();
//			}
//		}).subscribe(new Consumer<String>() {
//			@Override
//			public void accept(String s) throws Exception {
//				System.out.println(s);
//			}
//		});


	}

	public static void useTake() {
//		Observable.just(1, 2, 3, 4).take(2).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});
//
//		Observable.just(1, 2, 3, 4).takeLast(2).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});

//		Observable.intervalRange(10, 5, 0, 1, TimeUnit.SECONDS)
//				.take(3, TimeUnit.SECONDS)
//				.subscribe(new Consumer<Long>() {
//					@Override
//					public void accept(Long aLong) throws Exception {
//						System.out.println(aLong);
//					}
//				});

//		Observable.intervalRange(10, 5, 0, 1, TimeUnit.SECONDS)
//				.takeLast(3, TimeUnit.SECONDS)
//				.subscribe(new DisposableObserver<Long>() {
//					@Override
//					protected void onStart() {
//						System.out.println("---------");
//					}
//
//					@Override
//					public void onNext(Long aLong) {
//						System.out.println(System.currentTimeMillis());
//						System.out.println(aLong);
//					}
//
//					@Override
//					public void onError(Throwable throwable) {
//
//					}
//
//					@Override
//					public void onComplete() {
//						System.out.println("---------");
//					}
//				});

//		Observable.just(1, 2, 3, 4).takeUntil(new Predicate<Integer>() {
//			@Override
//			public boolean test(Integer integer) throws Exception {
//				return integer > 2;
//			}
//		}).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});

//		Observable.just(1, 2, 3, 4).takeWhile(new Predicate<Integer>() {
//			@Override
//			public boolean test(Integer integer) throws Exception {
//				return integer < 3;
//			}
//		}).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});


//		Observable.just(1, 2, 3, 4).skip(2).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});
//
//		Observable.just(1, 2, 3, 4).skipLast(2).subscribe(new Consumer<Integer>() {
//			@Override
//			public void accept(Integer integer) throws Exception {
//				System.out.println(integer);
//			}
//		});

//		Observable.intervalRange(10, 5, 0, 1, TimeUnit.SECONDS)
//				.skip(3, TimeUnit.SECONDS)
//				.subscribe(new Consumer<Long>() {
//					@Override
//					public void accept(Long aLong) throws Exception {
//						System.out.println(aLong);
//					}
//				});

//		Observable.intervalRange(10, 5, 0, 1, TimeUnit.SECONDS)
//				.skipLast(3, TimeUnit.SECONDS)
//				.subscribe(new DisposableObserver<Long>() {
//					@Override
//					protected void onStart() {
//						System.out.println("---------");
//					}
//
//					@Override
//					public void onNext(Long aLong) {
//						System.out.println(System.currentTimeMillis());
//						System.out.println(aLong);
//					}
//
//					@Override
//					public void onError(Throwable throwable) {
//
//					}
//
//					@Override
//					public void onComplete() {
//						System.out.println("---------");
//					}
//				});


		Observable.just(1, 2, 3, 4).skipWhile(new Predicate<Integer>() {
			@Override
			public boolean test(Integer integer) throws Exception {
				return integer < 3;
			}
		}).subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				System.out.println(integer);
			}
		});
	}

	public static void useThrottle() {
//		Observable.create(new ObservableOnSubscribe<Long>() {
//			@Override
//			public void subscribe(ObservableEmitter<Long> observableEmitter) throws Exception {
//				observableEmitter.onNext(1L);
//				TimeUnit.MILLISECONDS.sleep(300);
//				observableEmitter.onNext(2L);
//				TimeUnit.MILLISECONDS.sleep(300);
//				observableEmitter.onNext(3L);
//				TimeUnit.MILLISECONDS.sleep(500);
//				observableEmitter.onNext(4L);
//				TimeUnit.MILLISECONDS.sleep(500);
//				observableEmitter.onNext(5L);
//				TimeUnit.MILLISECONDS.sleep(200);
//				observableEmitter.onNext(6L);
//				TimeUnit.MILLISECONDS.sleep(500);
//				observableEmitter.onComplete();
//			}
//		}).sample(400, TimeUnit.MILLISECONDS)
//				.subscribe(new Consumer<Long>() {
//					@Override
//					public void accept(Long aLong) throws Exception {
//						System.out.println(aLong);
//					}
//				});
//
//		Observable.intervalRange(10,10,100,300,TimeUnit.MILLISECONDS)
//				.throttleLast(400,TimeUnit.MILLISECONDS)
//				.subscribe(new Consumer<Long>() {
//					@Override
//					public void accept(Long aLong) throws Exception {
//						System.out.println(aLong);
//					}
//				});

//		Observable.create(new ObservableOnSubscribe<Long>() {
//			@Override
//			public void subscribe(ObservableEmitter<Long> observableEmitter) throws Exception {
//				observableEmitter.onNext(1L);
//				TimeUnit.MILLISECONDS.sleep(300);
//				observableEmitter.onNext(2L);
//				TimeUnit.MILLISECONDS.sleep(300);
//				observableEmitter.onNext(3L);
//				TimeUnit.MILLISECONDS.sleep(500);
//				observableEmitter.onNext(4L);
//				TimeUnit.MILLISECONDS.sleep(500);
//				observableEmitter.onNext(5L);
//				TimeUnit.MILLISECONDS.sleep(200);
//				observableEmitter.onNext(6L);
//				TimeUnit.MILLISECONDS.sleep(500);
//				observableEmitter.onComplete();
//			}
//		}).debounce(400, TimeUnit.MILLISECONDS)
//				.subscribe(new Consumer<Long>() {
//					@Override
//					public void accept(Long aLong) throws Exception {
//						System.out.println(aLong);
//					}
//				});

//		Observable.intervalRange(10, 10, 100, 300, TimeUnit.MILLISECONDS)
//				.throttleWithTimeout(200, TimeUnit.MILLISECONDS)
//				.subscribe(new Consumer<Long>() {
//					@Override
//					public void accept(Long aLong) throws Exception {
//						System.out.println(aLong);
//					}
//				});

//		Observable.intervalRange(10, 10, 100, 300, TimeUnit.MILLISECONDS)
//				.throttleFirst(400, TimeUnit.MILLISECONDS)
//				.subscribe(new Consumer<Long>() {
//					@Override
//					public void accept(Long aLong) throws Exception {
//						System.out.println(aLong);
//					}
//				});

		Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
				TimeUnit.MILLISECONDS.sleep(200);
				for (int i = 1; i < 7; i++) {
					System.out.println(simpleTime() + " : emit->" + i);
					observableEmitter.onNext(i);
					TimeUnit.MILLISECONDS.sleep(300);
				}
				observableEmitter.onComplete();
			}
		}).throttleFirst(700, TimeUnit.MILLISECONDS)
				.subscribe(new Consumer<Integer>() {
					@Override
					public void accept(Integer num) throws Exception {
						System.out.println(simpleTime() + " : accept<-" + num);
					}
				});

//		Observable<String> source = Observable.create(emitter -> {
//			System.out.println(simpleTime()+" : emit->A");
//			emitter.onNext("A");
//
//			Thread.sleep(1_500);
//			System.out.println(simpleTime()+" : emit->B");
//			emitter.onNext("B");
//
//			Thread.sleep(500);
//			System.out.println(simpleTime()+" : emit->C");
//			emitter.onNext("C");
//
//			Thread.sleep(250);
//			System.out.println(simpleTime()+" : emit->D");
//			emitter.onNext("D");
//
//			Thread.sleep(2_000);
//			System.out.println(simpleTime()+" : emit->E");
//			emitter.onNext("E");
//			emitter.onComplete();
//		});
//
//		source.subscribeOn(Schedulers.io())
//				.debounce(1, TimeUnit.SECONDS)
//				.blockingSubscribe(
//						item -> System.out.println(simpleTime()+" : accept<-" + item),
//						Throwable::printStackTrace,
//						() -> System.out.println("onComplete"));
	}

	private static long simpleTime() {
		long mills = System.currentTimeMillis();
		double result = mills / 100;
		long t = Math.round(result) * 100;
		return t;
	}
}
