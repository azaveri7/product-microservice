package com.resttemplatedemo.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.resttemplatedemo.model.Product;


public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	public Collection<Product> findByType(String type);

}
