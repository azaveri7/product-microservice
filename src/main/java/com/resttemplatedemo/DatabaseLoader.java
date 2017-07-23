package com.resttemplatedemo;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resttemplatedemo.model.Product;
import com.resttemplatedemo.repository.ProductRepository;


@Service
public class DatabaseLoader {

	private static final Logger logger = LoggerFactory.getLogger(MicroserviceApplication.class);
	private final ProductRepository productRepository;	
	
	@Autowired
	public DatabaseLoader(ProductRepository productRepository){
		this.productRepository = productRepository;
	}
	
    @PostConstruct
    private void initDatabase(){
    	
    	System.out.println("inside post construct of database loader");
    	
    	productRepository.deleteAll();
    	
    	Product product1 = new Product();
		product1.setId(1);
		product1.setType("electronic");		

		Product product2 = new Product();
		product2.setId(2);
		product2.setType("cloth");
		
		Product product3 = new Product();
		product3.setId(3);
		product3.setType("toy");
		
		Product product4 = new Product();
		product4.setId(4);
		product4.setType("electronic");

		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
		productRepository.save(product4);
    	
    }
}
