package com.example.webservice.controller;

import com.example.webservice.model.Client;
import com.example.webservice.model.model.ClientRequestDTO;
import com.example.webservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);

    }

    @PostMapping("/create")
    public Client createClient(@RequestBody ClientRequestDTO client) {
        return clientService.createClient(client);
    }

    @PutMapping("/update/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO updatedClient) {
        return clientService.updateClient(id,updatedClient);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}

