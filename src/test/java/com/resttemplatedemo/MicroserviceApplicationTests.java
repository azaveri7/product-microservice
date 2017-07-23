package com.resttemplatedemo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resttemplatedemo.model.Product;
import com.resttemplatedemo.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicroserviceApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	final String BASE_URL = "http://localhost:8080/";

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		this.productRepository.deleteAll();

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

	@Test
	public void test_getProducts() throws Exception {

		mockMvc.perform(get(BASE_URL + "/products")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[2].id", is(3))).andExpect(jsonPath("$[3].id", is(4)))
				.andExpect(jsonPath("$[0].type", is("electronic"))).andExpect(jsonPath("$[1].type", is("cloth")))
				.andExpect(jsonPath("$[2].type", is("toy"))).andExpect(jsonPath("$[3].type", is("electronic")));

	}
	
	@Test
	public void test_getProductsOfSameTypes() throws Exception {

		mockMvc.perform(get(BASE_URL + "/products/types?type=toy")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(3))).andExpect(jsonPath("$[0].type", is("toy")));

	}

	@Test
	public void test_postProducts() throws Exception {

		Product p = new Product();
		p.setId(5);
		p.setType("electronic");

		mockMvc.perform(post(BASE_URL + "/products").content(new ObjectMapper().writeValueAsString(p))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(5)))
				.andExpect(jsonPath("$.type", is("electronic")));

		Assert.assertEquals(productRepository.count(), 5);
		Assert.assertEquals(productRepository.findOne(5).getType(), "electronic");

	}

	@Test
	public void test_deleteProduct() throws Exception {

		mockMvc.perform(delete(BASE_URL + "/products/{id}", 2)).andExpect(status().isOk());

		Assert.assertEquals(productRepository.count(), 3);
		Assert.assertEquals(productRepository.findOne(2), null);

	}

}
