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

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ClientRepository clientRepository;
    private final FacilityRepository facilityRepository;

    /**
     * Constructs a new ListingService with the specified repositories.
     *
     * @param listingRepository the listing repository
     * @param clientRepository  the client repository
     * @param facilityRepository the facility repository
     */
    @Autowired
    public ListingService(ListingRepository listingRepository,
                          ClientRepository clientRepository,
                          FacilityRepository facilityRepository) {
        this.listingRepository = listingRepository;
        this.clientRepository = clientRepository;
        this.facilityRepository = facilityRepository;
    }

    /**
     * Retrieves a list of all listings.
     *
     * @return the list of all listings
     */
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    /**
     * Retrieves a listing by its ID.
     *
     * @param id the ID of the listing to retrieve
     * @return an Optional containing the retrieved listing, or empty if not found
     */
    public Optional<Listing> getListingById(Long id) {
        return listingRepository.findById(id);
    }

    /**
     * Creates a new listing with the provided details.
     *
     * @param clientID the ID of the client creating the listing
     * @param auth     the authentication token of the client
     * @param listing  the details of the new listing
     * @return the created listing
     */
    public Listing createListing(Long clientID,
                                 String auth,
                                 ListingRequestDTO listing) {
        // Constraints and validation could be added here if necessary.
        Client c = clientRepository.findById(clientID)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientID));

        if (!auth.equals(c.getAuthentication())) {
            throw (new ResourceNotFoundException("wrong auth"));
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

    /**
     * Updates an existing listing with the provided details.
     *
     * @param clientID       the ID of the client updating the listing
     * @param auth           the authentication token of the client
     * @param id             the ID of the listing to update
     * @param updatedListing the new details for the listing
     * @return an Optional containing the updated listing, or empty if not found
     */
    public Optional<Listing> updateListing(Long clientID,
                                           String auth,
                                           Long id,
                                           ListingRequestDTO updatedListing) {
        Client c = clientRepository.findById(clientID)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientID));

        if (!auth.equals(c.getAuthentication())) {
            throw (new ResourceNotFoundException("wrong auth"));
        }
        if (listingRepository.existsById(id)) {
            Listing toUpdate = listingRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Listing not found with id: " + id));
            if (!toUpdate.getAssociatedFacility().getFacilityID().equals(
                    c.getAssociatedFacility().getFacilityID())) {
                throw (new ResourceNotFoundException("Unmatched client and listingID"));
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

    /**
     * Deletes a listing by its ID.
     *
     * @param clientID the ID of the client requesting the deletion
     * @param auth     the authentication token of the client
     * @param id       the ID of the listing to delete
     * @return true if the listing was deleted, false otherwise
     */
    public boolean deleteListing(Long clientID,
                                 String auth,
                                 Long id) {
        Client c = clientRepository.findById(clientID)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientID));

        if (!auth.equals(c.getAuthentication())) {
            throw (new ResourceNotFoundException("wrong auth"));
        }

        if (listingRepository.existsById(id)) {
            Listing toDelete = listingRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Listing not found with id: " + id));
            if (toDelete.getAssociatedFacility().getFacilityID().equals(
                    c.getAssociatedFacility().getFacilityID())) {
                throw (new ResourceNotFoundException("Unmatched client and listingID"));
            }
            listingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Searches for listings based on a location and range.
     *
     * @param latitude  the latitude of the search center
     * @param longitude the longitude of the search center
     * @param range     the range (in units) from the center to search for listings
     * @return a list of listings found within the specified range of the location
     */
    public List<Listing> searchListingsByLocation(Double latitude,
                                                  Double longitude,
                                                  Double range) {
        return listingRepository.findListingsByLocation(latitude, longitude, range);
    }

}
