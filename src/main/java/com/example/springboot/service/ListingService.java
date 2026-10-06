package com.example.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;

import com.example.springboot.dto.ListingDto;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class ListingService {
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public ListingService(ObjectMapper om, ResourceLoader rl) {
        this.objectMapper = om;
        this.resourceLoader = rl;
    }

    public List<ListingDto> parseListingsJson() {
        try {
            // Load file from src/main/resources/sample_listings.json
            Resource resource = resourceLoader.getResource("classpath:sample_listings.json");
            InputStream inputStream = resource.getInputStream();
            
            // Parse JSON input stream directly into the User class
            List<ListingDto> listings = objectMapper.readValue(inputStream, new TypeReference<List<ListingDto>>() {});
            return listings;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to read or parse JSON file", e);
        }
    }

    
}
