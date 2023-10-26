package com.example.webservice.service;

import com.example.webservice.model.Listing;
import com.example.webservice.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ListingServiceTest {

    @InjectMocks
    private ListingService listingService;

    @Mock
    private ListingRepository listingRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllListings() {
        Listing listing = new Listing();
        when(listingRepository.findAll()).thenReturn(Arrays.asList(listing));

        List<Listing> result = listingService.getAllListings();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(listing, result.get(0));
    }

    @Test
    public void testGetListingById() {
        Long id = 1L;
        Listing listing = new Listing();
        when(listingRepository.findById(id)).thenReturn(Optional.of(listing));

        Optional<Listing> result = listingService.getListingById(id);
        assertTrue(result.isPresent());
        assertEquals(listing, result.get());
    }

    @Test
    public void testCreateListing() {
        Listing listing = new Listing();
        when(listingRepository.save(any())).thenReturn(listing);

        Listing result = listingService.createListing(listing);
        assertNotNull(result);
        assertEquals(listing, result);
    }

    @Test
    public void testUpdateListing() {
        Long id = 1L;
        Listing updatedListing = new Listing();
        when(listingRepository.existsById(id)).thenReturn(true);
        when(listingRepository.save(updatedListing)).thenReturn(updatedListing);

        Optional<Listing> result = listingService.updateListing(id, updatedListing);
        assertTrue(result.isPresent());
        assertEquals(updatedListing, result.get());
    }

    @Test
    public void testDeleteListing() {
        Long id = 1L;
        when(listingRepository.existsById(id)).thenReturn(true);

        boolean isDeleted = listingService.deleteListing(id);
        assertTrue(isDeleted);
        verify(listingRepository, times(1)).deleteById(id);
    }

    @Test
    public void testSearchListingsByLocation() {
        Double latitude = 1.0;
        Double longitude = 1.0;
        Double range = 1.0;
        Listing listing = new Listing();
        when(listingRepository.findListingsByLocation(latitude, longitude, range)).thenReturn(Arrays.asList(listing));

        List<Listing> result = listingService.searchListingsByLocation(latitude, longitude, range);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(listing, result.get(0));
    }
}
