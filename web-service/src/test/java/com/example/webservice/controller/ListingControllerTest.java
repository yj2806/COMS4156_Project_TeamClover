package com.example.webservice.controller;

import com.example.webservice.model.Listing;
import com.example.webservice.service.ListingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ListingController.class)
public class ListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListingService listingService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Test case for retrieving all listings.
     */
    @Test
    public void testGetAllListings() throws Exception {
        Listing listing = new Listing();
        // Set properties if necessary
        when(listingService.getAllListings()).thenReturn(List.of(listing));

        mockMvc.perform(get("/listing"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(listing))));
    }

    /**
     * Test case for retrieving a listing by its ID.
     */
    @Test
    public void testGetListingById() throws Exception {
        Long id = 1L;
        Listing listing = new Listing();
        // Set properties if necessary
        when(listingService.getListingById(id)).thenReturn(Optional.of(listing));

        mockMvc.perform(get("/listing/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listing)));
    }

    @Test
    public void testGetListingById_NotFound() throws Exception {
        Long id = 1L;
        when(listingService.getListingById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/listing/{id}", id))
                .andExpect(status().isNotFound());
    }

    /**
     * Test case for creating a new listing.
     */
    @Test
    public void testDeleteListing() throws Exception {
        when(listingService.deleteListing(anyLong(), anyString(), anyLong())).thenReturn(true);

        mockMvc.perform(delete("/listing/delete/1")
                        .param("clientID", "1")
                        .param("auth", "validAuthString"))
                .andExpect(status().isNoContent());
    }


    @Test
    public void testSearchListingsByLocation() throws Exception {
        Double latitude = 40.0;
        Double longitude = 50.0;
        Double range = 10.0;
        Listing listing = new Listing();
        // Set properties if necessary
        when(listingService.searchListingsByLocation(latitude, longitude, range)).thenReturn(List.of(listing));

        mockMvc.perform(get("/listing/search")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .param("range", String.valueOf(range)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(listing))));
    }
}
