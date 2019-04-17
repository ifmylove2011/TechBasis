package com.xter.design.decorate;

public class Main {
	public static void main(String[] args) {
		IPrinter printer = new HeaderPrinter(new Printer());
		System.out.println(printer.printContent());
		IPrinter printer1 = new TailPrinter(new Printer());
		System.out.println(printer1.printContent());
	}
}
