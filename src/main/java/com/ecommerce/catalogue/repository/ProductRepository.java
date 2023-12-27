package com.ecommerce.catalogue.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ecommerce.catalogue.model.Product;

public interface ProductRepository extends MongoRepository<Product,String> {
	
	@Query("{'manufacturer':?0, 'price': {$gt:?1}}")
	List<Product> findTop10ProductsByManufacturerNameAndPriceGreaterThan(String manufacturer,double price);
	
	@Query("{'manufacturer':{$ne:?0}, 'price':{$gt:?1, $lt:?2}")
	List<Product> findTop10ProductsBYByManufacturerNotAndPriceBetween(String manufacturer, double lowerPrice, double higerPrice);
	
	void deleteAllProductsByManufacturer(String manufacturer);
	
	List<Product> findAllProductsByManufacturer(String manufacturer);

}
