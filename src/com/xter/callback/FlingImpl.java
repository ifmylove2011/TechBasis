package com.xter.callback;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/5/8
 * 描述:
 */
public class FlingImpl implements Fling {
	private GestureListener listener;

	@Override
	public void onMove() {
		listener.done(this);
	}

	@Override
	public void setGestureListener(GestureListener<? extends Gesture> listener) {
		this.listener = listener;
	}

	@Override
	public void onFling() {
		System.out.println("invoke onFling");
	}
}
