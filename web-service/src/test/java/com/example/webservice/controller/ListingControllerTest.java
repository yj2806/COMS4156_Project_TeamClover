package com.example.webservice.controller;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.Listing;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.model.model.ListingRequestDTO;
import com.example.webservice.model.type.ClientType;
import com.example.webservice.service.ListingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
     * Test case for creating a listing
     */
    @Test
    public void testCreateListing() throws Exception {

        Long clientID = 1L;
        String auth = "abc";
        ListingRequestDTO requestDTO = new ListingRequestDTO();

        Listing createdListing = new Listing();
        createdListing.setPublic(true);

        Mockito.when(listingService.createListing(clientID, auth, requestDTO)).thenReturn(createdListing);

        ObjectMapper mapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/listing/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .param("clientID",  String.valueOf(clientID))
                        .param("auth", auth))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals(content, mapper.writeValueAsString(createdListing));

    }

    /**
     * Test case for updating a listing by its ID.
     */
    @Test
    public void testUpdateListing() throws Exception {
        Long clientID = 1L;
        String auth = "abc";
        Long id= 1L;
        ListingRequestDTO requestDTO = new ListingRequestDTO();
        Listing updatedListing = new Listing();
        updatedListing.setPublic(true);
        Optional<Listing> updatedListingOptional= Optional.of(updatedListing);

        when(listingService.updateListing(clientID, auth, id , requestDTO)).thenReturn(updatedListingOptional);

        ObjectMapper mapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(put("/listing/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .param("clientID",  String.valueOf(clientID))
                        .param("auth", auth)
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals(content, mapper.writeValueAsString(updatedListing));
    }

    @Test
    public void testUpdateListing_NotFound() throws Exception {
        Long clientID = 1L;
        String auth = "abc";
        Long id= 1L;
        ListingRequestDTO requestDTO = new ListingRequestDTO();

        when(listingService.updateListing(clientID, auth, id , requestDTO)).thenReturn(Optional.empty());

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/listing/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .param("clientID",  String.valueOf(clientID))
                        .param("auth", auth)
                        .param("id", String.valueOf(id)))
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
    public void testDeleteListing_NotFound() throws Exception {
        when(listingService.deleteListing(anyLong(), anyString(), anyLong())).thenReturn(false);

        mockMvc.perform(delete("/listing/delete/1")
                        .param("clientID", "1")
                        .param("auth", "validAuthString"))
                .andExpect(status().isNotFound());
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
