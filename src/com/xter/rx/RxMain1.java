package com.xter.rx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableGroupBy;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxMain1 {

	public static void main(String[] args) {

		useCreate().subscribe(new SimpleStringObserver());
		useEmpty().subscribe(new SimpleStringObserver());
		useNever().subscribe(new SimpleStringObserver());
		useError().subscribe(new SimpleStringObserver());
		useJust().subscribe(new SimpleStringObserver());
		useFromArray().subscribe(new SimpleStringObserver());
		useFromIterable().subscribe(new SimpleStringObserver());

		useAmb().subscribe(new SimpleStringObserver());
		useRepeat().subscribe(new SimpleStringObserver());
//		useRepeatWhen().subscribe(observer);
		useDefer().subscribe(new SimpleStringObserver());

		useRange().subscribe(new SimpleIntegerObserver());

		useInterval().subscribe(new SimpleLongObserver());
		useTimer().subscribe(new SimpleLongObserver());


		useBuffer().subscribe(new SimpleListStringObserver());
		useMap().subscribe(new SimpleListStringObserver());
		useFlatMap().subscribe(new SimpleStringObserver());
		useConcatMap().subscribe(new SimpleStringObserver());
		useSwitchMap().subscribe(new SimpleStringObserver());

		useScan().subscribe(new SimpleIntegerObserver());
		useGroupBy().subscribe(new GroupObserver());
	}

	/**
	 * 直接产生Observable，需自行调用onNext/onComplete/onError等方法
	 *
	 * @return Observable
	 */
	public static Observable<String> useCreate() {
		return Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
				String s = "I'm RxJava2";
				observableEmitter.onNext(s);
				observableEmitter.onComplete();
			}
		});
	}

	public static Observable<String> useEmpty() {
		return Observable.empty();
	}

	public static Observable<String> useNever() {
		return Observable.never();
	}

	public static Observable<String> useError() {
		return Observable.error(new IOException("This is a exception"));
	}

	public static Observable<String> useJust() {
		String s = "Just so so";
		return Observable.just(s);
	}

	public static Observable<String> useFromArray() {
		String[] strings = {"aa", "bb", "cc"};
		return Observable.fromArray(strings);
	}

	public static Observable<String> useFromIterable() {
		String[] strings = {"aa12", "bb34", "cc56"};
		List<String> listString = Arrays.asList(strings);
		return Observable.fromIterable(listString);
	}

	public static Observable<String> useAmb() {
		String[] strings2 = {"dd", "ee", "ff"};
		String[] strings3 = {"ddddd", "eeeeee", "ffffff"};
		List<Observable<String>> observableList = new ArrayList<>();
		observableList.add(Observable.fromIterable(Arrays.asList(strings2)));
		observableList.add(Observable.fromIterable(Arrays.asList(strings3)));
		return Observable.amb(observableList);
	}

	public static Observable<String> useRepeat() {
		return useJust().repeat(5);
	}

	public static Observable<String> useRepeatWhen() {
		return useJust().repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
			@Override
			public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
				return objectObservable;
			}
		});
	}

	public static Observable<String> useDefer() {
		return Observable.defer(new Callable<ObservableSource<? extends String>>() {
			@Override
			public ObservableSource<? extends String> call() throws Exception {
				System.out.println("callled");
				return useJust();
			}
		});
	}

	public static Observable<Integer> useRange() {
		return Observable.range(10, 5);
	}

	public static Observable<Long> useInterval() {
		return Observable.interval(2, TimeUnit.SECONDS);
//		return Observable.intervalRange(10,4,1,1, TimeUnit.SECONDS);
	}

	public static Observable<Long> useTimer() {
		return Observable.timer(1, TimeUnit.MILLISECONDS);
	}

	public static Observable<List<String>> useBuffer() {
//		return useFromIterable().buffer(2);
		return useFromIterable().buffer(2, 1);
	}

	public static Observable<List<String>> useMap() {
		return useFromIterable().map(new Function<String, List<String>>() {
			@Override
			public List<String> apply(String s) throws Exception {
				List<String> list = new ArrayList<>();
				list.add(s);
				return list;
			}
		});
	}

	public static Observable<String> useFlatMap() {
		return useFromIterable().flatMap(new Function<String, Observable<String>>() {
			@Override
			public Observable<String> apply(String s) throws Exception {
				return Observable.just(s);
			}
		});
	}

	public static Observable<String> useConcatMap() {
		return useFromIterable().concatMap(new Function<String, Observable<String>>() {
			@Override
			public Observable<String> apply(String s) throws Exception {
				return Observable.just(s);
			}
		});
	}

	public static Observable<String> useSwitchMap() {
		return useAmb().switchMap(new Function<String, Observable<String>>() {
			@Override
			public Observable<String> apply(String s) throws Exception {
				return Observable.just(s);
			}
		});
	}

	public static Observable<Integer> useScan() {
		return useRange().scan(new BiFunction<Integer, Integer, Integer>() {
			@Override
			public Integer apply(Integer i1, Integer i2) throws Exception {
				System.out.println("i1=" + i1 + ",i2=" + i2);
				return i1 + i2;
			}
		});
	}

	public static Observable<GroupedObservable<String, Integer>> useGroupBy() {
		return useRange().groupBy(new Function<Integer, String>() {
			@Override
			public String apply(Integer integer) throws Exception {
				return integer % 2 == 0 ? "a" : "b";
			}
		});
	}

	static class SimpleStringObserver extends DisposableObserver<String> {

		@Override
		protected void onStart() {
			System.out.println("----start----");
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
			System.out.println("----end----");
		}
	}

	static class SimpleIntegerObserver extends DisposableObserver<Integer> {

		@Override
		protected void onStart() {
			System.out.println("----start----");
		}

		@Override
		public void onNext(Integer s) {
			System.out.println(s);
		}

		@Override
		public void onError(Throwable throwable) {
			throwable.printStackTrace();
		}

		@Override
		public void onComplete() {
			System.out.println("----end----");
		}
	}

	static class SimpleLongObserver extends DisposableObserver<Long> {

		@Override
		protected void onStart() {
			System.out.println("----start----");
		}

		@Override
		public void onNext(Long s) {
			System.out.println(s);
		}

		@Override
		public void onError(Throwable throwable) {
			throwable.printStackTrace();
		}

		@Override
		public void onComplete() {
			System.out.println("----end----");
		}
	}

	static class SimpleListStringObserver extends DisposableObserver<List<String>> {

		@Override
		protected void onStart() {
			System.out.println("----start----");
		}

		@Override
		public void onNext(List<String> list) {
			System.out.println(list);
		}

		@Override
		public void onError(Throwable throwable) {
			throwable.printStackTrace();
		}

		@Override
		public void onComplete() {
			System.out.println("----end----");
		}
	}

	static class GroupObserver extends DisposableObserver<GroupedObservable<String, Integer>> {
		@Override
		protected void onStart() {
			System.out.println("----start----");
		}

		@Override
		public void onNext(GroupedObservable<String, Integer> stringIntegerGroupedObservable) {
			System.out.println(stringIntegerGroupedObservable.getKey());
			stringIntegerGroupedObservable.subscribe(new SimpleIntegerObserver());
		}

		@Override
		public void onError(Throwable throwable) {
			throwable.printStackTrace();
		}

		@Override
		public void onComplete() {
			System.out.println("----end----");
		}
	}
}
