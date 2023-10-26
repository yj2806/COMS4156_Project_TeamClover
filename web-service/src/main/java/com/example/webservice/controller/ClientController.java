package com.example.webservice.controller;

import com.example.webservice.model.Client;
import com.example.webservice.model.model.ClientRequestDTO;
import com.example.webservice.service.ClientService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Rest controller for handling client-related operations.
 * Provides endpoints for creating, updating, retrieving, and deleting clients.
 */
@RestController
@RequestMapping("client")
public class ClientController {

    private final ClientService clientService;

    /**
     * Constructor for dependency injection of the ClientService.
     *
     * @param clientService Service to handle client operations.
     */
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


//    // GET request to retrieve all clients
//    @GetMapping
//    public List<Client> getAllClients() {
//        return clientService.getAllClients();
//    }

//    // GET request to retrieve a client by their ID
//    @GetMapping("/{id}")
//    public Client getClientById(@PathVariable Long id) {
//        return clientService.getClientById(id);
//    }

    // POST request to create a new client
    @PostMapping("/create")
    public Client createClient(@RequestBody ClientRequestDTO client) {
        try{
            return clientService.createClient(client);
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

//    // PUT request to update an existing client by their ID
//    @PutMapping("/update/{id}")
//    public Client updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO updatedClient) {
//        return clientService.updateClient(id, updatedClient);
//    }

    // DELETE request to delete a client by their ID
    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable Long id, @RequestParam String auth) {
        try {
            Client client = clientService.getClientById(id);

            if (auth.equals(client.getAuthentication())) {
                clientService.deleteClient(id);
            }else{
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "auth and id does not match");
            }

        }catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
}
