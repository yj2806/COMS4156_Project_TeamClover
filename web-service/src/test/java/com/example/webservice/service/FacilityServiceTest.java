package com.example.webservice.service;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.FacilityRepository;
import com.example.webservice.repository.ListingRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FacilityServiceTest {

    @InjectMocks
    private FacilityService facilityService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private FacilityRepository facilityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPublicFacilitiesSuccess() {
        List<Facility> expectedFacilities = Arrays.asList(new Facility(), new Facility());
        when(facilityRepository.findPublicFacilities()).thenReturn(expectedFacilities);

        List<Facility> actualFacilities = facilityService.getPublicFacilities();

        assertEquals(expectedFacilities, actualFacilities);
    }

    @Test
    public void getFacilitiesByClientIDSuccess() {
        Long clientID = 1L;
        List<Facility> expectedFacilities = Arrays.asList(new Facility());
        when(facilityRepository.findFacilitiesByClientID(clientID)).thenReturn(expectedFacilities);

        List<Facility> actualFacilities = facilityService.getFacilitiesByClientID(clientID);

        assertEquals(expectedFacilities, actualFacilities);
    }

    @Test
    public void getFacilityByIdSuccess() {
        Long id = 1L;
        Facility expectedFacility = new Facility();
        when(facilityRepository.findById(id)).thenReturn(Optional.of(expectedFacility));

        Facility actualFacility = facilityService.getFacilityById(id);

        assertEquals(expectedFacility, actualFacility);
    }

    @Test
    public void getFacilityByIdNotFound() {
        Long id = 1L;
        when(facilityRepository.findById(id)).thenReturn(Optional.empty());

        // Assert that the method call throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            facilityService.getFacilityById(id);
        });
    }


    @Test
    public void createFacilitySuccess() {
        Long clientId = 1L;
        FacilityRequestDTO facilityDTO = new FacilityRequestDTO(); // Initialize with test data
        facilityDTO.setIsPublic(true);
        Facility expectedFacility = new Facility();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));
        when(facilityRepository.save(any(Facility.class))).thenReturn(expectedFacility);

        Facility actualFacility = facilityService.createFacility(clientId, facilityDTO);

        assertEquals(expectedFacility, actualFacility);
    }

    @Test
    public void createFacilityClientNotFound() {
        Long clientId = 1L;
        FacilityRequestDTO facilityDTO = new FacilityRequestDTO(); // Initialize with test data
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Assert that the method call throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            facilityService.createFacility(clientId, facilityDTO);
        });
    }


    @Test
    public void updateFacilitySuccess() {
        Long clientId = 1L;
        Long facilityId = 1L;
        FacilityRequestDTO updatedFacilityDTO = new FacilityRequestDTO();
        updatedFacilityDTO.setIsPublic(true); // Set a value for isPublic
        // Initialize other necessary fields of updatedFacilityDTO

        Facility existingFacility = new Facility();
        existingFacility.setAssociated_distributorID(clientId);
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(existingFacility));
        when(facilityRepository.save(any(Facility.class))).thenReturn(existingFacility);

        Facility updatedFacility = facilityService.updateFacility(clientId, "validAuth", facilityId, updatedFacilityDTO);

        assertNotNull(updatedFacility);
    }


    @Test
    public void updateFacilityUnauthorized() {
        Long clientId = 1L;
        Long facilityId = 1L;
        FacilityRequestDTO updatedFacilityDTO = new FacilityRequestDTO(); // Initialize with test data
        Facility existingFacility = new Facility();
        existingFacility.setAssociated_distributorID(2L); // Different client ID
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(existingFacility));

        // Assert that the method call throws a ResponseStatusException
        assertThrows(ResponseStatusException.class, () -> {
            facilityService.updateFacility(clientId, "validAuth", facilityId, updatedFacilityDTO);
        });
    }


    @Test
    public void deleteFacilitySuccess() {
        Long clientId = 1L;
        Long facilityId = 1L;
        Facility facility = new Facility();
        facility.setAssociated_distributorID(clientId);
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));
        when(listingRepository.findListingsByFacilityID(facilityId)).thenReturn(Collections.emptyList());

        facilityService.deleteFacility(clientId, facilityId);

        verify(facilityRepository).deleteById(facilityId);
    }


    @Test
    public void deleteFacilityUnauthorized() {
        Long clientId = 1L;
        Long facilityId = 1L;
        Facility facility = new Facility();
        facility.setAssociated_distributorID(2L); // Different client ID
        when(facilityRepository.findById(facilityId)).thenReturn(Optional.of(facility));

        // Assert that the method call throws a ResponseStatusException
        assertThrows(ResponseStatusException.class, () -> {
            facilityService.deleteFacility(clientId, facilityId);
        });
    }

}




