package com.ecommerce.catalogue.util;

import java.util.ArrayList;
import java.util.List;

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
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<UpsellAndCrosssellDto> upsellProducts(ProductDTO productDto){
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
		return upsellProductDtos;
	}
	
	
	public List<UpsellAndCrosssellDto>crosssellProducts(ProductDTO productDto){
		double lowerPrice = productDto.getPrice()-10000;
		double higherPrice = productDto.getPrice()+10000;
		List<Product> crosssellProducts = productRepository.findTop10ProductsBYByManufacturerNotAndPriceBetween(productDto.getManufacturerName(), lowerPrice, higherPrice);
		List<UpsellAndCrosssellDto> crosssellProductDtos = new ArrayList<>();
		for(Product product : crosssellProducts){
			UpsellAndCrosssellDto crossellProductDto = new UpsellAndCrosssellDto();
			crossellProductDto.setPrice(product.getPrice());
			crossellProductDto.setProductDescription(product.getProductDescription());
			crossellProductDto.setProductID(product.getProductId());
			crossellProductDto.setProductName(product.getProductName());
			crosssellProductDtos.add(crossellProductDto);
		}
		return crosssellProductDtos;
		
	}

}
