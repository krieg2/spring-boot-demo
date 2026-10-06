package com.example.springboot;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springboot.dto.ListingDto;
import com.example.springboot.service.ListingService;

@SpringBootTest
@AutoConfigureMockMvc
public class ListingServiceTest {
    @Autowired
    private ListingService listingService;

    @Test
    @DisplayName("Should return all of the listings from the json")
    void parseListingsJson_Success() {
        List<ListingDto> result = listingService.parseListingsJson();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(12);
    }
}
