package com.xter.design.decorate;

public abstract class MulitPrinter implements IPrinter {
	IPrinter printer;

	public MulitPrinter(IPrinter printer) {
		this.printer = printer;
	}
}
