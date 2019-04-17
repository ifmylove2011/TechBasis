package com.xter.design.decorate;

public class TailPrinter extends MulitPrinter {
	public TailPrinter(IPrinter printer) {
		super(printer);
	}

	public String printContent() {
		StringBuilder sb = new StringBuilder();
		sb.append(printer.printContent());
		sb.append(":tail");
		return sb.toString();
	}
}
