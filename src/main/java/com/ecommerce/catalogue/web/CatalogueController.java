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

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.*;


@OpenAPIDefinition(info = @Info(
		contact= @Contact(name="Ecommerce Website team"),
		description = "This service is a part of laptop ecommerce website it deals with creation of product and its specification and details.",
		version="v1.0")
)
@RestController
@RequestMapping("/catalogue")
public class CatalogueController {
	
	@Autowired
	private ProductService productService;
	
	@Operation(summary = "To create a product entity") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Product Is created"), 
	@ApiResponse(responseCode = "401",description = "Unauthorized user"), 
	        @ApiResponse(responseCode = "400",description = "Invalid request")})
	@PostMapping("/")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDto){
		ProductDTO createdProduct = productService.createProduct(productDto);
		return new ResponseEntity<>(createdProduct,HttpStatus.OK);
	}
	
	@Operation(summary = "To get a product by using product Id") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Product found with the given product ID"), 
	@ApiResponse(responseCode = "401",description = "Unauthorized user"), 
	        @ApiResponse(responseCode = "404",description = "Product not found"), 
	        @ApiResponse(responseCode = "400",description = "Invalid request")})
	@GetMapping("/{productId}")
	public ResponseEntity<GetProductDTO> getProductById(@PathVariable String productId){
		GetProductDTO product = productService.getProductById(productId);
		return new ResponseEntity<>(product,HttpStatus.OK);
	}
	
	@Operation(summary = "To get all products for the given manufacturer name.") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Products found with the given manufacturer name"), 
	@ApiResponse(responseCode = "401",description = "Unauthorized user"), 
	        @ApiResponse(responseCode = "404",description = "manufacturer not found"), 
	        @ApiResponse(responseCode = "400",description = "Invalid request")})
	@GetMapping("/{manufacturerName}")
	public ResponseEntity<List<ProductDTO>> getProductByManufacturerName(@PathVariable String manufacturerName){
		List<ProductDTO> products = productService.getProductsByManufacturer(manufacturerName);
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@Operation(summary = "To get all products in the catalogue") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "All products returned"), 
	@ApiResponse(responseCode = "401",description = "Unauthorized user"), 
	        @ApiResponse(responseCode = "400",description = "Invalid request")})
	@GetMapping("/")
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		List<ProductDTO> productsList = productService.getAllProducts();
		return new ResponseEntity<>(productsList,HttpStatus.OK);
		}
	
	@Operation(summary = "To update a product using product ID") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Product Is updated successfully"), 
	@ApiResponse(responseCode = "401",description = "Unauthorized user"), 
	        @ApiResponse(responseCode = "404",description = "Product not found"), 
	        @ApiResponse(responseCode = "400",description = "Invalid request")})
	@PatchMapping("/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productId, @RequestBody Map<String,Object> fields){
		ProductDTO productDto = productService.updateProduct(productId, fields);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	
	@Operation(summary = "To update specifications of the product using product Id") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Product specifications updated successfully"), 
	@ApiResponse(responseCode = "401",description = "Unauthorized user"), 
	        @ApiResponse(responseCode = "404",description = "Product not found"), 
	        @ApiResponse(responseCode = "400",description = "Invalid request")})
	@PatchMapping("/sepcifiactions/{productId}")
	public ResponseEntity<ProductDTO> updateProductSpecifications(@PathVariable String productId,@RequestBody Map<String,String> specifications){
		ProductDTO productDto = productService.updateProductSpecifications(productId, specifications);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	
	@Operation(summary = "To delete a product by using the product Id") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Product Is deleted successfully"), 
	@ApiResponse(responseCode = "401",description = "Unauthorized user"), 
	        @ApiResponse(responseCode = "404",description = "Product not found"), 
	        @ApiResponse(responseCode = "400",description = "Invalid request")})
	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable String productId){
		String response = productService.deleteProductById(productId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@Operation(summary = "To create products in bulk") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Products are created"), 
	@ApiResponse(responseCode = "401",description = "Unauthorized user"),  
	        @ApiResponse(responseCode = "400",description = "Invalid request")})
	@PostMapping("/bulkProductCreation")
	public ResponseEntity<List<ProductDTO>> createProductsInBulk(List<ProductDTO> productDto){
		List<ProductDTO> productDtoList = productService.createProductsInBulk(productDto);
		return new ResponseEntity<>(productDtoList,HttpStatus.OK);
	}
	
	@Operation(summary = "To delete all the products in catalogue by manufacturer name") 
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "Products are deleted successfully"), 
	@ApiResponse(responseCode = "401",description = "Unauthorized user"), 
	        @ApiResponse(responseCode = "404",description = "Manufacturer not found"), 
	        @ApiResponse(responseCode = "400",description = "Invalid request")})
	@DeleteMapping("/")
	public ResponseEntity<String> deleteProductByManufacturer(@RequestParam String manufacturer){
		String response = productService.deleteProductsByManufacturerName(manufacturer);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	

}
