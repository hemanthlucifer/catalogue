package com.ecommerce.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan("{com.ecommerce.catalogue.web}")
public class CatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogueApplication.class, args);
	}

}
