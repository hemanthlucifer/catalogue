package com.ecommerce.catalogue.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UpsellAndCrosssellDto {
	
	@Getter
	@Setter
	private String productID;
	
	@Getter
	@Setter
	private String productName;
	
	@Getter
	@Setter
	private String productDescription;
	
	@Getter
	@Setter
	private double price;

}
