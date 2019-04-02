package com.xter.util;

public class L {
	public static void d(String msg) {
		System.out.println(TimeGetter.getCurrentTime()+" "+getMethodPath(3, 3) + ":" + msg);
	}

	public static String getMethodPath(int classPrior, int methodPrior) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

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

}
