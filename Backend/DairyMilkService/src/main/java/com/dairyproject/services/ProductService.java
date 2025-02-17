package com.dairyproject.services;

import java.util.Set;

import com.dairyproject.entities.ProductDetails;
import com.dairyproject.exceptions.ProductNotFoundException;

public interface ProductService {
	Set<ProductDetails> getAllProductDetails();

	ProductDetails getProductDetailsByName(String name);

	ProductDetails getProductDetailsByPid(Integer PID);

	String insertNewProductDetails(ProductDetails productDetails);

	String updateProductDetails(ProductDetails productDetails);

	String removeProductByPID(Integer pid) throws ProductNotFoundException;
}
