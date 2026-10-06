package com.example.springboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.springboot.dto.ListingDto;
import com.example.springboot.service.ListingService;
import com.example.springboot.util.PageUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {
	private final ListingService listingService;
	private final List<ListingDto> listings;

	public Controller(ListingService ls){
		this.listingService = ls;
		this.listings = listingService.parseListingsJson();
	}

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/listings")
	public Page<ListingDto> getListings(
		@RequestParam(required = true, defaultValue = "0") int page,
		@RequestParam(required = true, defaultValue = "5") int size,
		@RequestParam(required = true, defaultValue = "0") String minPrice,
		@RequestParam(required = true, defaultValue = "10000000") String maxPrice,
		@RequestParam(required = true, defaultValue = "0") String minBedrooms,
		@RequestParam(required = false) String city,
		@RequestParam(required = false) String description
	) throws ResponseStatusException {
		Sort sort = Sort.by("listedDate").ascending();
		Pageable pageable = PageRequest.of(page, size, sort);

		List<ListingDto> results = new ArrayList<ListingDto>();
		if(Float.parseFloat(minPrice.trim()) > Float.parseFloat(maxPrice.trim())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "minPrice cannot be greater than maxPrice.");
		}
		try {
			for(ListingDto listing : this.listings) {
				if(
					listing.price >= Float.parseFloat(minPrice.trim()) &&
                    listing.price <= Float.parseFloat(maxPrice.trim()) &&
                    listing.bedrooms >= Integer.parseInt(minBedrooms.trim()) &&
					(city != null ? listing.city.toLowerCase().contains(city.trim().toLowerCase()) : true) &&
					(description != null ? listing.description.toLowerCase().contains(description.trim().toLowerCase()) : true)
				) {
					results.add(listing);
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		return PageUtil.toPage(results, pageable);
	}

}
