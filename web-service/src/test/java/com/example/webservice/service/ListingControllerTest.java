package com.example.webservice.service;

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
        testListing.setListingID(1);  // assuming int ID type
        // ... set other fields as necessary ...

        when(listingRepository.findAll()).thenReturn(Arrays.asList(testListing));
        when(listingRepository.findById(1)).thenReturn(Optional.of(testListing));
    }

    @Test
    public void getAllListings_ShouldReturnListings() throws Exception {
        mockMvc.perform(get("/listings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].listingID").value(1));
    }

    @Test
    public void getListingById_ValidId_ShouldReturnListing() throws Exception {
        mockMvc.perform(get("/listings/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.listingID").value(1));
    }

    @Test
    public void getListingById_InvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/listings/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addListing_ValidListing_ShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/listings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testListing)))
                .andExpect(status().isCreated());
    }

    @Test
    public void modifyListing_ValidId_ShouldReturnOk() throws Exception {
        mockMvc.perform(put("/listings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testListing)))
                .andExpect(status().isOk());
    }

    @Test
    public void modifyListing_InvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(put("/listings/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testListing)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeListing_ValidId_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/listings/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeListing_InvalidId_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/listings/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void searchListingsByLocation_ValidParameters_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/listings/search")
                        .param("latitude", "0.0")
                        .param("longitude", "0.0"))
                .andExpect(status().isOk());
    }

}
