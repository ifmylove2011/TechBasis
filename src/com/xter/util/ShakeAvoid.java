package com.xter.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XTER on 2017/8/31.
 * 防抖工具
 */
public class ShakeAvoid {
	private static class AvoidShakeHolder {
		private static final ShakeAvoid INSTANCE = new ShakeAvoid();
	}

	/**
	 * 记录时间点
	 */
	private Map<Integer, Long> mPeriodMap;

	/**
	 * 记录次数
	 */
	private Map<Integer, Integer> mCounterMap;

	private ShakeAvoid() {
		mPeriodMap = new HashMap<>();
		mCounterMap = new HashMap<>();
	}

	public static ShakeAvoid get() {
		return AvoidShakeHolder.INSTANCE;
	}

	/**
	 * @param id     标志
	 * @param period 时间间隔
	 * @return 是否符合规则
	 */
	public boolean check(int id, long period) {
		long curTime = System.currentTimeMillis();
		Long previousTime = mPeriodMap.get(id);
		if (previousTime != null && curTime - previousTime < period) {
			return false;
		}
		mPeriodMap.put(id, curTime);
		return true;
	}

	/**
	 * @param id       标志
	 * @param maxTimes 最大重复次数
	 * @return 是否符合规则
	 */
	public boolean check(int id, int maxTimes) {
		Integer counter = mCounterMap.get(id);
		if (counter != null && counter < maxTimes) {
			counter++;
			mCounterMap.put(id, counter);
			return false;
		} else {
			mCounterMap.put(id, 1);
		}
		return true;
	}
}
