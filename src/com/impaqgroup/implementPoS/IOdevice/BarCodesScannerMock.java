package com.impaqgroup.implementPoS.IOdevice;

import java.util.Random;

public class BarCodesScannerMock implements Scanner {

	private Random random = new Random(System.currentTimeMillis());
	
	public String scann() {
		
		int i = random.nextInt(5);
		switch (i) {
		case 1:
			return "111111";
			//break;
		case 2:
			return "222222";	
		
		case 3:
			return "333333";
		
		case 4:
			return "444444";
		
		case 0:
			return "";	
			
		default:
			return "";
		}			
		
	}
	
}
