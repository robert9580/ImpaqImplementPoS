package com.impaqgroup.implementPoS.database;

import com.impaqgroup.implementPoS.Product;

public interface DataBase {
	Product findProductByBarCode(String s);
}
