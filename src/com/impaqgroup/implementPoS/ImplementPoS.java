package com.impaqgroup.implementPoS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.impaqgroup.implementPoS.IOdevice.BarCodesScannerMock;
import com.impaqgroup.implementPoS.IOdevice.LCDDisplayMock;
import com.impaqgroup.implementPoS.IOdevice.OutputDevice;
import com.impaqgroup.implementPoS.IOdevice.PrinterMock;
import com.impaqgroup.implementPoS.IOdevice.Scanner;
import com.impaqgroup.implementPoS.database.DataBase;
import com.impaqgroup.implementPoS.database.DataBaseMock;

public class ImplementPoS {
	
	private Scanner scanner = new BarCodesScannerMock();
	
	private OutputDevice lcdDisplay = new LCDDisplayMock();
	
	private OutputDevice printer = new PrinterMock();
	
	private DataBase database = new DataBaseMock();
	
	private boolean newReceipt;
	
	private List<Product> listOfProducts = new ArrayList<Product>();
	
	public void singleProductSale() {
		
		if(newReceipt) {
			listOfProducts.clear();
			newReceipt = false;
		}
		
		String barCode = scanner.scann();
		if(barCode == null || barCode.isEmpty()) {           //StringUtils from common library
			lcdDisplay.println(ImplementPoSDictionary.INVALID_BARCODE);
			return;
		}
		Product product = database.findProductByBarCode(barCode);
		if(product == null) {
			lcdDisplay.println(ImplementPoSDictionary.PRODUCT_NOT_FOUND);
			return;
		}
		lcdDisplay.println(product.print());
		listOfProducts.add(product);
		
	}
	
	public void exit() {
		lcdDisplay.println("----------------------------");
		String sum = sumReceipt();
		lcdDisplay.println(ImplementPoSDictionary.SUM + sum);
		String forPrinter = "";
		for (Product product : listOfProducts) {
			forPrinter+=product.print()+"\n";			//compiler change + to StringBuilder
		}
		forPrinter+=ImplementPoSDictionary.SUM + sum;
		printer.println(forPrinter);
		newReceipt = true;
	}
	
	private String sumReceipt() {
		BigDecimal sum = new BigDecimal("0.00");
		for (Product product : listOfProducts) {
			sum = sum.add(product.getPrice());
		}
		return sum.toPlainString();
	}
	
	public static void main(String[] args) {
		
		ImplementPoS implementPoS = new ImplementPoS();
		System.out.println("*********NEW RECEIPT********");
		for (int i = 0; i < 5; i++) {
			implementPoS.singleProductSale();
		}
		implementPoS.exit();
		System.out.println("*********NEW RECEIPT********");
		for (int i = 0; i < 3; i++) {
			implementPoS.singleProductSale();
		}
		implementPoS.exit();
	}
	


}
