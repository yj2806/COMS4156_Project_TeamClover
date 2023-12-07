package com.example.webservice.service;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.Listing;
import com.example.webservice.model.model.ListingRequestDTO;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.FacilityRepository;
import com.example.webservice.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private ListingService listingService;

    // Test data setup
    private Client testClient;
    private Facility testFacility;
    private Listing testListing;
    private ListingRequestDTO testListingRequestDTO;

    @BeforeEach
    public void setup() {
        // Initialize test data
        testClient = new Client(); // Initialize with test data
        testClient.setAuthentication("authToken");

        testFacility = new Facility(); // Initialize with test data
        testFacility.setAssociated_distributorID(1L); // Set the distributor ID

        testListing = new Listing(); // Initialize with test data
        testListing.setAssociatedFacility(testFacility); // Associate the facility with the listing

        testListingRequestDTO = new ListingRequestDTO(); // Initialize with test data
        testListingRequestDTO.setIsPublic(true);

    }


    @Test
    public void testGetListingByIdFound() {
        Long listingId = 1L;
        when(listingRepository.findById(listingId)).thenReturn(Optional.of(testListing));

        var result = listingService.getListingById(listingId);

        assertTrue(result.isPresent());
        assertEquals(testListing, result.get());
        verify(listingRepository).findById(listingId);
    }

    @Test
    public void testGetListingByIdNotFound() {
        Long listingId = 1L;
        when(listingRepository.findById(listingId)).thenReturn(Optional.empty());

        var result = listingService.getListingById(listingId);

        assertFalse(result.isPresent());
        verify(listingRepository).findById(listingId);
    }

    @Test
    public void testCreateListingSuccess() {
        Long clientId = 1L;
        Long facilityId = 1L;
        String authToken = "authToken";

        // Set the authentication token on the test client to match the one used in the test
        testClient.setAuthentication(authToken);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(testClient));
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(testFacility));
        when(listingRepository.save(any(Listing.class))).thenReturn(testListing);

        Listing result = listingService.createListing(clientId, authToken, facilityId, testListingRequestDTO);

        assertNotNull(result);
        assertEquals(testListing, result);
        verify(clientRepository).findById(clientId);
        verify(facilityRepository).findById(facilityId);
        verify(listingRepository).save(any(Listing.class));
    }


    @Test
    public void testCreateListingClientNotFound() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                listingService.createListing(1L, "authToken", 1L, testListingRequestDTO));
    }

    @Test
    public void testUpdateListingSuccess() {
        Long listingId = 1L;
        String authToken = "authToken";

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(testClient));
        when(listingRepository.existsById(listingId)).thenReturn(true);
        when(listingRepository.findById(listingId)).thenReturn(Optional.of(testListing));
        when(listingRepository.save(any(Listing.class))).thenReturn(testListing);

        Optional<Listing> result = listingService.updateListing(listingId, 1L, authToken, testListingRequestDTO);

        assertTrue(result.isPresent());
        assertEquals(testListing, result.get());
        verify(listingRepository).save(any(Listing.class));
    }


    @Test
    public void testUpdateListingClientNotFound() {
        Long listingId = 1L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                listingService.updateListing(listingId, 1L, "authToken", testListingRequestDTO));
    }

    @Test
    public void testUpdateListingNotFound() {
        Long listingId = 1L;
        testClient.setAuthentication("authToken"); // Ensure the client has the correct authentication token
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(testClient));
        when(listingRepository.existsById(listingId)).thenReturn(true); // Assume the listing exists
        when(listingRepository.findById(listingId)).thenReturn(Optional.empty()); // Simulate that the listing is not found

        assertThrows(ResponseStatusException.class, () -> {
            listingService.updateListing(listingId, 1L, "authToken", testListingRequestDTO);
        });
    }

    @Test
    public void testUpdateListingUnauthorized() {
        Long listingId = 1L;
        testClient.setAuthentication("differentAuthToken");
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(testClient));

        assertThrows(ResponseStatusException.class, () ->
                listingService.updateListing(listingId, 1L, "authToken", testListingRequestDTO));
    }

    @Test
    public void testDeleteListingSuccess() {
        Long clientId = 1L;
        Long listingId = 1L;
        String authToken = "authToken";

        // Set the authentication token on the test client to match the one used in the test
        testClient.setAuthentication(authToken);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(testClient));
        when(listingRepository.existsById(listingId)).thenReturn(true);

        // Mock the findById method to return the testListing, which has an associated facility
        when(listingRepository.findById(listingId)).thenReturn(Optional.of(testListing));

        assertTrue(listingService.deleteListing(clientId, authToken, listingId));
        verify(listingRepository).deleteById(listingId);
    }


    @Test
    public void testDeleteListingNotFound() {
        Long clientId = 1L;
        Long listingId = 1L;
        String authToken = "authToken";

        // Set the authentication token on the test client to match the one used in the test
        testClient.setAuthentication(authToken);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(testClient));
        when(listingRepository.existsById(listingId)).thenReturn(false);

        assertFalse(listingService.deleteListing(clientId, authToken, listingId));
        verify(listingRepository, never()).deleteById(anyLong());
    }


