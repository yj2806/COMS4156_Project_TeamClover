package com.example.webservice.service;

import com.example.webservice.model.*;
import com.example.webservice.model.model.ClientRequestDTO;
import com.example.webservice.repository.*;
import com.example.webservice.service.ClientService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private FacilityRepository facilityRepository;
    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ClientService clientService;

    private Client testClient;
    private ClientRequestDTO testClientRequestDTO;

    @BeforeEach
    public void setup() {
        testClient = new Client(); // Initialize with test data
        testClientRequestDTO = new ClientRequestDTO(); // Initialize with test data
        testClientRequestDTO.setType("DISTRIBUTOR");
    }

    @Test
    public void testGetClientByIdFound() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(testClient));

        Client result = clientService.getClientById(clientId);

        assertNotNull(result);
        assertEquals(testClient, result);
        verify(clientRepository).findById(clientId);
    }

    @Test
    public void testGetClientByIdNotFound() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientById(clientId));
    }

    @Test
    public void testCreateClientSuccess() {
        when(clientRepository.save(any(Client.class))).thenReturn(testClient);

        Client result = clientService.createClient(testClientRequestDTO);

        assertNotNull(result);
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    public void testCreateClientInvalidType() {
        testClientRequestDTO.setType("INVALID_TYPE");

        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(testClientRequestDTO));
    }

    @Test
    public void testDeleteClient() {
        Long clientId = 1L;
        when(facilityRepository.findFacilitiesByClientID(clientId)).thenReturn(Collections.emptyList());

        clientService.deleteClient(clientId);

        verify(clientRepository).deleteById(clientId);
        verify(facilityRepository).findFacilitiesByClientID(clientId);
        verify(listingRepository, never()).deleteById(anyLong());
    }

}
