package com.impaqgroup.implementPoS.IOdevice;

public class LCDDisplayMock implements OutputDevice {

	public void println(String s) {
		System.out.println("LCD DISPLAY:");
		System.out.println(s);
		System.out.println();
	}
}
