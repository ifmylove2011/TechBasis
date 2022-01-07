package com.xter.reflec;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/6/25
 * 描述:
 */
public class DomanActivity extends BaseActivity<DomainDataBinding, DomainViewModel> {
	@Override
	void onCreate() {

	}

	public static void main(String[] args) {
		DomanActivity da = new DomanActivity();
		da.onStart();
	}
}
