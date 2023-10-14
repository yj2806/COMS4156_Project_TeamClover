package com.example.webservice.controller;

import com.example.webservice.model.Facility;
import com.example.webservice.repository.FacilityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityRepository facilityRepository;

    public FacilityController(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @GetMapping
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    @GetMapping("/{id}")
    public Facility getFacilityById(@PathVariable Long id) throws Exception {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new Exception("Facility not found with id: " + id));
    }

    @PostMapping
    public Facility createFacility(@RequestBody Facility facility) {
        return facilityRepository.save(facility);
    }

    @PutMapping("/{id}")
    public Facility updateFacility(@PathVariable Long id, @RequestBody Facility updatedFacility) throws Exception {
        return facilityRepository.findById(id)
                .map(facility -> {
                    // Update fields here
                    return facilityRepository.save(facility);
                })
                .orElseThrow(() -> new Exception("Facility not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteFacility(@PathVariable Long id) {
        facilityRepository.deleteById(id);
    }
}

