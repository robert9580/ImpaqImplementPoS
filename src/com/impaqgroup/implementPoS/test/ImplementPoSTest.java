package com.impaqgroup.implementPoS.test;


import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.impaqgroup.implementPoS.ImplementPoS;
import com.impaqgroup.implementPoS.ImplementPoSDictionary;
import com.impaqgroup.implementPoS.Product;
import com.impaqgroup.implementPoS.IOdevice.OutputDevice;
import com.impaqgroup.implementPoS.IOdevice.Scanner;
import com.impaqgroup.implementPoS.database.DataBase;

@RunWith(MockitoJUnitRunner.class)
public class ImplementPoSTest {

	@Mock
	private Scanner scanner;
	
	@Mock
	private OutputDevice lcdDisplay;
	
	@Mock
	private OutputDevice printer;
	
	@Mock
	private DataBase database;
	
	@InjectMocks
	private ImplementPoS implementPoS;
	
	
	

//	@Before
//	public void setUp() throws Exception {
//		implementPoS = new ImplementPoS();
//	}

	
	@Test
	public void productNotFound() throws Exception {
		final String _0000000 = "000000";

		when(scanner.scann()).thenReturn(_0000000);
		
		Assert.assertNull("Product with bar-code="+_0000000+" existing in database", database.findProductByBarCode(_0000000));
		
		implementPoS.singleProductSale();
		verify(lcdDisplay, times(1)).println(ImplementPoSDictionary.PRODUCT_NOT_FOUND);
		verify(lcdDisplay, times(1)).println(anyString());
	}
	
	@Test
	public void productFound() {
		final String _222222 = "222222";
		when(scanner.scann()).thenReturn(_222222);
		Product product = new Product("something", new BigDecimal("9999.99"));
		when(database.findProductByBarCode(_222222)).thenReturn(product);
		implementPoS.singleProductSale();
		verify(lcdDisplay, times(1)).println(product.print());
		verify(lcdDisplay, times(1)).println(anyString());
	}
	
	@Test
	public void productEmptyCode() {
		final String _empty = "";
		when(scanner.scann()).thenReturn(_empty);
		implementPoS.singleProductSale();
		verify(lcdDisplay, times(1)).println(ImplementPoSDictionary.INVALID_BARCODE);
		verify(lcdDisplay, times(1)).println(anyString());
	}
	
	@Test
	public void exitInputFor2Receipts() {
		final String _222222 = "222222";
		final String _333333 = "333333";
		final String _444444 = "444444";
		when(scanner.scann()).thenReturn(_222222).thenReturn(_333333).thenReturn(_444444);
		Product product2 = new Product("2", new BigDecimal("9999.99"));
		Product product3 = new Product("3", new BigDecimal("9999.98"));
		Product product4 = new Product("4", new BigDecimal("0.03"));
		
		when(database.findProductByBarCode(_222222)).thenReturn(product2);
		when(database.findProductByBarCode(_333333)).thenReturn(product3);
		when(database.findProductByBarCode(_444444)).thenReturn(product4);
		implementPoS.singleProductSale();
		implementPoS.singleProductSale();
		implementPoS.singleProductSale();
		implementPoS.exit();
		BigDecimal sum = new BigDecimal("20000.00");
		verify(lcdDisplay, times(1)).println(ImplementPoSDictionary.SUM + sum.toPlainString());
		verify(printer, times(1)).println(contains(ImplementPoSDictionary.SUM + sum.toPlainString()));
		verify(printer, times(1)).println(contains(product2.print()));
		verify(printer, times(1)).println(contains(product3.print()));
		verify(printer, times(1)).println(contains(product4.print()));
		when(scanner.scann()).thenReturn(_333333);
		implementPoS.singleProductSale();
		verify(lcdDisplay, times(2)).println(product3.print());
		implementPoS.exit();
		verify(lcdDisplay, times(1)).println(ImplementPoSDictionary.SUM + "9999.98");
		verify(printer, times(1)).println(contains(ImplementPoSDictionary.SUM + "9999.98"));
		verify(printer, times(2)).println(contains(product3.print()));
	}
	
	

}
