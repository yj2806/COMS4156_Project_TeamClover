package com.example.webservice.service;

import com.example.webservice.model.Client;
import com.example.webservice.model.Listing;
import com.example.webservice.model.model.ListingRequestDTO;
import com.example.webservice.repository.ListingRepository;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.FacilityRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ClientRepository clientRepository;
    private final FacilityRepository facilityRepository;


    @Autowired
    public ListingService(ListingRepository listingRepository,
                          ClientRepository clientRepository,
                          FacilityRepository facilityRepository) {
        this.listingRepository = listingRepository;
        this.clientRepository = clientRepository;
        this.facilityRepository = facilityRepository;
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Optional<Listing> getListingById(Long id) {
        return listingRepository.findById(id);
    }

    public Listing createListing(Long clientID,
                                 String auth,
                                 ListingRequestDTO listing) {
        // We can add constraint validation here if necessary
        Client c = clientRepository.findById(clientID)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientID));

        if (!auth.equals(c.getAuthentication())) {
            throw( new ResourceNotFoundException("wrong auth"));
        }
        Listing newListing = new Listing();
        newListing.setAssociatedFacility(c.getAssociatedFacility());
        newListing.setPublic(listing.getIsPublic());
        newListing.setGroupCode(listing.getGroupCode());
        newListing.setItemList(listing.getItemList());
        newListing.setAgeRequirement(listing.getAgeRequirement());
        newListing.setVeteranStatus(listing.getVeteranStatus());
        newListing.setGender(listing.getGender());
        return listingRepository.save(newListing);
    }

    public Optional<Listing> updateListing(Long clientID,
                                           String auth,
                                           Long id, ListingRequestDTO updatedListing) {
        Client c = clientRepository.findById(clientID)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientID));

        if (!auth.equals(c.getAuthentication())) {
            throw( new ResourceNotFoundException("wrong auth"));
        }
        if (listingRepository.existsById(id)) {
            Listing to_update = listingRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Listing not found with id: " + id));
            if (to_update.getAssociatedFacility().getFacilityID() != c.getAssociatedFacility().getFacilityID()) {
                throw( new ResourceNotFoundException("Unmatched client and listingID"));
            }

            return Optional.of(listingRepository.findById(id)
                    .map(listing -> {
                        listing.setListingID(id);
                        listing.setAssociatedFacility(c.getAssociatedFacility());
                        listing.setPublic(updatedListing.getIsPublic());
                        listing.setGroupCode(updatedListing.getGroupCode());
                        listing.setItemList(updatedListing.getItemList());
                        listing.setAgeRequirement(updatedListing.getAgeRequirement());
                        listing.setVeteranStatus(updatedListing.getVeteranStatus());
                        listing.setGender(updatedListing.getGender());
                        return listingRepository.save(listing);
                    })
                    .orElseThrow(() -> new ResourceNotFoundException("Listing not found with id: " + id)));
        }
        return Optional.empty();
    }

    public boolean deleteListing(Long clientID,
                                 String auth,
                                 Long id) {
        Client c = clientRepository.findById(clientID)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientID));

        if (!auth.equals(c.getAuthentication())) {
            throw( new ResourceNotFoundException("wrong auth"));
        }

        if (listingRepository.existsById(id)) {
            Listing to_delete = listingRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Listing not found with id: " + id));
            if (to_delete.getAssociatedFacility().getFacilityID() != c.getAssociatedFacility().getFacilityID()) {
                throw( new ResourceNotFoundException("Unmatched client and listingID"));
            }
            listingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Listing> searchListingsByLocation(Double latitude, Double longitude, Double range) {
        return listingRepository.findListingsByLocation(latitude, longitude, range);
    }

}
