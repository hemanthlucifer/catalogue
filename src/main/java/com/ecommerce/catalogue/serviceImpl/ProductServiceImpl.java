package com.ecommerce.catalogue.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    
	/**
	 * This method takes ProductDTO as argument and creates a product entity from the DTO and returns the created Product through ProductDTO
	 * **/
	@Override
	public ProductDTO createProduct(ProductDTO productDto) {
		logger.info("Create product flow started successfully");
		Optional<Product> ifExisted = productRepository.findById(productDto.getProductId());
		if(ifExisted.isPresent()) {
			logger.error("Product is already present with the given Id");
			throw new ProductAlreadyPresentException(messageSource.getMessage(ExceptionCodes.productAlreadyPresent, null, LocaleContextHolder.getLocale()));
		}
		Product newProduct = convertorUtil.convertProductDtoToEntity(productDto);
		newProduct = productRepository.save(newProduct);
		ProductDTO createdProduct = convertorUtil.convertProductToProductDto(newProduct);
		logger.info("product created successfully");
		return createdProduct;
	}
    
	
	/**
	 * This Method takes the productID as argument and returns the Product object for the given producId.
	 * **/
	@Override
	public GetProductDTO getProductById(String productId) {
		logger.info("Get product by product id flow started.");
		Optional<Product> product = productRepository.findById(productId);
		if(product.isEmpty() || product==null) {
			logger.error("No product found with the given product Id.");
			throw new ProductNotFoundException(messageSource.getMessage(ExceptionCodes.productNotFoundCode, null, LocaleContextHolder.getLocale()));
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
		logger.info("Get product by product id flow completed.");
		return getProductDto;
		
	}
    
	/**
	 * This method returns all the products in the product repository.
	 * **/
	@Override
	public List<ProductDTO> getAllProducts() {
		logger.info("Get all products in the repository flow started");
		List<Product> listOfProducts = productRepository.findAll();
		List<ProductDTO> listOfProductDto = new ArrayList<>();
		if(listOfProducts==null || listOfProducts.isEmpty()) {
			logger.warn("No products found in the repository.");
			return null;
		}else {
			for(Product product : listOfProducts) {
				ProductDTO productDto = convertorUtil.convertProductToProductDto(product);
				listOfProductDto.add(productDto);
			}
		}
		logger.info("Get all products in the repository flow completed");
		return listOfProductDto;
	}
    
	/**
	 * This method takes productId and Map of fields as input and returns the updated ProductDTO object.
	 * **/
	@Override
	public ProductDTO updateProduct(String productId, Map<String, Object> fields) {
		logger.info("update product by using product id flow started.");
		Optional<Product> product = productRepository.findById(productId);
		if(product.isEmpty() || product==null) {
			logger.error("Product with given product Id is not found.");
			throw new ProductNotFoundException(messageSource.getMessage(ExceptionCodes.productNotFoundCode, null, LocaleContextHolder.getLocale()));
		}
		fields.forEach((key,value) -> {
			Field field = ReflectionUtils.findField(Product.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, product.get(), value);
		});
		Product updatedProduct = productRepository.save(product.get());
		logger.info("update product by using product id flow completed.");
		return convertorUtil.convertProductToProductDto(updatedProduct);
	}
    
	/**
	 * This method used to update product specifications for the given productId.
	 * **/
	@Override
	public ProductDTO updateProductSpecifications(String productId, Map<String, String> specifications) {
		logger.info("Update product specifications with the given product Id started.");
		Optional<Product> existingProduct = productRepository.findById(productId);
		if(existingProduct.isEmpty() || existingProduct==null) {
			logger.error("No product found with the given product ID.");
			throw new ProductNotFoundException(messageSource.getMessage(ExceptionCodes.productNotFoundCode, null, LocaleContextHolder.getLocale()));
		}
		existingProduct.get().setSpecifications(specifications);
		Product product = productRepository.save(existingProduct.get());
		logger.info("Update product specifications with the given product Id completed.");
		return convertorUtil.convertProductToProductDto(product);
	}
    
	/**
	 * This method takes productId as the argument and deletes the product with the given productId.
	 * **/
	@Override
	public String deleteProductById(String productId) {
		logger.info("Delete product with the given product Id started.");
		Optional<Product> product = productRepository.findById(productId);
		if(product.isEmpty() || product == null) {
			logger.error("No product found with the given product Id.");
			throw new ProductNotFoundException(messageSource.getMessage(ExceptionCodes.productNotFoundCode, null, LocaleContextHolder.getLocale()));
		}
		productRepository.delete(product.get());
		logger.info("Delete product with the given product Id completed.");
		return messageSource.getMessage(SucessMessageCodes.productDeleteionSucessCode, null,LocaleContextHolder.getLocale());
	}
    
	
	/**
	 * This method deletes the entire catalogue of products for the given manufacturer.
	 * **/
	@Override
	public String deleteProductsByManufacturerName(String manufacturerName) {
		logger.info("Delete products by using manufacturer name started.");
		productRepository.deleteAllProductsByManufacturerName(manufacturerName);
		logger.info("Delete products by using manufacturer name completed.");
		return messageSource.getMessage(SucessMessageCodes.catalogueDeletionSuccessCode, null,LocaleContextHolder.getLocale());
	}
    
	
	/**
	 * This method creates products in bulk from the list of productDTO.
	 * **/
	@Override
	public List<ProductDTO> createProductsInBulk(List<ProductDTO> products) {
		logger.info("create products in bulk process started");
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
		logger.info("create products in bulk process completed.");
		return bulkProductList;
	}
    
	/**
	 * This Method used to get products by the given manufacturer name.
	 * **/
	@Override
	public List<ProductDTO> getProductsByManufacturer(String manufacturerName) {
		logger.info("get products by manufacturer name started.");
		List<Product> allProducts = productRepository.findAllProductsByManufacturerName(manufacturerName);
		List<ProductDTO> productDtoList =  new ArrayList<>();
		if(allProducts==null || allProducts.isEmpty()) {
			logger.warn("No products found with the given manufacturer name.");
			return null;
		}else {
			for(Product product: allProducts) {
				ProductDTO productDto = convertorUtil.convertProductToProductDto(product);
				productDtoList.add(productDto);
			}
			logger.info("get products by manufacturer name completed.");
			return productDtoList;
		}
	}

}
