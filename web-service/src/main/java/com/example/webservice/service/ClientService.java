package com.example.webservice.service;

import com.example.webservice.model.Client;
import com.example.webservice.repository.ClientRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
    public Client createClient(Client client) {
        // You can add validation or business logic here if needed
        return clientRepository.save(client);
    }

    @Transactional
    public Client updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id)
                .map(client -> {
                    // Update fields here
                    // For example: client.setAuthentication(updatedClient.getAuthentication());
                    // Update other fields as needed
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    @Transactional
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}

