package com.xter.util;

public class L {

	private static final ThreadLocal<StackTraceElement[]> traceLocal = new ThreadLocal<>();

	public static void d(String msg) {
		System.out.println(TimeGetter.getCurrentTime() + " " + getMethodPath(3, 3) + ":" + msg);
	}

	public static String getMethodPath(int classPrior, int methodPrior) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//		if (stackTrace == null) {
//			stackTrace = Thread.currentThread().getStackTrace();
//			traceLocal.set(stackTrace);
//			L.d("get stackTrace");
//		}

		StackTraceElement targetElement = stackTrace[classPrior];
		String fileName = targetElement.getFileName();

		String methodName = targetElement.getMethodName();
		int lineNumber = targetElement.getLineNumber();

		if (lineNumber < 0) {
			lineNumber = 0;
		}
		int length = Thread.currentThread().getStackTrace().length;
		if (classPrior > length || methodPrior > length) {
			return "";
		} else
			return "(" + fileName + ":" + lineNumber + ")#" + methodName + "-->";
	}

	public static void logThreadSequence() {
		StackTraceElement[] stackTrace = traceLocal.get();
		if (stackTrace == null) {
			stackTrace = Thread.currentThread().getStackTrace();
			traceLocal.set(stackTrace);
		}
		int length = stackTrace.length;
		for (int i = 0; i < length; i++) {
			System.out.println(stackTrace[i].getClassName()+","+
					stackTrace[i].getMethodName());
		}
	}
}
