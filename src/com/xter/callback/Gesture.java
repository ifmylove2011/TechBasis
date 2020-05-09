package com.xter.callback;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/5/8
 * 描述:
 */
public interface Gesture {
	void onMove();

	void setGestureListener(GestureListener<? extends Gesture> listener);
}
