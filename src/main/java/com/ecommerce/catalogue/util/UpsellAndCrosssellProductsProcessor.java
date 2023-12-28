package com.ecommerce.catalogue.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.catalogue.dto.ProductDTO;
import com.ecommerce.catalogue.dto.UpsellAndCrosssellDto;
import com.ecommerce.catalogue.model.Product;
import com.ecommerce.catalogue.repository.ProductRepository;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class UpsellAndCrosssellProductsProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(UpsellAndCrosssellProductsProcessor.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<UpsellAndCrosssellDto> upsellProducts(ProductDTO productDto){
		logger.info("process of creating upsell products started.");
		List<Product> upsellProducts = productRepository.findTop10ProductsByManufacturerNameAndPriceGreaterThan(productDto.getManufacturerName(), productDto.getPrice());
		List<UpsellAndCrosssellDto> upsellProductDtos = new ArrayList<>();
		for(Product product : upsellProducts) {
			UpsellAndCrosssellDto upsellProductDto = new UpsellAndCrosssellDto();
			upsellProductDto.setProductDescription(product.getProductDescription());
			upsellProductDto.setProductID(product.getProductId());
			upsellProductDto.setProductName(product.getProductId());
			upsellProductDto.setPrice(product.getPrice());
			upsellProductDtos.add(upsellProductDto);
		}
		logger.info("process of creating upsell products completed");
		return upsellProductDtos;
	}
	
	
	public List<UpsellAndCrosssellDto>crosssellProducts(ProductDTO productDto){
		logger.info("Process of creating cross sell products started");
		double lowerPrice = productDto.getPrice()-10000;
		double higherPrice = productDto.getPrice()+10000;
		List<Product> crosssellProducts = productRepository.findTop10ProductsBYByManufacturerNameNotAndPriceBetween(productDto.getManufacturerName(), lowerPrice, higherPrice);
		List<UpsellAndCrosssellDto> crosssellProductDtos = new ArrayList<>();
		for(Product product : crosssellProducts){
			UpsellAndCrosssellDto crossellProductDto = new UpsellAndCrosssellDto();
			crossellProductDto.setPrice(product.getPrice());
			crossellProductDto.setProductDescription(product.getProductDescription());
			crossellProductDto.setProductID(product.getProductId());
			crossellProductDto.setProductName(product.getProductName());
			crosssellProductDtos.add(crossellProductDto);
		}
		logger.info("Process of creating crosssell products  completed.");
		return crosssellProductDtos;
		
	}

}
