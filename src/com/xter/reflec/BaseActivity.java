package com.xter.reflec;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/6/25
 * 描述:
 */
public abstract class BaseActivity<V extends BaseDataBinding,VM extends BaseViewModel> {

	abstract void onCreate();

	void onStart(){
		Class c = ReflectUtil.getClass(this);
		System.out.println(c.getName());
	}
}
