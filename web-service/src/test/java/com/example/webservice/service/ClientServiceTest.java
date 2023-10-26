package com.example.webservice.service;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.model.ClientRequestDTO;
import com.example.webservice.model.type.ClientType;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.FacilityRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private FacilityRepository facilityRepository;

    private Client client;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.client = new Client();
        this.client.setClientID(1L);
        this.client.setType(ClientType.DISTRIBUTOR);
        this.client.setAuthentication("test");
        this.client.setAssociatedFacility(new Facility());
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

    }

    @Test
    public void testGetAllClients() {
        List<Client> clients = new ArrayList<>();
        Client client = new Client();
        client.setClientID(1L);
        client.setType(ClientType.DISTRIBUTOR);
        client.setAuthentication("test");
        client.setAssociatedFacility(new Facility());

        clients.add(client);
        client.setClientID(2L);
        clients.add(client);
        // Add some clients to the list
        Mockito.when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetClientById() {
        Long clientId = 1L;
        Client client = new Client();
        client.setClientID(1L);
        client.setType(ClientType.NON_DISTRIBUTOR);
        client.setAuthentication("test");
        client.setAssociatedFacility(new Facility());
        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Client result = clientService.getClientById(clientId);
//        System.out.println(result.getClientID());
        assertEquals(client, result);
    }

    @Test
    public void testGetClientByIdNotFound() {
        Long clientId = 1L;
        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.getClientById(clientId);
        });
    }

//    @Test
//    public void testCreateClient() {
//        ClientRequestDTO clientRequest = new ClientRequestDTO();
//        clientRequest.setType("DISTRIBUTOR");
//        // TODO:Set clientRequest properties
//
//        Facility facility = new Facility();
//        Mockito.when(facilityRepository.findById(clientRequest.getAssociatedFacilityId())).thenReturn(Optional.of(facility));
//
//        Client result = clientService.createClient(clientRequest);
//
//        assertEquals(ClientType.DISTRIBUTOR,result.getType());
//        // TODO: Perform assertions on the result
//    }

//    @Test
//    public void testUpdateClient() {
//        Long clientId = 1L;
//        ClientRequestDTO updatedClient = new ClientRequestDTO();
//        // TODO: Set updatedClient properties
//        updatedClient.setType("DISTRIBUTOR");
//
////        Client existingClient = new Client();
////        existingClient.setClientID(clientId);
//        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(this.client));
//        Mockito.when(facilityRepository.findById(updatedClient.getAssociatedFacilityId())).thenReturn(Optional.of(new Facility()));
//
//        Client result = clientService.updateClient(clientId, updatedClient);
//
//        assertEquals(updatedClient.getType(),result.getType().toString());
//        // TODO: Perform assertions on the result
//    }

    @Test
    public void testUpdateClientNotFound() {
        Long clientId = 1L;
        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.updateClient(clientId, new ClientRequestDTO());
        });
    }

    @Test
    public void testDeleteClient() {
        Long clientId = 1L;

        clientService.deleteClient(clientId);

        Mockito.verify(clientRepository).deleteById(clientId);
    }
}
