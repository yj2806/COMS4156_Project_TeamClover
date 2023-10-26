package com.example.webservice.controller;

import com.example.webservice.model.Facility;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * Retrieves all facilities.
     *
     * @return a list of all facilities
     */
    @GetMapping
    public List<Facility> getAllFacilities() {
        return facilityService.getAllFacilities();
    }

    /**
     * Retrieves a facility by its ID.
     *
     * @param id the ID of the facility
     * @return the facility with the given ID
     */
    @GetMapping("/{id}")
    public Facility getFacilityById(@PathVariable Long id) {
        return facilityService.getFacilityById(id);
    }

    /**
     * Creates a new facility.
     *
     * @param facility the facility data to create
     * @return the created facility
     */
    @PostMapping("/create")
    public Facility createFacility(@RequestBody FacilityRequestDTO facility) {
        return facilityService.createFacility(facility);
    }

    /**
     * Updates an existing facility by its ID.
     *
     * @param id       the ID of the facility to update
     * @param facility the updated facility data
     * @return the updated facility
     */
    @PutMapping("/update/{id}")
    public Facility updateFacility(@PathVariable Long id, @RequestBody FacilityRequestDTO facility) {
        return facilityService.updateFacility(id, facility);
    }

    /**
     * Deletes a facility by its ID.
     *
     * @param id the ID of the facility to delete
     */
    @DeleteMapping("/delete/{id}")
    public void deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
    }
}
