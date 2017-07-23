package com.resttemplatedemo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resttemplatedemo.model.Product;
import com.resttemplatedemo.repository.ProductRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/products")
@Api(value="products")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@ApiOperation(value = "View a list of available products", response = Iterable.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Product>> getAllProducts(){
		return new ResponseEntity<>((Collection<Product>) productRepository.findAll(), HttpStatus.OK);
    }
	
	@ApiOperation(value = "View a list of available products by type", response = Iterable.class)
	@RequestMapping(value = "/types", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Product>> getAllProductsOfSameType(@RequestParam("type") String type){
		return new ResponseEntity<>((Collection<Product>) productRepository.findByType(type), HttpStatus.OK);
    }
	
	@ApiOperation(value = "Save a product", response = Product.class)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product){
		return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
    }
	
	@ApiOperation(value = "Delete a product by id")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable("id") int id){
		productRepository.delete(id);
    }

}
