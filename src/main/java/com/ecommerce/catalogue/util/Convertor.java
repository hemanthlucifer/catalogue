package com.ecommerce.catalogue.util;

import org.springframework.stereotype.Component;

import com.ecommerce.catalogue.dto.ProductDTO;
import com.ecommerce.catalogue.dto.UpsellAndCrosssellDto;
import com.ecommerce.catalogue.model.Product;

/**
 * This class is used for converting Entity to DTO object and vice versa.
 * **/
@Component
public class Convertor {
	
	
	public Product convertProductDtoToEntity(ProductDTO productDto) {
		Product product = new Product();
		product.setProductId(productDto.getProductId());
		product.setCategoryId(productDto.getCategoryId());
		product.setImageLink(productDto.getImageLink());
		product.setManufacturerName(productDto.getManufacturerName());
		product.setPrice(productDto.getPrice());
		product.setProductDescription(product.getProductDescription());
		product.setProductName(product.getProductName());
		product.setSpecifications(productDto.getSpecifications());
		return product;
	}
	
	public ProductDTO convertProductToProductDto(Product product) {
		ProductDTO productDto = new ProductDTO();
		productDto.setCategoryId(product.getCategoryId());
		productDto.setImageLink(product.getImageLink());
		productDto.setManufacturerName(product.getManufacturerName());
		productDto.setPrice(product.getPrice());
		productDto.setProductDescription(product.getProductDescription());
		productDto.setProductId(product.getProductId());
		productDto.setProductName(product.getProductName());
		productDto.setSpecifications(product.getSpecifications());
		return productDto;
	}
	
	

}
