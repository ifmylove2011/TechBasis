package com.xter.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XTER
 * 创建时间: 2020/6/15
 * 描述: 正则替换
 */
public class TextUtil {

	/**
	 * 替换对应group(n)的内容
	 *
	 * @param origin      原始字符串
	 * @param regex       全匹配正则，需要替换的内容加小括号提取参数
	 * @param groupIndice group索引
	 * @param content     最终要得到的内容数组
	 * @return 最终内容
	 */
	public static String replaceMatcherContent(String origin, String regex, int[] groupIndice, String... content) {
		if (groupIndice.length != content.length) {
			return origin;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(origin);
		if (matcher.matches()) {
			int count = matcher.groupCount();
			String[] resSubArray = new String[count * 2 + 1];
			int[] offsets = getOffsets(Matcher.class, matcher);
			if (offsets == null) {
				return origin;
			}
			//分离出解析的内容
			int lastIndex = 0;
			for (int i = 1; i <= count; i++) {
				int startIndex = offsets[i * 2];
				int endIndex = offsets[i * 2 + 1];
				resSubArray[i * 2 - 2] = origin.substring(lastIndex, startIndex);
				resSubArray[i * 2 - 1] = origin.substring(startIndex, endIndex);
				lastIndex = endIndex;
			}
			resSubArray[count * 2] = origin.substring(lastIndex);

			//替换对应位置的内容
			for (int i = 0; i < groupIndice.length; i++) {
				resSubArray[groupIndice[i] * 2 - 1] = content[i];
			}

			//合并字符串
			StringBuilder sb = new StringBuilder();
			for (String sub : resSubArray) {
				sb.append(sub);
			}
			return sb.toString();
		}

		return origin;
	}

	/**
	 * 反射得到group所在索引
	 *
	 * @param clazz           Matcher类
	 * @param matcherInstance Matcher实例
	 * @return 索引数组
	 */
	private static int[] getOffsets(Class clazz, Object matcherInstance) {
		try {
			Field field = clazz.getDeclaredField("groups");
			field.setAccessible(true);

			return (int[]) field.get(matcherInstance);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
