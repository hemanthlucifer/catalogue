package com.ecommerce.catalogue.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.catalogue.dto.ProductDTO;
import com.ecommerce.catalogue.model.Product;
import com.ecommerce.catalogue.repository.ProductRepository;
import com.ecommerce.catalogue.serviceImpl.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(CatalogueController.class)
class CatalogueControllerTest {
    
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductServiceImpl productServiceImpl;
	
	@MockBean
	private ProductRepository productRepository;
	
	Product product;
	
	ProductDTO productDto;
	
	@BeforeEach
	void setUp() {
		
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
	void testCreateProduct() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(productDto);
		when(productServiceImpl.createProduct(productDto)).thenReturn(productDto);
		this.mockMvc.perform(post("/catalogue/").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
		.andExpect(status().isOk());
	}


	@Test
	void testGetAllProducts() throws Exception {
		List<ProductDTO> products = new ArrayList<>();
		products.add(productDto);
		when(productServiceImpl.getAllProducts()).thenReturn(products);
		this.mockMvc.perform(get("/catalogue/")).andDo(print()).andExpect(status().isOk());
	}


	@Test
	void testDeleteProductById() throws  Exception{
		String productID = "PR001";
		String message = "product deleted successfully";
		when(productServiceImpl.deleteProductById(productID)).thenReturn(message);
		this.mockMvc.perform(delete("/catalogue/PR001")).andDo(print()).andExpect(status().isOk());
	}


	

}
