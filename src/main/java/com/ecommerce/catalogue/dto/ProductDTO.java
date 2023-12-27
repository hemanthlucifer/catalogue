package com.ecommerce.catalogue.dto;

import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProductDTO {
	
	/**
	 * Represents the product id unique for every product.
	 * **/
	@Getter
	@Setter
	private String productId;
	
	/**
	 * Represents name of product.
	 * **/
	@Getter
	@Setter
	private String productName;
	
	/**
	 * Represents unique Id of category for which product belongs.
	 * **/
	@Getter
	@Setter
	private String categoryId;
	
	/**
	 * Represents name of product manufacturer.
	 * **/
	@Getter
	@Setter
	private String manufacturerName;
	
	/**
	 * Represents description of the product.
	 * **/
	@Getter
	@Setter
	private String productDescription;
	
	/**
	 * Represents the price of the product.
	 * **/
	@Getter
	@Setter
	private double price;
	
	/**
	 * Represents the link of the image.
	 * **/
	@Getter
	@Setter
	private String imageLink;
	
	/**
	 * Represents the product specifications
	 * **/
	@Getter
	@Setter
	private Map<String,String> specifications;

}
