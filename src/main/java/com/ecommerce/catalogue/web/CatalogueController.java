package com.ecommerce.catalogue.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.catalogue.dto.GetProductDTO;
import com.ecommerce.catalogue.dto.ProductDTO;
import com.ecommerce.catalogue.service.ProductService;

@RestController
@RequestMapping("/catalogue")
public class CatalogueController {
	
	@Autowired
	private ProductService productService;
	
	
	@PostMapping("/")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDto){
		ProductDTO createdProduct = productService.createProduct(productDto);
		return new ResponseEntity<>(createdProduct,HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<GetProductDTO> getProductById(@PathVariable String productId){
		GetProductDTO product = productService.getProductById(productId);
		return new ResponseEntity<>(product,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		List<ProductDTO> productsList = productService.getAllProducts();
		return new ResponseEntity<>(productsList,HttpStatus.OK);
		}
	
	@PatchMapping("/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productId, @RequestBody Map<String,Object> fields){
		ProductDTO productDto = productService.updateProduct(productId, fields);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	
	@PatchMapping("/sepcifiactions/{productId}")
	public ResponseEntity<ProductDTO> updateProductSpecifications(@PathVariable String productId,@RequestBody Map<String,String> specifications){
		ProductDTO productDto = productService.updateProductSpecifications(productId, specifications);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable String productId){
		String response = productService.deleteProductById(productId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/bulkProductCreation")
	public ResponseEntity<List<ProductDTO>> createProductsInBulk(List<ProductDTO> productDto){
		List<ProductDTO> productDtoList = productService.createProductsInBulk(productDto);
		return new ResponseEntity<>(productDtoList,HttpStatus.OK);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<String> deleteProductByManufacturer(@RequestParam String manufacturer){
		String response = productService.deleteProductsByManufacturerName(manufacturer);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	

}
