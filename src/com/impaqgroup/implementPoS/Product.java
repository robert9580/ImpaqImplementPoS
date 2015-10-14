package com.impaqgroup.implementPoS;

import java.math.BigDecimal;

public class Product {
	
	private String name;
	
	private BigDecimal price;

	public Product(String name, BigDecimal price) {
	
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public String print() {
		return name + "\t\t\t" + price.toPlainString();//NumberFormat.getCurrencyInstance().format(price)
	}

}
