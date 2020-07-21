package com.xter.callback;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/5/8
 * 描述:
 */
public interface GestureListener<F> {
	void done(F gesture);
}
