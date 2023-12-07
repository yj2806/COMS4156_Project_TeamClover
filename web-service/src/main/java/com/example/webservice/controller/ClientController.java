package com.example.webservice.controller;

import com.example.webservice.model.Client;
import com.example.webservice.model.model.ClientRequestDTO;
import com.example.webservice.service.ClientService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

/**
 * Rest controller for handling client-related operations.
 * Provides endpoints for creating, updating, retrieving, and deleting clients.
 */
@RestController
@CrossOrigin
@RequestMapping("client")
public class ClientController {
    private final ClientService clientService;

    /**
     * Constructor for dependency injection of the ClientService.
     *
     * @param clientService Service to handle client operations.
     */
    @Autowired
    @SuppressFBWarnings
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    /**
     * Create a new client.
     * @param client client info
     * @return the created client.
     */
    @PostMapping("/create")
    public Client createClient(@RequestBody ClientRequestDTO client) {
        try {
            return clientService.createClient(client);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                            e.getMessage());
        }

    }

    /**
     * Delete client.
     * @param id client id, auth
     * @param auth client authentication
     */
    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable Long id, @RequestParam String auth) {
        try {
            Client client = clientService.getClientById(id);

            if (auth.equals(client.getAuthentication())) {
                clientService.deleteClient(id);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "auth and id does not match");
            }

        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage());
        }

    }
}
