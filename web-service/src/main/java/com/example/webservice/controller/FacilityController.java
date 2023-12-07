package com.example.webservice.controller;

import com.example.webservice.exception.InvalidClientIDOrAuthException;
import com.example.webservice.exception.InvalidTokenException;
import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.service.FacilityService;
import com.example.webservice.service.ClientService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

/**
 * Controller for handling operations related to the {@link Facility} entity.
 */
@RestController
@CrossOrigin
@RequestMapping("facility")
public class FacilityController {

    private final FacilityService facilityService;
    private final ClientService clientService;

    /**
     * Constructs a new FacilityController with the specified {@link FacilityService}.
     *
     * @param facilityService the facility service
     */
    @Autowired
    @SuppressFBWarnings
    public FacilityController(FacilityService facilityService, ClientService clientService) {
        this.facilityService = facilityService;
        this.clientService = clientService;
    }

    /**
     * Retrieves a list of all public facilities.
     *
     * @return ResponseEntity containing a list of all public facilities and an HTTP status.
     *         HttpStatus.OK (200) for success.
     */
    @GetMapping
    public ResponseEntity<List<Facility>> getAllFacilities() {
        List<Facility> facilities = facilityService.getPublicFacilities();
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }

    /**
     * Retrieves a list of facilities associated with client.
     *
     * @return ResponseEntity containing a list of facilities and an HTTP status.
     *         HttpStatus.OK (200) for success.
     */
    @GetMapping("/by_client/{clientID}")
    public ResponseEntity<List<Facility>> getFacilityByClient(@PathVariable Long clientID,
                                                              @RequestParam String auth) {
        try {
            Client client = clientService.getClientById(clientID);

            if (!auth.equals(client.getAuthentication())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "auth and id does not match");
            }
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage());
        }

        List<Facility> facilities = facilityService.getFacilitiesByClientID(clientID);
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }

    /**
     * Retrieves information on facility with a specific ID.
     *
     * @param id the ID of the facility
     * @return ResponseEntity containing the facility with the given ID and an HTTP status.
     *         HttpStatus.OK (200) for success.
     *         HttpStatus.UNAUTHORIZED (401) for invalid token.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Facility> getFacilityById(@PathVariable Long id) {
        try {
            Facility facility = facilityService.getFacilityById(id);
            return new ResponseEntity<>(facility, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "unknown facility");
        }
    }

    /**
     * Create a new facility
     *
     * @param clientID the ID of the client associated with the facility
     * @param auth the authentication string of the client
     * @param facility the facility data
     * @return ResponseEntity containing the updated facility and an HTTP status.
     *         HttpStatus.OK (200) for success.
     *         HttpStatus.UNAUTHORIZED (401) for invalid token.
     *         HttpStatus.NOT_FOUND (404) for invalid client ID or authentication.
     */
    @PostMapping("/create")
    public ResponseEntity<Facility> createFacility(
            @RequestParam Long clientID,
            @RequestParam String auth,
            @RequestBody FacilityRequestDTO facility) {

        try {
            Client client = clientService.getClientById(clientID);

            if (!auth.equals(client.getAuthentication())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "auth and id does not match");
            }
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage());
        }
        try {
            Facility newFacility = facilityService.createFacility(clientID, facility);
            return new ResponseEntity<>(newFacility, HttpStatus.OK);
        } catch (InvalidTokenException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (InvalidClientIDOrAuthException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates information for an existing facility. Requires client authentication.
     *
     * @param clientID the ID of the client associated with the facility
     * @param auth the authentication string of the client
     * @param facilityID the ID of the facility
     * @param facility the updated facility data
     * @return ResponseEntity containing the updated facility and an HTTP status.
     *         HttpStatus.OK (200) for success.
     *         HttpStatus.UNAUTHORIZED (401) for invalid token.
     *         HttpStatus.NOT_FOUND (404) for invalid client ID or authentication.
     */
    @PutMapping("/update/{facilityID}")
    public ResponseEntity<Facility> updateFacility(
            @RequestParam Long clientID,
            @RequestParam String auth,
            @PathVariable Long facilityID,
            @RequestBody FacilityRequestDTO facility) {

        try {
            Client client = clientService.getClientById(clientID);

            if (!auth.equals(client.getAuthentication())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "auth and id does not match");
            }
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Client Not Found");
        }
        Facility updatedFacility = facilityService.updateFacility(clientID, auth, facilityID, facility);
        return new ResponseEntity<>(updatedFacility, HttpStatus.OK);
    }

    /**
     * Delete a facility.
     *
     * @param clientID the ID of the client associated with the facility
     * @param auth the authentication string of the client
     * @param id the ID of the facility
     * @return ResponseEntity containing the updated facility and an HTTP status.
     *         HttpStatus.OK (200) for success.
     *         HttpStatus.UNAUTHORIZED (401) for invalid token.
     *         HttpStatus.NOT_FOUND (404) for invalid client ID or authentication.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFacility(@RequestParam Long clientID,
                                               @RequestParam String auth,
                                               @PathVariable Long id) {

        try {
            Client client = clientService.getClientById(clientID);

            if (!auth.equals(client.getAuthentication())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "auth and id does not match");
            }
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Client Not Found");
        }

        facilityService.deleteFacility(clientID, id);
        return new ResponseEntity<>("facility " + id + " deleted", HttpStatus.OK);
    }


}
