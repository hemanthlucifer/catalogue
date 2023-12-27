package com.ecommerce.catalogue.service;

import java.util.List;
import java.util.Map;

import com.ecommerce.catalogue.dto.GetProductDTO;
import com.ecommerce.catalogue.dto.ProductDTO;

public interface ProductService {
	
	public ProductDTO createProduct(ProductDTO productDto);
	public GetProductDTO getProductById(String productID);
	public List<ProductDTO> getAllProducts();
	public ProductDTO updateProduct(String productId,Map<String,Object> fields);
	public ProductDTO updateProductSpecifications(String productId,Map<String,String> specifications);
	public String deleteProductById(String productId);
	public String deleteProductsByManufacturerName(String manufacturerName);
	public List<ProductDTO>createProductsInBulk(List<ProductDTO> products);
	public List<ProductDTO>getProductsByManufacturer(String manufacturerName);

}
