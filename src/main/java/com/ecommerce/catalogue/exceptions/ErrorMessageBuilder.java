package com.ecommerce.catalogue.exceptions;

public class ErrorMessageBuilder {
	
	public String responseBuilder(String statusCode, String message) {
		return (statusCode+" "+":"+" "+message);
	}

}
