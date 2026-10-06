package com.example.springboot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingDto {

    public String id;
    public String source;
    public String address;
    public String city;
    public String state;
    public String zip;
    public float price;
    public int bedrooms;
    public float bathrooms;
    public int sqft;
    public float latitude;
    public float longitude;
    public LocalDate listedDate;
    public String status;
    public String description;

}
