package com.xter.design.decorate;

public class HeaderPrinter extends MulitPrinter {

	public HeaderPrinter(IPrinter printer) {
		super(printer);
	}

	public String printContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("header:");
		sb.append(printer.printContent());
		return sb.toString();
	}
}
