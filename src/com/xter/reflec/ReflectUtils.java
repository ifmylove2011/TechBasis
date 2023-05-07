package com.xter.reflec;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 放射工具类
 * Created by hongkuan on 2020-06-11 0011.
 */
public class ReflectUtils {

    /**
     * 创建一个新对象 调用无参构造函数
     *
     * @param clsName 类名（包含包名）
     * @return
     */
    public static Object newObjectNotParam(String clsName) throws Exception {
        Object obj = null;
        obj = Class.forName(clsName).newInstance();
        return obj;
    }

    /**
     * @param clsName
     * @param getSingletonMethodName
     * @param paramsType
     * @param params
     * @return
     */
    public static Object getSingletonObject(String clsName, String getSingletonMethodName,
                                            String[] paramsType, Object[] params) throws Exception {
        //调用此方法 必须参数类型和参数值长度相等 或者都为空
        if (!((null == paramsType && null == params) || (null != paramsType && null != params && paramsType.length == params.length))) {
            throw new NoSuchMethodException("调用参数不匹配");
        }

        Object obj = null;
        Class<?> clazz = Class.forName(clsName);
        Method m = clazz.getDeclaredMethod(getSingletonMethodName, disParamsType(paramsType));
        obj = m.invoke(null, params);
        return obj;
    }

    /**
     * 将对象绝对路径串转成Class
     *
     * @param paramsType 对象绝对路径数组
     * @return
     * @throws Exception
     */
    public static Class[] disParamsType(String[] paramsType) throws Exception {
        Class[] argType = null;//方法的参数类型数组
        if (null != paramsType) {
            argType = new Class[paramsType.length];
            for (int i = 0; i < paramsType.length; i++) {
                argType[i] = Class.forName(paramsType[i]);
            }
        }
        return argType;
    }

    public static Object newObject(String clsName, String[] paramsType, Object[] params) throws Exception {
        //调用此方法 必须参数类型和参数值长度相等 或者都为空
        if (!((null == paramsType && null == params) || (null != paramsType && null != params && paramsType.length == params.length))) {
            throw new NoSuchMethodException("调用参数不匹配");
        }

        Object obj = null;
        // 通过构造方法实例化一个类;本例是一个有参数的构造函数，并且构造函数可以为private修饰
        Class clazz = Class.forName(clsName);
        Constructor constructor = clazz.getDeclaredConstructor(disParamsType(paramsType));
        //获得构造方法，argtype是参数类型数组，我们这里代表的是参数只有一个String类型
        constructor.setAccessible(true);// 访问私有构造函数,Spring可以配置私有的属性和方法，其实就是用到的这里
        obj = constructor.newInstance(params);
        return obj;
    }

    public static String callMethodNotParam(Object obj, String methodName) throws Exception {
        if (null == obj) {
            return "";
        }
        Method method = obj.getClass().getMethod(methodName, new Class<?>[0]);//获得不参数的方法
        return (String) method.invoke(obj, new Object() {
        });
    }

    public static String callStaticMethodNotParam(String clsName, String methodName) throws Exception {
        try {
            Class clazz = Class.forName(clsName);
            Method method = clazz.getMethod(methodName, new Class<?>[0]);//获得不参数的方法
            return (String) method.invoke(null, new Object(){});
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String callMethod(Object obj, String methodName, String[] paramsType,
                                    Object[] params) throws Exception {
        //调用此方法 必须参数类型和参数值长度相等 或者都为空
        if (!((null == paramsType && null == params) || (null != paramsType && null != params && paramsType.length == params.length))) {
            throw new NoSuchMethodException("调用参数不匹配");
        }

        if (null == obj) {
            return "";
        }
        Method method = obj.getClass().getMethod(methodName, disParamsType(paramsType));//获得带参数的方法
        return (String) method.invoke(obj, params);
    }

    public static String callStaticMethod(String clsName, String methodName, String[] paramsType, Object[] params)throws Exception {
        //调用此方法 必须参数类型和参数值长度相等 或者都为空
        if (!((null == paramsType && null == params) || (null != paramsType && null != params && paramsType.length == params.length))) {
            throw new NoSuchMethodException("调用参数不匹配");
        }

        try {
            Class clazz = Class.forName(clsName);
            Method method = clazz.getMethod(methodName, disParamsType(paramsType));//获得带参数的方法
            return (String) method.invoke(null, params);

        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param className
     * @param classLoader
     * @param callbackImpl
     * @return
     */
    public static Object crateProxyCallback(String className, ClassLoader classLoader,
                                            InvocationHandler callbackImpl) throws Exception {
        Object myCallbackObj = null;
        Class<?> pICallback = Class.forName(className);//接口
        myCallbackObj = Proxy.newProxyInstance(
                classLoader,//类加载器
                new Class[]{pICallback},//接口数组
                callbackImpl//为接口实现的对应具体方法
        );//为接口实例化对象
        return myCallbackObj;
    }

}
