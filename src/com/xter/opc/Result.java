package com.xter.opc;

public class Result {

	/**
	 * 监控位置
	 */
	private final String itemId;

	/**
	 * 监控值
	 */
	private final Object value;

	public Result(String itemId, Object value) {
		this.itemId = itemId;
		this.value = value;
	}

	public String getItemId() {
		return itemId;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "[itemId=" + itemId + ", value=" + value + "]";
	}

}