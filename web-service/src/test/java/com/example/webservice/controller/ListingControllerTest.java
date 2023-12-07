package com.example.webservice.controller;

import com.example.webservice.controller.ListingController;
import com.example.webservice.model.Facility;
import com.example.webservice.model.Listing;
import com.example.webservice.model.model.ListingRequestDTO;
import com.example.webservice.service.ListingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collections;
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

    Listing testListing = new Listing();

    private ListingRequestDTO testListingRequestDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        testListing = new Listing();

        testListingRequestDTO = new ListingRequestDTO();
        testListingRequestDTO.setIsPublic(true);
    }

    @Test
    public void testGetAllListings() throws Exception {
        Listing listing = new Listing();
        // Set properties if necessary
        when(listingService.getAllListings()).thenReturn(List.of(listing));

        mockMvc.perform(get("/listing"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(listing))));
    }

    @Test
    public void testGetListingByIdFound() throws Exception {
        Long listingId = 1L;
        Listing mockListing = new Listing();
        when(listingService.getListingById(listingId)).thenReturn(Optional.of(mockListing));

        mockMvc.perform(get("/listing/{id}", listingId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockListing)));
    }

    @Test
    public void testGetListingByIdNotFound() throws Exception {
        Long listingId = 1L;
        when(listingService.getListingById(listingId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/listing/{id}", listingId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateListingSuccess() throws Exception {

        Listing mockListing = new Listing();
        // Initialize necessary fields of mockListing

        when(listingService.createListing(anyLong(), anyString(), anyLong(), any(ListingRequestDTO.class)))
                .thenReturn(mockListing);

        mockMvc.perform(post("/listing/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testListingRequestDTO))
                        .param("clientID", "123") // Add the clientID parameter
                        .param("auth", "authToken") // Add the auth parameter if needed
                        .param("facilityID", "456")) // Add the facilityID parameter if needed
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockListing)));

        verify(listingService).createListing(anyLong(), anyString(), anyLong(), any(ListingRequestDTO.class));
    }

    @Test
    public void testUpdateListingSuccess() throws Exception {
        Long listingId = 1L;
        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();
        updatedListingDTO.setIsPublic(true); // Set a value for isPublic
        // Set other necessary properties on updatedListingDTO

        Listing updatedListing = new Listing(); // Initialize with test data
        updatedListing.setListingID(listingId); // Make sure this sets a non-null value
        // Set other necessary properties on updatedListing

        when(listingService.updateListing(eq(listingId), anyLong(), anyString(), any(ListingRequestDTO.class)))
                .thenReturn(Optional.of(updatedListing));

        mockMvc.perform(put("/listing/update/{id}", listingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedListingDTO))
                        .param("clientID", "123")
                        .param("auth", "authToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listingID", is(1))); // Adjusted to match the actual field name

        verify(listingService).updateListing(eq(listingId), anyLong(), anyString(), any(ListingRequestDTO.class));
    }



    @Test
    public void testUpdateListingNotFound() throws Exception {
        Long listingId = 1L;
        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();
        updatedListingDTO.setIsPublic(true); // Ensure all necessary fields are set

        // Mock to return empty Optional, simulating not found
        when(listingService.updateListing(eq(listingId), anyLong(), anyString(), any(ListingRequestDTO.class)))
                .thenReturn(Optional.empty());

        // Perform the request and expect a status other than 500, based on your logic
        mockMvc.perform(put("/listing/update/{id}", listingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedListingDTO))
                        .param("clientID", "123")
                        .param("auth", "authToken"))
                .andExpect(status().isNotFound()); // Update this based on expected behavior

        verify(listingService).updateListing(eq(listingId), anyLong(), anyString(), any(ListingRequestDTO.class));
    }



    @Test
    public void testDeleteListingSuccess() throws Exception {
        Long listingId = 1L;
        when(listingService.deleteListing(anyLong(), anyString(), eq(listingId))).thenReturn(true);

        mockMvc.perform(delete("/listing/delete/{id}", listingId)
                        .param("clientID", "123")
                        .param("auth", "authToken"))
                .andExpect(status().isNoContent());

        verify(listingService).deleteListing(anyLong(), anyString(), eq(listingId));
    }

    @Test
    public void testDeleteListingNotFound() throws Exception {
        Long listingId = 1L;
        when(listingService.deleteListing(anyLong(), anyString(), eq(listingId))).thenReturn(false);

        mockMvc.perform(delete("/listing/delete/{id}", listingId)
                        .param("clientID", "123")
                        .param("auth", "authToken"))
                .andExpect(status().isNotFound());

        verify(listingService).deleteListing(anyLong(), anyString(), eq(listingId));
    }

    @Test
    public void testSearchListings() throws Exception {
        when(listingService.searchListings(any(), any(), any(), any(), any(), any(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(Collections.singletonList(new Listing()));

        mockMvc.perform(get("/listing/search")
                        .param("latitude", "40.7128")
                        .param("longitude", "-74.0060")
                        .param("range", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(listingService).searchListings(any(), any(), any(), any(), any(), any(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    public void testSearchWithFilter() throws Exception {
        Listing mockListing = new Listing();
        mockListing.setListingID(1L);
        mockListing.setItemList("item1|item2");
        mockListing.setAgeRequirement(Integer.valueOf("18"));

        Facility testFacility = new Facility(); // Initialize with test data
        testFacility.setAssociated_distributorID(1L); // Set the distributor ID
        testFacility.setLatitude(Float.parseFloat("45"));
        testFacility.setLongitude(Float.parseFloat("-75"));

        mockListing.setAssociatedFacility(testFacility); // Associate the facility with the listing


        when(listingService.searchListingsWithFilter(eq(40.7128), eq(-74.0060), eq(10.0), eq("item1|item2"), eq(18), any(), anyString()))
                .thenReturn(Collections.singletonList(mockListing));


        mockMvc.perform(get("/listing/search_with_filter")
                        .param("latitude", "40.7128")
                        .param("longitude", "-74.0060")
                        .param("range", "10")
                        .param("itemContained", "item1|item2")
                        .param("age", "18"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(listingService).searchListingsWithFilter(anyDouble(), anyDouble(), anyDouble(), anyString(), any(), any(), anyString());
    }


    @Test
    public void testSearchListingsWithGroupCode() throws Exception {
        when(listingService.searchListingsWithGroupCode(anyDouble(), anyDouble(), anyDouble(), anyInt()))
                .thenReturn(Collections.singletonList(new Listing())); // Set properties as needed

        mockMvc.perform(get("/listing/search_with_group_code")
                        .param("latitude", "40.7128")
                        .param("longitude", "-74.0060")
                        .param("range", "10")
                        .param("groupCode", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(listingService).searchListingsWithGroupCode(anyDouble(), anyDouble(), anyDouble(), anyInt());
    }

}
