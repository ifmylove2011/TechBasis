package com.xter.rx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class RxMain1 {

	public static void main(String[] args) {

//		useCreate().subscribe(new SimpleStringObserver());
//		useEmpty().subscribe(new SimpleStringObserver());
//		useNever().subscribe(new SimpleStringObserver());
//		useError().subscribe(new SimpleStringObserver());
//		useJust().subscribe(new SimpleStringObserver());
//		useFromArray().subscribe(new SimpleStringObserver());
//		useFromIterable().subscribe(new SimpleStringObserver());
//
//		useAmb().subscribe(new SimpleStringObserver());
//		useRepeat().subscribe(new SimpleStringObserver());
////		useRepeatWhen().subscribe(observer);
//		useDefer().subscribe(new SimpleStringObserver());
//
//		useRange().subscribe(new SimpleIntegerObserver());
//
////		useInterval().subscribe(new SimpleLongObserver());
//		useTimer().subscribe(new SimpleLongObserver());
//
//
//		useBuffer().subscribe(new SimpleListStringObserver());
//		useMap().subscribe(new SimpleListStringObserver());
//		useFlatMap().subscribe(new SimpleStringObserver());
//		useConcatMap().subscribe(new SimpleStringObserver());
//		useSwitchMap().subscribe(new SimpleStringObserver());
//		useScan().subscribe(new SimpleIntegerObserver());
//		useCast().subscribe(new SimpleStringObserver());
//
//		useGroupBy().subscribe(new GroupObserver());
//		useWindow().subscribe(new ObservableObserver());
//
//
//		useFilter().subscribe(new SimpleIntegerObserver());
//		useTake().subscribe(new SimpleIntegerObserver());
//		useTakeUntil().subscribe(new SimpleIntegerObserver());
//		useTakeWhile().subscribe(new SimpleIntegerObserver());
//		useTakeLast().subscribe(new SimpleIntegerObserver());
//
//		useFirst().subscribe(new SingleStringObserver());
//		useLast().subscribe(new SingleStringObserver());
//		useFirstElement().subscribe(new MaybeStringObserver());
//		useLastElement().subscribe(new MaybeStringObserver());
//		useElementAt().subscribe(new MaybeStringObserver());
//		useIgnore().subscribe(new CompletableObserver());
//
//		useSkip().subscribe(new SimpleIntegerObserver());
//		useSkipLast().subscribe(new SimpleIntegerObserver());
////
		useThrottleFirst().subscribe(new SimpleLongObserver());
//		useThrottleLast().subscribe(new SimpleLongObserver());
//		useThrottleWithTimeout().subscribe(new SimpleLongObserver());
//		useTimeout().subscribe(new SimpleLongObserver());

//		useDistinct().subscribe(new SimpleStringObserver());
//		useOfType().subscribe(new SimpleLongObserver());

//		useCombineLatest().subscribe(new SimpleStringObserver());

//		useMerge().subscribe(new SimpleStringObserver());
//		useZip().subscribe(new SimpleStringObserver());
//		useConcat().subscribe(new SimpleStringObserver());

//		useRetry().subscribe(new SimpleLongObserver());

//		useToList().subscribe(new SingleListStringObserver());

		//一些Observable需要延时才能看到效果
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

	/**
	 * 返回空
	 * @return Observable
	 */
	public static Observable<String> useEmpty() {
		return Observable.empty();
	}

	/**
	 * 同上，等价于empty
	 * @return Observable
	 */
	public static Observable<String> useNever() {
		return Observable.never();
	}

	/**
	 * 直接发送异常
	 * @return Observable
	 */
	public static Observable<String> useError() {
		return Observable.error(new IOException("This is a exception"));
	}

	/**
	 * 发送多个数据
	 * @return Observable
	 */
	public static Observable<String> useJust() {
//		String s = "Just so so";
//		return Observable.just(s);
		return Observable.just("a","b","c","d","e");
	}

	/**
	 * 发送多个数据，以可变长参数形式传参
	 * @return Observable
	 */
	public static Observable<String> useFromArray() {
		String[] strings = {"aa", "bb", "cc", "bb", "ccc"};
		return Observable.fromArray(strings);
	}

	/**
	 * 发送多个数据，传入实现Iterable接口的参数
	 * @return Observable
	 */
	public static Observable<String> useFromIterable() {
		String[] strings = {"aa12", "bb34", "cc56"};
		List<String> listString = Arrays.asList(strings);
		return Observable.fromIterable(listString);
	}

	/**
	 * 仅执行第一个Observable的发送行为
	 * @return Observable
	 */
	public static Observable<String> useAmb() {
		String[] strings2 = {"dd", "ee", "ff"};
		String[] strings3 = {"ddddd", "eeeeee", "ffffff"};
		List<Observable<String>> observableList = new ArrayList<>();
		observableList.add(Observable.fromIterable(Arrays.asList(strings2)));
		observableList.add(Observable.fromIterable(Arrays.asList(strings3)));
		return Observable.amb(observableList);
	}

	/**
	 * 重复
	 * @return Observable
	 */
	public static Observable<String> useRepeat() {
		return useJust().repeat(5);
	}

	/**
	 * 有条件重复
	 * @return Observable
	 */
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

	/**
	 * 发送指定范围内的整数
	 * @return Observable
	 */
	public static Observable<Integer> useRange() {
		return Observable.range(10, 5);
	}

	/**
	 * 定时发送整数
	 * @return Observable
	 */
	public static Observable<Long> useInterval() {
		return Observable.interval(1, TimeUnit.SECONDS);
//		return Observable.intervalRange(10,4,1,1, TimeUnit.SECONDS);
	}

	/**
	 * 延时发送整数
	 * @return Observable
	 */
	public static Observable<Long> useTimer() {
		return Observable.timer(1, TimeUnit.MILLISECONDS);
	}

	/**
	 * 缓冲一些数据添入集合再一起发送
	 * @return Observable
	 */
	public static Observable<List<String>> useBuffer() {
//		return useFromIterable().buffer(2);
		return useFromIterable().buffer(2, 1);
	}

	/**
	 * 转换数据
	 * @return Observable
	 */
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

	/**
	 * 将每个数据封装为一个单独的Observable
	 * @return Observable
	 */
	public static Observable<String> useFlatMap() {
		return useFromIterable().flatMap(new Function<String, Observable<String>>() {
			@Override
			public Observable<String> apply(String s) throws Exception {
				return Observable.just(s);
			}
		});
	}

	/**
	 * 类似flatMap，不过可以保证顺序，不受延时影响
	 * @return Observable
	 */
	public static Observable<String> useConcatMap() {
		return useFromIterable().concatMap(new Function<String, Observable<String>>() {
			@Override
			public Observable<String> apply(String s) throws Exception {
				return Observable.just(s);
			}
		});
	}

	/**
	 * 将数据转为单独的Observable，只发送最近的数据
	 * @return Observable
	 */
	public static Observable<String> useSwitchMap() {
		return useAmb().switchMap(new Function<String, Observable<String>>() {
			@Override
			public Observable<String> apply(String s) throws Exception {
				return Observable.just(s);
			}
		});
	}

	/**
	 * 针对本身数据，使用function进行变换后再发送
	 * @return Observable
	 */
	public static Observable<Integer> useScan() {
		return useRange().scan(new BiFunction<Integer, Integer, Integer>() {
			@Override
			public Integer apply(Integer i1, Integer i2) throws Exception {
				System.out.println("i1=" + i1 + ",i2=" + i2);
				return i1 + i2;
			}
		});
	}

	/**
	 * 将不同key的数据分成多个Observable
	 * @return GroupedObservable
	 */
	public static Observable<GroupedObservable<String, Integer>> useGroupBy() {
		return useRange().groupBy(new Function<Integer, String>() {
			@Override
			public String apply(Integer integer) throws Exception {
				return integer % 2 == 0 ? "a" : "b";
//				return  "b";
			}
		});
	}

	/**
	 * 通过数据的个数或时间切割为不同的Observable
	 * @return Observable
	 */
	public static Observable<Observable<Long>> useWindow() {
//		return useInterval().window(4);
		return useInterval().window(6);
//		return useInterval().window(2,TimeUnit.SECONDS);
	}

	/**
	 * 强转类型
	 * @return Observable
	 */
	public static Observable<String> useCast() {
		return useFromIterable().cast(String.class);
	}

	/**
	 * 过滤
	 * @return Observable
	 */
	public static Observable<Integer> useFilter() {
		return useRange().filter(new Predicate<Integer>() {
			@Override
			public boolean test(Integer integer) throws Exception {
				return integer % 2 == 0;
			}
		});
	}

	/**
	 * 按条件取元素
	 * @return Observable
	 */
	public static Observable<Integer> useTake() {
		return useRange().take(3);
//		return useRange().take(3,TimeUnit.SECONDS);
	}

	/**
	 * 按条件取元素，直到某条件成立后不取
	 * @return Observable
	 */
	public static Observable<Integer> useTakeUntil() {
		return useRange().takeUntil(new Predicate<Integer>() {
			@Override
			public boolean test(Integer integer) throws Exception {
				return integer > 10;
			}
		});
	}

	/**
	 * 某条件成立，则一直取
	 * @return Observable
	 */
	public static Observable<Integer> useTakeWhile() {
		return useRange().takeWhile(new Predicate<Integer>() {
			@Override
			public boolean test(Integer integer) throws Exception {
				return integer < 14;
			}
		});
	}

	public static Observable<Integer> useTakeLast() {
		return useRange().takeLast(3);
//		return useRange().take(3,TimeUnit.SECONDS);
	}

	/**
	 * 取最开始的一个值，若无则取默认值；
	 * Single类型只能发送数据或【错误error】通知，不能发送【完成complete】通知
	 *
	 * @return Single
	 */
	public static Single<String> useFirst() {
		return useFromIterable().first("ed");
	}

	/**
	 * 取最开始的一个值；
	 * Maybe类型可发送数据和一条通知，与Observer相似
	 *
	 * @return Maybe
	 */
	public static Maybe<String> useFirstElement() {
		return useFromArray().firstElement();
	}

	/**
	 * 取最后一个值，若无则取默认值；
	 * Single类型只能发送数据或【错误error】通知，不能发送【完成complete】通知
	 *
	 * @return Single
	 */
	public static Single<String> useLast() {
		return useFromIterable().last("da");
	}

	/**
	 * 取最后一个值；
	 * Maybe类型可发送数据和一条通知，与Observer相似
	 *
	 * @return Maybe
	 */
	public static Maybe<String> useLastElement() {
		return useFromArray().lastElement();
	}

	public static Maybe<String> useElementAt() {
		return useFromArray().elementAt(2);
	}

	public static Completable useIgnore() {
		return useFromArray().ignoreElements();
	}

	public static Observable<Integer> useSkip() {
		return useRange().skip(2);
	}

	public static Observable<Integer> useSkipLast() {
		return useRange().skipLast(2);
	}

	public static Observable<Long> useThrottleFirst() {
		return useInterval().throttleFirst(3, TimeUnit.SECONDS);
	}

	public static Observable<Long> useThrottleLast() {
		return useInterval().throttleLast(3, TimeUnit.SECONDS);
	}

	public static Observable<Long> useThrottleWithTimeout() {
		return useInterval().throttleWithTimeout(3, TimeUnit.SECONDS);
	}

	public static Observable<Long> useDebounce() {
		return useInterval().debounce(3, TimeUnit.SECONDS);
	}

	/**
	 * 超时未发送数据则抛出java.util.concurrent.TimeoutException
	 *
	 * @return Observable
	 */
	public static Observable<Long> useTimeout() {
		return useThrottleFirst().timeout(3, TimeUnit.SECONDS);
	}

	public static Observable<String> useDistinct() {
//		return useFromArray().distinct();
//		return useFromArray().distinct(new Function<String, String>() {
//			@Override
//			public String apply(String s) throws Exception {
//				System.out.println("key:"+s);
//				return String.valueOf(s.length());
//			}
//		});
//		return useFromArray().distinctUntilChanged();
//		return useFromArray().distinctUntilChanged(new Function<String, String>() {
//			@Override
//			public String apply(String s) throws Exception {
//				System.out.println("key:" + s);
//				return String.valueOf(s.length());
//			}
//		});
		return useFromArray().distinctUntilChanged(new BiPredicate<String, String>() {
			@Override
			public boolean test(String s1, String s2) throws Exception {
				System.out.println("s1="+s1+",s2="+s2);
				return s1.length()==s2.length();
			}
		});
	}

	public static Observable<Long> useOfType() {
		return useRange().ofType(Long.class);
//		return useInterval().ofType(Long.class);
	}

	public static Observable<String> useCombineLatest() {
		return Observable.combineLatest(useFromIterable(), useFromArray(), new BiFunction<String, String, String>() {
			@Override
			public String apply(String s1, String s2) throws Exception {
				System.out.println("s1="+s1+",s2="+s2);
				return s1+s2;
			}
		});
	}

	public static Observable<String> useMerge() {
		return Observable.merge(useFromIterable(),useFromArray());
	}

	public static Observable<String> useZip() {
		return Observable.zip(useFromIterable(), useFromArray(), new BiFunction<String, String, String>() {
			@Override
			public String apply(String s1, String s2) throws Exception {
				System.out.println("s1="+s1+",s2="+s2);
				return s1+s2;
			}
		});
	}

	public static Observable<Long> useRetry() {
		return useTimeout().retry();
	}

	public static Single<List<String>> useToList() {
		return useFromIterable().toList();
	}

	public static Observable<String> useConcat() {
		return Observable.concat(useFromArray(),useFromIterable());
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
			System.out.println("group--" + stringIntegerGroupedObservable.getKey());
//			stringIntegerGroupedObservable.subscribe(new SimpleIntegerObserver());
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

	static class ObservableObserver extends DisposableObserver<Observable<Long>> {
		@Override
		protected void onStart() {
			System.out.println("----start----");
		}

		@Override
		public void onNext(Observable<Long> stringObservable) {
			System.out.println("window--" + stringObservable.toString());
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

	static class SingleStringObserver extends DisposableSingleObserver<String> {

		@Override
		protected void onStart() {
			System.out.println("----start----");
		}

		@Override
		public void onSuccess(String s) {
			System.out.println(s);
		}

		@Override
		public void onError(Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	static class SingleListStringObserver extends DisposableSingleObserver<List<String>> {

		@Override
		protected void onStart() {
			System.out.println("----start----");
		}

		@Override
		public void onSuccess(List<String> list) {
			System.out.println(list);
		}

		@Override
		public void onError(Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	static class MaybeStringObserver extends DisposableMaybeObserver<String> {

		@Override
		protected void onStart() {
			System.out.println("----start----");
		}

		@Override
		public void onSuccess(String s) {
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

	static class CompletableObserver extends DisposableCompletableObserver {

		@Override
		protected void onStart() {
			System.out.println("----start----");
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
