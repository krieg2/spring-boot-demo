package com.example.springboot;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("Greetings from Spring Boot!")));
	}

	@Test
	@DisplayName("Should get listings")
	public void getListings() throws Exception {
		String url = "/listings?minPrice=0&maxPrice=10000000&minBedrooms=0&page=0";
		mvc.perform(MockMvcRequestBuilders.get(url))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@DisplayName("Should throw an exception in get listings if minPrice is greater than maxPrice")
	public void getListingsThrowsException() throws Exception {
		String url = "/listings?minPrice=100&maxPrice=10&minBedrooms=0&page=0";
		mvc.perform(MockMvcRequestBuilders.get(url))
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Should get listings with min and max price")
	public void getListingsMinAndMax() throws Exception {
		String url = "/listings?minPrice=525000.00&maxPrice=527500.00&minBedrooms=0&page=0";
		mvc.perform(MockMvcRequestBuilders.get(url))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.content").exists())
			.andExpect(jsonPath("$.content[0].id").value("A2"))
			.andExpect(jsonPath("$.content[1].id").value("B8"))
			.andExpect(jsonPath("$.content[2]").doesNotExist());
	}

	@Test
	@DisplayName("Should get next page of listings")
	public void getNextPageListings() throws Exception {
		String url = "/listings?minPrice=0&maxPrice=10000000&minBedrooms=0&page=1";
		mvc.perform(MockMvcRequestBuilders.get(url))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.content").exists())
			.andExpect(jsonPath("$.content[0].id").value("B9"));
	}

	@Test
	@DisplayName("Should get empty page of listings for invalid page")
	public void getEmptyPageListings() throws Exception {
		String url = "/listings?minPrice=0&maxPrice=10000000&minBedrooms=0&page=10";
		mvc.perform(MockMvcRequestBuilders.get(url))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.content").exists())
			.andExpect(jsonPath("$.content[0]").doesNotExist());
	}
}
