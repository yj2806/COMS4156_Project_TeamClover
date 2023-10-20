package com.example.webservice.controller;

import com.example.webservice.model.Listing;
import com.example.webservice.repository.ListingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListingRepository listingRepository;

    private Listing testListing;

    @BeforeEach
    public void setup() {
        testListing = new Listing();
        testListing.setListingID(1L);  // adjusted to Long type
        // ... set other fields as necessary ...

        when(listingRepository.findAll()).thenReturn(Arrays.asList(testListing));
        when(listingRepository.findById(1L)).thenReturn(Optional.of(testListing));  // adjusted to Long type
    }

    @Test
    public void getAllListings_ShouldReturnListings() throws Exception {
        mockMvc.perform(get("/listings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].listingID").value(1L));
    }

    @Test
    public void getListingById_ValidId_ShouldReturnListing() throws Exception {
        mockMvc.perform(get("/listings/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.listingID").value(1L));
    }

    @Test
    public void getListingById_InvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/listings/99"))
                .andExpect(status().isNotFound());
    }

    // ... rest of the tests remain the same ...
}
