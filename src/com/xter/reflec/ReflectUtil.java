package com.xter.reflec;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/6/25
 * 描述:
 */
public class ReflectUtil {

	public static Class<?> getClass(Object object) {
		Type superClass = object.getClass().getGenericSuperclass();
		assert superClass != null;
		if (superClass instanceof ParameterizedType) {
			Type type = ((ParameterizedType) superClass).getActualTypeArguments()[1];
			Class<?> clazz = getRawType(type);
			return clazz;
		}
		return null;
	}

	static Class<?> getRawType(Type type) {
		if (type instanceof Class) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type rawType = parameterizedType.getRawType();
			return (Class<?>) rawType;
		} else if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			return Array.newInstance(getRawType(componentType), 0).getClass();
		} else if (type instanceof TypeVariable) {
			return Object.class;
		} else if (type instanceof WildcardType) {
			return getRawType(((WildcardType) type).getUpperBounds()[0]);
		} else {
			String className = type == null ? "null" : type.getClass().getName();
			throw new IllegalArgumentException("some type wrong");
		}
	}
}