//    @Test
//    public void testSearchListings() {
//        when(listingRepository.findListingsByCriteria(any(), any(), any(), any(), any(), any(), anyDouble(), anyDouble(), anyDouble()))
//                .thenReturn(Collections.singletonList(testListing));
//
//        var result = listingService.searchListings(true, 123, "itemList", 18, true, "gender", 40.0, -74.0, 10.0);
//
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        verify(listingRepository).findListingsByCriteria(any(), any(), any(), any(), any(), any(), anyDouble(), anyDouble(), anyDouble());
//    }

    @Test
    public void testSearchListingsWithFilterSuccess() {
        when(listingRepository.findListingsWithFilter(anyDouble(), anyDouble(), anyDouble(), anyString(), any(), any(), anyString()))
                .thenReturn(Collections.singletonList(testListing));

        List<Listing> result = listingService.searchListingsWithFilter(40.0, -74.0, 10.0, "item1|item2", 18, true, "gender");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(listingRepository).findListingsWithFilter(anyDouble(), anyDouble(), anyDouble(), anyString(), any(), any(), anyString());
    }

    @Test
    public void testSearchListingsWithFilterNoResults() {
        when(listingRepository.findListingsWithFilter(anyDouble(), anyDouble(), anyDouble(), anyString(), any(), any(), anyString()))
                .thenReturn(Collections.emptyList());

        List<Listing> result = listingService.searchListingsWithFilter(40.0, -74.0, 10.0, "nonexistent", 18, false, "gender");

        assertTrue(result.isEmpty());
        verify(listingRepository).findListingsWithFilter(anyDouble(), anyDouble(), anyDouble(), anyString(), any(), any(), anyString());
    }

    @Test
    public void testSearchListingsWithGroupCodeSuccess() {
        when(listingRepository.findListingsWithGroupCode(anyDouble(), anyDouble(), anyDouble(), anyInt()))
                .thenReturn(Collections.singletonList(testListing));

        List<Listing> result = listingService.searchListingsWithGroupCode(40.0, -74.0, 10.0, 123);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(listingRepository).findListingsWithGroupCode(anyDouble(), anyDouble(), anyDouble(), anyInt());
    }

    @Test
    public void testSearchListingsWithGroupCodeNoResults() {
        when(listingRepository.findListingsWithGroupCode(anyDouble(), anyDouble(), anyDouble(), anyInt()))
                .thenReturn(Collections.emptyList());

        List<Listing> result = listingService.searchListingsWithGroupCode(40.0, -74.0, 10.0, 999);

        assertTrue(result.isEmpty());
        verify(listingRepository).findListingsWithGroupCode(anyDouble(), anyDouble(), anyDouble(), anyInt());
    }
}