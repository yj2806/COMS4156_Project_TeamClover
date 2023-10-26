package com.example.webservice.service;

import com.example.webservice.model.Client;
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

    /**
     * Constructs a new FacilityService with the specified repositories.
     *
     * @param clientRepository   the client repository
     * @param facilityRepository the facility repository
     */
    @Autowired
    public FacilityService(ClientRepository clientRepository, FacilityRepository facilityRepository) {
        this.clientRepository = clientRepository;
        this.facilityRepository = facilityRepository;
    }

    /**
     * Retrieves a list of all facilities.
     *
     * @return the list of all facilities
     */
    @Transactional
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    /**
     * Retrieves a facility based on the given ID.
     *
     * @param id the ID of the facility to retrieve
     * @return the facility with the given ID
     * @throws ResourceNotFoundException if no facility is found with the given ID
     */
    @Transactional
    public Facility getFacilityById(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + id));
    }


    /**
     * Updates a facility with the given data. Requires authentication.
     *
     * @param clientId         the ID of the client making the update request
     * @param auth             the authentication token/string of the client
     * @param id               the ID of the facility to update
     * @param updatedFacility  the data transfer object containing updated facility details
     * @return the updated facility
     * @throws ResourceNotFoundException if no client or facility is found with the given IDs or if authentication fails
     */
    @Transactional
    public Facility updateFacility(Long clientId, String auth,
                                   Long id, FacilityRequestDTO updatedFacility) {
        Client c = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));

        if (!auth.equals(c.getAuthentication())) {
            throw( new ResourceNotFoundException("wrong auth"));
        }
        if (!id.equals(c.getAssociatedFacility())) {
            throw( new ResourceNotFoundException("wrong facilityID"));
        }

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

    /**
     * Deletes a facility based on its ID.
     *
     * @param id the ID of the facility to delete
     * @return true if the facility was found and deleted, false otherwise
     */
    public boolean deleteFacility(Long id) {
        if(facilityRepository.existsById(id)) {
            facilityRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
