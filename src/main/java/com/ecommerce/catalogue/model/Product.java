package com.ecommerce.catalogue.model;


import java.io.Serializable;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "Product")
public class Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represents the product id unique for every product.
	 * **/
	@Getter
	@Setter
	@Field("product_id")
	@Id
	private String productId;
	
	/**
	 * Represents name of product.
	 * **/
	@Getter
	@Setter
	@Field("product_name")
	private String productName;
	
	/**
	 * Represents unique Id of category for which product belongs.
	 * **/
	@Getter
	@Setter
	@Field("category_id")
	private String categoryId;
	
	/**
	 * Represents name of product manufacturer.
	 * **/
	@Getter
	@Setter
	@Field("manufacturer_name")
	private String manufacturerName;
	
	/**
	 * Represents description of the product.
	 * **/
	@Getter
	@Setter
	@Field("product_description")
	private String productDescription;
	
	/**
	 * Represents the price of the product.
	 * **/
	@Getter
	@Setter
	@Field("price")
	private double price;
	
	/**
	 * Represents the link of the image.
	 * **/
	@Getter
	@Setter
	@Field("image_link")
	private String imageLink;
	
	/**
	 * Represents the product specifications
	 * **/
	@Getter
	@Setter
	private Map<String,String> specifications;

}
