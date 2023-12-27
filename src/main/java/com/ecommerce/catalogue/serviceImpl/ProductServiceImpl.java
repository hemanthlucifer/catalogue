package com.ecommerce.catalogue.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.ecommerce.catalogue.dto.GetProductDTO;
import com.ecommerce.catalogue.dto.ProductDTO;
import com.ecommerce.catalogue.dto.UpsellAndCrosssellDto;
import com.ecommerce.catalogue.exceptions.ExceptionCodes;
import com.ecommerce.catalogue.exceptions.ProductAlreadyPresentException;
import com.ecommerce.catalogue.exceptions.ProductNotFoundException;
import com.ecommerce.catalogue.model.Product;
import com.ecommerce.catalogue.repository.ProductRepository;
import com.ecommerce.catalogue.service.ProductService;
import com.ecommerce.catalogue.util.Convertor;
import com.ecommerce.catalogue.util.SucessMessageCodes;
import com.ecommerce.catalogue.util.UpsellAndCrosssellProductsProcessor;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private Convertor convertorUtil;
	
	@Autowired
	private UpsellAndCrosssellProductsProcessor processor;
	
	@Autowired
	private MessageSource messageSource;
    
	/**
	 * This method takes ProductDTO as argument and creates a product entity from the DTO and returns the created Product through ProductDTO
	 * **/
	@Override
	public ProductDTO createProduct(ProductDTO productDto) {
		Optional<Product> ifExisted = productRepository.findById(productDto.getProductId());
		if(ifExisted.isPresent()) {
			throw new ProductAlreadyPresentException(messageSource.getMessage(ExceptionCodes.productAlreadyPresent, null, Locale.getDefault()));
		}
		Product newProduct = convertorUtil.convertProductDtoToEntity(productDto);
		newProduct = productRepository.save(newProduct);
		ProductDTO createdProduct = convertorUtil.convertProductToProductDto(newProduct);
		return createdProduct;
	}
    
	
	/**
	 * This Method takes the productID as argument and returns the Product object for the given producId.
	 * **/
	@Override
	public GetProductDTO getProductById(String productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isEmpty() || product==null) {
			throw new ProductNotFoundException(messageSource.getMessage(ExceptionCodes.productNotFoundCode, null, Locale.getDefault()));
		}
		GetProductDTO getProductDto = new GetProductDTO();
		ProductDTO productDto = convertorUtil.convertProductToProductDto(product.get());
		List<UpsellAndCrosssellDto> upSellProducts = processor.upsellProducts(productDto);
		List<UpsellAndCrosssellDto> crossellProducts = processor.crosssellProducts(productDto);
		getProductDto.setCategoryId(productDto.getCategoryId());
		getProductDto.setCrossellProducts(crossellProducts);
		getProductDto.setImageLink(productDto.getImageLink());
		getProductDto.setManufacturerName(productDto.getManufacturerName());
		getProductDto.setPrice(productDto.getPrice());
		getProductDto.setProductDescription(productDto.getProductDescription());
		getProductDto.setProductId(productDto.getProductId());
		getProductDto.setProductName(productDto.getProductName());
		getProductDto.setSpecifications(productDto.getSpecifications());
		getProductDto.setUpsellProducts(upSellProducts);
		return getProductDto;
		
	}
    
	/**
	 * This method returns all the products in the product repository.
	 * **/
	@Override
	public List<ProductDTO> getAllProducts() {
		List<Product> listOfProducts = productRepository.findAll();
		List<ProductDTO> listOfProductDto = new ArrayList<>();
		for(Product product : listOfProducts) {
			ProductDTO productDto = convertorUtil.convertProductToProductDto(product);
			listOfProductDto.add(productDto);
		}
		return listOfProductDto;
	}
    
	/**
	 * This method takes productId and Map of fields as input and returns the updated ProductDTO object.
	 * **/
	@Override
	public ProductDTO updateProduct(String productId, Map<String, Object> fields) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isEmpty() || product==null) {
			throw new ProductNotFoundException(messageSource.getMessage(ExceptionCodes.productNotFoundCode, null, Locale.getDefault()));
		}
		fields.forEach((key,value) -> {
			Field field = ReflectionUtils.findField(Product.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, product.get(), value);
		});
		Product updatedProduct = productRepository.save(product.get());
		return convertorUtil.convertProductToProductDto(updatedProduct);
	}
    
	/**
	 * This method used to update product specifications for the given productId.
	 * **/
	@Override
	public ProductDTO updateProductSpecifications(String productId, Map<String, String> specifications) {
		Optional<Product> existingProduct = productRepository.findById(productId);
		if(existingProduct.isEmpty() || existingProduct==null) {
			throw new ProductNotFoundException(messageSource.getMessage(ExceptionCodes.productNotFoundCode, null, Locale.getDefault()));
		}
		existingProduct.get().setSpecifications(specifications);
		Product product = productRepository.save(existingProduct.get());
		return convertorUtil.convertProductToProductDto(product);
	}
    
	/**
	 * This method takes productId as the argument and deletes the product with the given productId.
	 * **/
	@Override
	public String deleteProductById(String productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isEmpty() || product == null) {
			throw new ProductNotFoundException(messageSource.getMessage(ExceptionCodes.productNotFoundCode, null, Locale.getDefault()));
		}
		productRepository.delete(product.get());
		return messageSource.getMessage(SucessMessageCodes.productDeleteionSucessCode, null,Locale.getDefault());
	}
    
	
	/**
	 * This method deletes the entire catalogue of products for the given manufacturer.
	 * **/
	@Override
	public String deleteProductsByManufacturerName(String manufacturerName) {
		productRepository.deleteAllProductsByManufacturer(manufacturerName);
		return messageSource.getMessage(SucessMessageCodes.catalogueDeletionSuccessCode, null,Locale.getDefault());
	}
    
	
	/**
	 * This method creates products in bulk from the list of productDTO.
	 * **/
	@Override
	public List<ProductDTO> createProductsInBulk(List<ProductDTO> products) {
		List<Product> bulkProducts = new ArrayList<>();
		List<ProductDTO> bulkProductList = new ArrayList<>();
		for(ProductDTO productDto : products) {
			Product product = convertorUtil.convertProductDtoToEntity(productDto);
			bulkProducts.add(product);
		}
		List<Product> savedProducts = productRepository.saveAll(bulkProducts);
		for(Product product: savedProducts) {
			ProductDTO bulkProduct = convertorUtil.convertProductToProductDto(product);
			bulkProductList.add(bulkProduct);
		}
		
		return bulkProductList;
	}
    
	/**
	 * This Method used to get products by the given manufacturer name.
	 * **/
	@Override
	public List<ProductDTO> getProductsByManufacturer(String manufacturerName) {
		List<Product> allProducts = productRepository.findAllProductsByManufacturer(manufacturerName);
		List<ProductDTO> productDtoList =  new ArrayList<>();
		for(Product product: allProducts) {
			ProductDTO productDto = convertorUtil.convertProductToProductDto(product);
			productDtoList.add(productDto);
		}
		return productDtoList;
	}

}
