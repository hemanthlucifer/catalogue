package com.ecommerce.catalogue.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecommerce.catalogue.dto.ProductDTO;
import com.ecommerce.catalogue.dto.UpsellAndCrosssellDto;
import com.ecommerce.catalogue.model.Product;

/**
 * This class is used for converting Entity to DTO object and vice versa.
 * **/
@Component
public class Convertor {
	
	private static final Logger logger = LoggerFactory.getLogger(Convertor.class);
	
	public Product convertProductDtoToEntity(ProductDTO productDto) {
		logger.info("convertProductDtoToEntity started.");
		Product product = new Product();
		product.setProductId(productDto.getProductId());
		product.setCategoryId(productDto.getCategoryId());
		product.setImageLink(productDto.getImageLink());
		product.setManufacturerName(productDto.getManufacturerName());
		product.setPrice(productDto.getPrice());
		product.setProductDescription(productDto.getProductDescription());
		product.setProductName(productDto.getProductName());
		product.setSpecifications(productDto.getSpecifications());
		logger.info("convertProductDtoToEntity completed.");
		return product;
	}
	
	public ProductDTO convertProductToProductDto(Product product) {
		logger.info("convertProductToProductDto started.");
		ProductDTO productDto = new ProductDTO();
		productDto.setCategoryId(product.getCategoryId());
		productDto.setImageLink(product.getImageLink());
		productDto.setManufacturerName(product.getManufacturerName());
		productDto.setPrice(product.getPrice());
		productDto.setProductDescription(product.getProductDescription());
		productDto.setProductId(product.getProductId());
		productDto.setProductName(product.getProductName());
		productDto.setSpecifications(product.getSpecifications());
		logger.info("convertProductToProductDto completed.");
		return productDto;
	}
	
	

}
