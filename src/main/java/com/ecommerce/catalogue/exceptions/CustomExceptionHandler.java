package com.ecommerce.catalogue.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> productNotFoundException(ProductNotFoundException exception){
		ErrorMessageBuilder errorMessage = new ErrorMessageBuilder();
		String responseMessage = errorMessage.responseBuilder(ExceptionCodes.productNotFoundCode, exception.getMessage());
		return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductAlreadyPresentException.class)
	public ResponseEntity<String> productAlreadyPresentException(ProductAlreadyPresentException exception){
		ErrorMessageBuilder errorMessage = new ErrorMessageBuilder();
		String responseMessage = errorMessage.responseBuilder(ExceptionCodes.productNotFoundCode, exception.getMessage());
		return new ResponseEntity<>(responseMessage,HttpStatus.CONFLICT);
	}

}
