package com.example.webservice.service;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.Listing;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.model.model.ListingRequestDTO;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.ListingRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
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

    @Mock
    private ClientRepository clientRepository;

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
    public void testGetListingById_NotFound() {
        Long id = 1L;
        when(listingRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Listing> result = listingService.getListingById(id);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateListing() {
        Long clientID = 1L;
        String auth = "admin";
        Listing listing = new Listing();
        listing.setPublic(true);
        listing.setAgeRequirement(20);
        listing.setGender("Male");

        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();
        updatedListingDTO.setGender("Male");
        updatedListingDTO.setIsPublic(true);
        updatedListingDTO.setAgeRequirement(20);

        Client client = new Client();
        client.setClientID(clientID);
        client.setAuthentication(auth);
        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));

        when(listingRepository.save(any(Listing.class))).thenReturn(listing);

        Listing result = listingService.createListing(clientID, auth, updatedListingDTO);
        assertNotNull(result);
        assertEquals("Male", result.getGender());
        assertEquals(20, result.getAgeRequirement());
        assertEquals(true, result.isPublic());
    }
    @Test
    public void testCreateListingClientNotFound() {
        Long clientID = 1L;
        String auth = "admin";

        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();

        when(clientRepository.findById(clientID)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> listingService.createListing(clientID, auth, updatedListingDTO));
    }
    @Test
    public void testCreateListingWrongAuth() {
        Long clientID = 1L;
        String auth = "admin";
        String auth2 = "admin2";
        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();

        Client client = new Client();
        client.setClientID(clientID);
        client.setAuthentication(auth2);
        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));
        assertThrows(ResponseStatusException.class,
                () -> listingService.createListing(clientID, auth, updatedListingDTO));
    }

    @Test
    public void testUpdateListing() {
        Long clientID = 1L;
        String auth = "admin";
        Long id = 1L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Client client = new Client();
        client.setAuthentication(auth);
        client.setAssociatedFacility(facility);

        Listing listing = new Listing();
        listing.setListingID(id);
        listing.setPublic(true);
        listing.setAgeRequirement(20);
        listing.setGender("Male");
        listing.setAssociatedFacility(facility);

        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();
        updatedListingDTO.setGender("Male");
        updatedListingDTO.setIsPublic(true);
        updatedListingDTO.setAgeRequirement(20);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));

        when(listingRepository.existsById(id)).thenReturn(true);
        when(listingRepository.findById(id)).thenReturn(Optional.of(listing)).thenReturn(Optional.of(listing));
        when(listingRepository.save(any(Listing.class))).thenReturn(listing);

        Optional<Listing> result = listingService.updateListing(clientID, auth, id, updatedListingDTO);
        assertTrue(result.isPresent());
        Listing res = result.get();
        assertEquals("Male", res.getGender());
        assertEquals(20, res.getAgeRequirement());
        assertEquals(true, res.isPublic());
    }

    @Test
    public void testUpdateListingWrongClientNotFound() {
        Long clientID = 1L;
        String auth = "admin";
        Long id = 1L;

        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();
        updatedListingDTO.setGender("Male");
        updatedListingDTO.setIsPublic(true);
        updatedListingDTO.setAgeRequirement(20);

        when(clientRepository.findById(clientID)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> listingService.updateListing(clientID, auth, id, updatedListingDTO));
    }

    @Test
    public void testUpdateListingWrongAuth() {
        Long clientID = 1L;
        String auth = "admin";
        String auth2 = "admin2";
        Long id = 1L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Client client = new Client();
        client.setAuthentication(auth2);
        client.setAssociatedFacility(facility);

        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();
        updatedListingDTO.setGender("Male");
        updatedListingDTO.setIsPublic(true);
        updatedListingDTO.setAgeRequirement(20);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));
        assertThrows(ResponseStatusException.class,
                () -> listingService.updateListing(clientID, auth, id, updatedListingDTO));
    }

    @Test
    public void testUpdateListingFacilityIDUnmatched() {
        Long clientID = 1L;
        String auth = "admin";
        Long id = 1L;
        Long id2 = 2L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Listing listing = new Listing();
        listing.setListingID(id);
        listing.setPublic(true);
        listing.setAgeRequirement(20);
        listing.setGender("Male");
        listing.setAssociatedFacility(facility);

        Facility facility2 = new Facility();
        facility2.setFacilityID(id2);

        Client client = new Client();
        client.setAuthentication(auth);
        client.setAssociatedFacility(facility2);

        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();
        updatedListingDTO.setGender("Male");
        updatedListingDTO.setIsPublic(true);
        updatedListingDTO.setAgeRequirement(20);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));
        when(listingRepository.existsById(id)).thenReturn(true);
        when(listingRepository.findById(id)).thenReturn(Optional.of(listing)).thenReturn(Optional.of(listing));
        assertThrows(ResponseStatusException.class,
                () -> listingService.updateListing(clientID, auth, id, updatedListingDTO));
    }


    @Test
    public void testUpdateListingNotExisted() {
        Long clientID = 1L;
        String auth = "admin";
        Long id = 1L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Client client = new Client();
        client.setAuthentication(auth);
        client.setAssociatedFacility(facility);

        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();
        updatedListingDTO.setGender("Male");
        updatedListingDTO.setIsPublic(true);
        updatedListingDTO.setAgeRequirement(20);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));

        when(listingRepository.existsById(id)).thenReturn(false);

        assertEquals(Optional.empty(), listingService.updateListing(clientID, auth, id, updatedListingDTO));
    }

    @Test
    public void testUpdateListing_NotFound() {
        Long clientID = 1L;
        String auth = "admin";
        Long id = 1L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Client client = new Client();
        client.setAuthentication(auth);
        client.setAssociatedFacility(facility);

        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();
        updatedListingDTO.setGender("Male");
        updatedListingDTO.setIsPublic(true);
        updatedListingDTO.setAgeRequirement(20);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));
        when(listingRepository.existsById(id)).thenReturn(true);
        when(listingRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> listingService.updateListing(clientID, auth, id, updatedListingDTO));
    }

    @Test
    public void testUpdateListing_NotFound_2() {
        Long clientID = 1L;
        String auth = "admin";
        Long id = 1L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Client client = new Client();
        client.setAuthentication(auth);
        client.setAssociatedFacility(facility);

        Listing listing = new Listing();
        listing.setListingID(id);
        listing.setPublic(true);
        listing.setAgeRequirement(20);
        listing.setGender("Male");
        listing.setAssociatedFacility(facility);

        ListingRequestDTO updatedListingDTO = new ListingRequestDTO();
        updatedListingDTO.setGender("Male");
        updatedListingDTO.setIsPublic(true);
        updatedListingDTO.setAgeRequirement(20);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));

        when(listingRepository.existsById(id)).thenReturn(true);
        when(listingRepository.findById(id)).thenReturn(Optional.of(listing)).thenReturn(Optional.empty());
        when(listingRepository.save(any(Listing.class))).thenReturn(listing);

        assertThrows(ResponseStatusException.class,
                () -> listingService.updateListing(clientID, auth, id, updatedListingDTO));
    }


    @Test
    public void testDeleteListing() {
        Long id = 1L;
        String auth = "admin";
        Long clientID = 1L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Client client = new Client();
        client.setAuthentication(auth);
        client.setAssociatedFacility(facility);

        Listing listing = new Listing();
        listing.setListingID(id);
        listing.setPublic(true);
        listing.setAgeRequirement(20);
        listing.setGender("Male");
        listing.setAssociatedFacility(facility);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));
        when(listingRepository.existsById(id)).thenReturn(true);
        when(listingRepository.findById(id)).thenReturn(Optional.of(listing));

        boolean isDeleted = listingService.deleteListing(clientID, auth, id);
        assertTrue(isDeleted);
        verify(listingRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteListing_IDNotFound() {
        Long id = 1L;
        String auth = "admin";
        Long clientID = 1L;

        when(clientRepository.findById(clientID)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> listingService.deleteListing(clientID, auth, id));
    }

    @Test
    public void testDeleteListing_WrongAuth() {
        Long id = 1L;
        String auth = "admin";
        String auth2 = "admin2";
        Long clientID = 1L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Client client = new Client();
        client.setAuthentication(auth2);
        client.setAssociatedFacility(facility);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));
        assertThrows(ResponseStatusException.class,
                () -> listingService.deleteListing(clientID, auth, id),
                "auth and id does not match");
    }

    @Test
    public void testDeleteListing_ListingNotExisted() {
        Long id = 1L;
        String auth = "admin";
        Long clientID = 1L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Client client = new Client();
        client.setAuthentication(auth);
        client.setAssociatedFacility(facility);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));
        when(listingRepository.existsById(id)).thenReturn(false);

        boolean isDeleted = listingService.deleteListing(clientID, auth, id);
        assertFalse(isDeleted);
        verify(listingRepository, times(0)).deleteById(id);
    }

    @Test
    public void testDeleteListing_ListingNotFound() {
        Long id = 1L;
        String auth = "admin";
        Long clientID = 1L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Client client = new Client();
        client.setAuthentication(auth);
        client.setAssociatedFacility(facility);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));
        when(listingRepository.existsById(id)).thenReturn(true);
        when(listingRepository.findById(id)).thenReturn(Optional.empty());

        Throwable t = assertThrows(ResourceNotFoundException.class,
                () -> listingService.deleteListing(clientID, auth, id));
    }
    @Test
    public void testDeleteListing_FacilityIDUnmatched() {
        Long id = 1L;
        Long id2 = 2L;
        String auth = "admin";
        Long clientID = 1L;

        Facility facility = new Facility();
        facility.setFacilityID(id);

        Client client = new Client();
        client.setAuthentication(auth);
        client.setAssociatedFacility(facility);


        Facility facility2 = new Facility();
        facility2.setFacilityID(id2);

        Listing listing = new Listing();
        listing.setListingID(id);
        listing.setPublic(true);
        listing.setAgeRequirement(20);
        listing.setGender("Male");
        listing.setAssociatedFacility(facility2);

        when(clientRepository.findById(clientID)).thenReturn(Optional.of(client));
        when(listingRepository.existsById(id)).thenReturn(true);
        when(listingRepository.findById(id)).thenReturn(Optional.of(listing));

        Throwable t = assertThrows(ResponseStatusException.class,
                () -> listingService.deleteListing(clientID, auth, id));
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
