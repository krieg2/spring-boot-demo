package com.example.springboot;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.springboot.dto.ListingDto;
import com.example.springboot.util.PageUtil;
import lombok.NonNull;

public class PageUtilTest {
    @NonNull
    private Sort sort;

    @BeforeEach
    public void setUp() {
        this.sort = Sort.by("listedDate").ascending();
    }

    @Test
    @DisplayName("Should convert listings to page")
    void toPage_Success() {
        List<ListingDto> listings = new ArrayList<ListingDto>();
        Pageable pageable = PageRequest.of(0, 1, sort);

        Page<ListingDto> result = PageUtil.toPage(listings, pageable);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should convert listings to multiple pages")
    void toPageMultiple_Success() {
        List<ListingDto> listings = new ArrayList<ListingDto>();
        listings.add(new ListingDto());
        listings.add(new ListingDto());
        listings.add(new ListingDto());
        listings.add(new ListingDto());
        Pageable pageable = PageRequest.of(0, 2, sort);

        Page<ListingDto> result = PageUtil.toPage(listings, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(4);
    }

    @Test
    @DisplayName("Should convert null listings to page")
    void toPageNull_Success() {
        Pageable pageable = PageRequest.of(0, 2, sort);

        Page<ListingDto> result = PageUtil.toPage(null, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalPages()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(0);
    }
}
