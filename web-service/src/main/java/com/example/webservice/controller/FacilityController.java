package com.example.webservice.controller;

import com.example.webservice.exception.InvalidClientIDOrAuthException;
import com.example.webservice.exception.InvalidTokenException;
import com.example.webservice.model.Facility;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.service.FacilityService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller for handling operations related to the {@link Facility} entity.
 */
@RestController
@RequestMapping("facility")
public class FacilityController {

    private final FacilityService facilityService;

    /**
     * Constructs a new FacilityController with the specified {@link FacilityService}.
     *
     * @param facilityService the facility service
     */
    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    /**
     * Retrieves a list of all facilities.
     *
     * @return ResponseEntity containing a list of all facilities and an HTTP status.
     *         HttpStatus.OK (200) for success.
     */
    @GetMapping
    public ResponseEntity<List<Facility>> getAllFacilities() {
        List<Facility> facilities = facilityService.getAllFacilities();
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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "auth and id does not match");
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
            @RequestParam Long facilityID,
            @RequestBody FacilityRequestDTO facility) {
        try {
            Facility updatedFacility = facilityService.updateFacility(clientID, auth, facilityID, facility);
            return new ResponseEntity<>(updatedFacility, HttpStatus.OK);
        } catch (InvalidTokenException e) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFacility(@PathVariable Long id) {
        if (facilityService.deleteFacility(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
