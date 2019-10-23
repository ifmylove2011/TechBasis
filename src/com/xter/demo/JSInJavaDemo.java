package com.xter.demo;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

/**
 * @author XTER
 * @desc 在JAVA中运行JS
 * @date 2019/10/14
 */
public class JSInJavaDemo {

	public static void main(String[] args) {
		String jsCode = "function myFunction(a, b)\n" +
				"{\n" +
				"    return a * b;\n" +
				"}";

		System.out.println(new JSInJavaDemo().getResultByAddtionalJS(jsCode, "myFunction", 2, 3));
	}

	public String getResultByAddtionalJS(String jsCode, String jsFunctionName, Object... values) {
		String finalResult = null;
		Context rhino = Context.enter();
		rhino.setOptimizationLevel(-1);
		try {
			//初始化作用域
			ScriptableObject scope = rhino.initStandardObjects();
			//执行js
			rhino.evaluateString(scope, jsCode, "demo", 1, null);
			//js函数调用
			Function function = (Function) scope.get(jsFunctionName, scope);
			Object result = function.call(rhino, scope, scope, values);
			finalResult = Context.toString(result);
		} finally {
			//释放资源
			Context.exit();
		}
		return finalResult;
	}
}
