package com.impaqgroup.implementPoS.database;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.impaqgroup.implementPoS.Product;

public class DataBaseMock implements DataBase {

	private Map<String, Product> map = new HashMap<String, Product>();
	
	public DataBaseMock() {
		initialize();
	}
	
	public Product findProductByBarCode(String s) {
		return map.get(s);
	}
	
	private void initialize() {
		//map.put("111111", new Product("braided sweet white bread", new BigDecimal("2.80")));//not found
		map.put("222222", new Product("milk", new BigDecimal("2.25")));
		map.put("333333", new Product("bread", new BigDecimal("2.87")));
		map.put("444444", new Product("roll", new BigDecimal("0.55")));
	}
}
