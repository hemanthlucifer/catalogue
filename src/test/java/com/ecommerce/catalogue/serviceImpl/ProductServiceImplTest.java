package com.ecommerce.catalogue.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecommerce.catalogue.dto.ProductDTO;
import com.ecommerce.catalogue.model.Product;
import com.ecommerce.catalogue.repository.ProductRepository;

@SpringBootTest
class ProductServiceImplTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	
	private Product product;
	
	private ProductDTO productDto;

	@BeforeEach
	void setUp() throws Exception {
		
		product = new Product();
		product.setCategoryId("Laptop");
		product.setImageLink("www.image.com");
		product.setManufacturerName("abc");
		product.setPrice(22.0);
		product.setProductDescription("Its a good product");
		product.setProductId("PR001");
		product.setProductName("xyz");
		
		productDto = new ProductDTO();
		productDto.setCategoryId("Laptop");
		productDto.setImageLink("www.image.com");
		productDto.setManufacturerName("abc");
		productDto.setPrice(22.0);
		productDto.setProductDescription("Its a good product");
		productDto.setProductId("PR001");
		productDto.setProductName("xyz");
	}

	@Test
	void testCreateProduct() {
		
		when(productRepository.save(product)).thenReturn(product);
		assertEquals(product.getCategoryId(),"Laptop");
		assertEquals(product.getImageLink(),"www.image.com");
		assertEquals(product.getManufacturerName(),"abc");
		assertEquals(product.getPrice(),22.0);
		assertEquals(product.getProductDescription(),"Its a good product");
		assertEquals(product.getProductId(),"PR001");
		assertEquals(product.getProductName(),"xyz");
		
	}

	@Test
	void testGetProductById() {
	    String productId = "PR001";
		when(productRepository.findById(productId)).thenReturn(Optional.of(product));
		assertEquals(product.getProductId(),productId);
		
	}


	

}
