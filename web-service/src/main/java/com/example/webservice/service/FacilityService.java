package com.example.webservice.service;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.Listing;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.FacilityRepository;
import com.example.webservice.repository.ListingRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FacilityService {

    private final ClientRepository clientRepository;
    private final FacilityRepository facilityRepository;
    private final ListingRepository listingRepository;

    /**
     * Constructs a new FacilityService with the specified repositories.
     *
     * @param clientRepository   the client repository
     * @param facilityRepository the facility repository
     */
    @Autowired
    public FacilityService(ClientRepository clientRepository,
                           FacilityRepository facilityRepository,
                           ListingRepository listingRepository) {
        this.clientRepository = clientRepository;
        this.facilityRepository = facilityRepository;
        this.listingRepository = listingRepository;
    }

    /**
     * Retrieves a list of all public facilities.
     *
     * @return the list of all public facilities
     */
    @Transactional
    public List<Facility> getPublicFacilities() {
        return facilityRepository.findPublicFacilities();
    }

    /**
     * Retrieves a list facilities by clientID.
     * @param clientID   the client ID
     *
     * @return the list of all public facilities
     */
    @Transactional
    public List<Facility> getFacilitiesByClientID(Long clientID) {
        return facilityRepository.findFacilitiesByClientID(clientID);
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
     * Creates a facility with the given data. Requires authentication.
     *
     * @param clientId         the ID of the client making the update request
     * @param facility  the data transfer object containing new facility details
     * @return the updated facility
     * @throws ResourceNotFoundException if no client or facility is found with the given IDs or if authentication fails
     */
    @Transactional
    public Facility createFacility(Long clientId,
                                   FacilityRequestDTO facility) {
        Client c = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));

        try {
            Facility newFacility = new Facility();
            newFacility.setAssociated_distributorID(c.getClientID());
            newFacility.setLatitude(facility.getLatitude());
            newFacility.setLongitude(facility.getLongitude());
            newFacility.setPublic(facility.getIsPublic());
            newFacility.setEmail(facility.getEmail());
            newFacility.setPhone(facility.getPhone());
            newFacility.setHours(facility.getHours());
            return facilityRepository.save(newFacility);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
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
    public Facility updateFacility(Long clientId,
                                   String auth,
                                   Long id,
                                   FacilityRequestDTO updatedFacility) {
        Facility f = facilityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Facility Not Found"));

        if (!clientId.equals(f.getAssociated_distributorID())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "unmatched client and facility");
        }

        return facilityRepository.findById(id)
                .map(facility -> {
                    facility.setFacilityID(id);
                    facility.setAssociated_distributorID(clientId);
                    facility.setLatitude(updatedFacility.getLatitude());
                    facility.setLongitude(updatedFacility.getLongitude());
                    facility.setPublic(updatedFacility.getIsPublic());
                    facility.setEmail(updatedFacility.getEmail());
                    facility.setPhone(updatedFacility.getPhone());
                    facility.setHours(updatedFacility.getHours());
                    return facilityRepository.save(facility);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Facility Not Found"));
    }

    /**
     * Deletes a facility based on its ID.
     *
     * @param clientId the ID of the client associated with the facility
     * @param id the ID of the facility to delete
     * @return true if the facility was found and deleted, false otherwise
     */
    public void deleteFacility(Long clientId, Long id) {
        Facility f = facilityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Facility Not Found"));

        if (!clientId.equals(f.getAssociated_distributorID())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "unmatched client and facility");
        }
        List<Listing> listingID = listingRepository.findListingsByFacilityID(id);
        for (Listing l : listingID) {
            listingRepository.deleteById(l.getListingID());
        }
        facilityRepository.deleteById(id);
    }
}
