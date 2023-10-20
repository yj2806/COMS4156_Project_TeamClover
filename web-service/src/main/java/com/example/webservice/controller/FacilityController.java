package com.example.webservice.controller;
import com.example.webservice.model.Client;
import com.example.webservice.model.model.ClientRequestDTO;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.webservice.model.Facility;
import org.springframework.web.bind.annotation.*;
import com.example.webservice.service.FacilityService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("facility")
public class FacilityController {


    private final FacilityService facilityService;

    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public List<Facility> getAllFacilities() {
        return facilityService.getAllFacilities();
    }

    @GetMapping("/{id}")
    public Facility getFacilityById(@PathVariable Long id) {
        return facilityService.getFacilityById(id);

    }

    @PostMapping("/create")
    public Facility createFacility(@RequestBody FacilityRequestDTO facility) {
        return facilityService.createFacility(facility);
    }

    @PutMapping("/update/{id}")
    public Facility updateFacility(@PathVariable Long id, @RequestBody FacilityRequestDTO facility) {
        return facilityService.updateFacility(id,facility);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
    }

//    @RequestMapping("/getAllFacility")
//    @ResponseBody
//    public List<Facility> findAll() {
//        List<Facility> list = new ArrayList<Facility>();
//        list = facilityRepository.findAll();
//        return list;
//    }

}

