package com.example.webservice.service;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.model.ClientRequestDTO;
import com.example.webservice.model.type.ClientType;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.FacilityRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final FacilityRepository facilityRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, FacilityRepository facilityRepository) {
        this.clientRepository = clientRepository;
        this.facilityRepository = facilityRepository;
    }

    @Transactional
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    @Transactional
    public Client createClient(ClientRequestDTO client) {
        ClientType.isValid(client.getType());

        Client newClient = new Client();
        newClient.setAssociatedFacility(getFacility(client.getAssociatedFacilityId()));
        newClient.setType(ClientType.fromString(client.getType()));
        newClient.setAuthentication(client.getAuthentication());
        return clientRepository.save(newClient);

    }

    @Transactional
    public Client updateClient(Long id, ClientRequestDTO updatedClient) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setAssociatedFacility(getFacility(updatedClient.getAssociatedFacilityId()));
                    client.setClientID(id);
                    client.setType(ClientType.fromString(updatedClient.getType()));
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    @Transactional
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    private Facility getFacility(Long id){
        Optional<Facility> f = facilityRepository.findById(id);
        if(f.isPresent()){
            return f.get();
        }
        throw new ResourceNotFoundException("Facility not found with id: " + id);
    }
}

