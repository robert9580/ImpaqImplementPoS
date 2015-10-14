package com.impaqgroup.implementPoS.IOdevice;

public class PrinterMock implements OutputDevice {

	public void println(String s) {
		System.out.println("PRINTER:");
		System.out.println(s);
		System.out.println();
	}
}
