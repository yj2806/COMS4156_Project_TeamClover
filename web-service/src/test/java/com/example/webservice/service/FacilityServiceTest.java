package com.example.webservice.service;

import com.example.webservice.model.Facility;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.FacilityRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
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

public class FacilityServiceTest {

    @InjectMocks
    private FacilityService facilityService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private FacilityRepository facilityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllFacilities() {
        Facility facility = new Facility();
        when(facilityRepository.findAll()).thenReturn(Arrays.asList(facility));

        List<Facility> result = facilityService.getPublicFacilities();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(facility, result.get(0));
    }

    @Test
    public void testGetFacilityById() {
        Long id = 1L;
        Facility facility = new Facility();
        when(facilityRepository.findById(id)).thenReturn(Optional.of(facility));

        Facility result = facilityService.getFacilityById(id);
        assertNotNull(result);
        assertEquals(facility, result);
    }

    @Test
    public void testGetFacilityById_NotFound() {
        Long id = 1L;
        when(facilityRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> facilityService.getFacilityById(id));
    }

//    @Test
//    public void testCreateFacility() {
//        FacilityRequestDTO facilityDTO = new FacilityRequestDTO();
//        // set the properties if necessary
//        Facility facility = new Facility();
//        when(facilityRepository.save(any())).thenReturn(facility);
//
//        Facility result = facilityService.createFacility(facilityDTO);
//        assertNotNull(result);
//        assertEquals(facility, result);
//    }

//    @Test
//    public void testUpdateFacility() {
//        Long id = 1L;
//        Facility facility = new Facility();
//        FacilityRequestDTO updatedFacilityDTO = new FacilityRequestDTO();
//        // set properties if necessary
//        when(facilityRepository.findById(id)).thenReturn(Optional.of(facility));
//        when(facilityRepository.save(facility)).thenReturn(facility);
//
//        Facility result = facilityService.updateFacility(id, updatedFacilityDTO);
//        assertNotNull(result);
//        assertEquals(facility, result);
//    }
//
//    @Test
//    public void testUpdateFacility_NotFound() {
//        Long id = 1L;
//        FacilityRequestDTO updatedFacilityDTO = new FacilityRequestDTO();
//        // set properties if necessary
//        when(facilityRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> facilityService.updateFacility(id, updatedFacilityDTO));
//    }

//    @Test
//    public void testDeleteFacility() {
//        Long id = 1L;
//        facilityService.deleteFacility(id);
//
//        verify(facilityRepository, times(1)).deleteById(id);
//    }
}
