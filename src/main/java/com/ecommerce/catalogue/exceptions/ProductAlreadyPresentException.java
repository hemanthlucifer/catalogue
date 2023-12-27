package com.ecommerce.catalogue.exceptions;

public class ProductAlreadyPresentException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductAlreadyPresentException(String message) {
		super(message);
	}

}
