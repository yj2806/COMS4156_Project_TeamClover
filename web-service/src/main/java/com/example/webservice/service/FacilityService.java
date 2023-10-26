package com.example.webservice.service;

import com.example.webservice.model.Facility;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.FacilityRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {

    private final ClientRepository clientRepository;
    private final FacilityRepository facilityRepository;

    @Autowired
    public FacilityService(ClientRepository clientRepository, FacilityRepository facilityRepository) {
        this.clientRepository = clientRepository;
        this.facilityRepository = facilityRepository;
    }

    @Transactional
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    @Transactional
    public Facility getFacilityById(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + id));
    }

//    @Transactional
//    public static Facility createFacility() {
//        Facility newFacility = new Facility();
//        return facilityRepository.save(newFacility);
//
//    }

    @Transactional
    public Facility updateFacility(Long id, FacilityRequestDTO updatedFacility) {
        return facilityRepository.findById(id)
                .map(facility -> {
                    facility.setFacilityID(id);
                    facility.setLatitude(updatedFacility.getLatitude());
                    facility.setLongitude(updatedFacility.getLongitude());
                    facility.setPublic(updatedFacility.getPublic());
                    facility.setEmail(updatedFacility.getEmail());
                    facility.setPhone(updatedFacility.getPhone());
                    facility.setHours(updatedFacility.getHours());
                    return facilityRepository.save(facility);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + id));
    }

    @Transactional
    public void deleteFacility(Long id) {
        facilityRepository.deleteById(id);
    }
}

