package com.example.webservice.service;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.Listing;
import com.example.webservice.model.model.ListingRequestDTO;
import com.example.webservice.repository.ListingRepository;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.FacilityRepository;
import com.example.webservice.service.FacilityService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ClientRepository clientRepository;
    private final FacilityRepository facilityRepository;
    private final FacilityService facilityService;

    /**
     * Constructs a new ListingService with the specified repositories.
     *
     * @param listingRepository the listing repository
     * @param clientRepository  the client repository
     * @param facilityRepository the facility repository
     */
    @Autowired
    @SuppressFBWarnings
    public ListingService(ListingRepository listingRepository,
                          ClientRepository clientRepository,
                          FacilityRepository facilityRepository,
                          FacilityService facilityService) {
        this.listingRepository = listingRepository;
        this.clientRepository = clientRepository;
        this.facilityRepository = facilityRepository;
        this.facilityService = facilityService;
    }

//    /**
//     * Retrieves a list of all listings.
//     *
//     * @return the list of all listings
//     */
//    public List<Listing> getAllListings() {
//        return listingRepository.findAll();
//    }

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
     * Retrieves a list of listings by clientID.
     * @param clientID   the client ID
     * @return the list of all public listings
     */
    @Transactional
    public List<Listing> getListingsByClientID(Long clientID) {
        List<Listing> listings = new ArrayList<>();
        for (Facility f : facilityService.getFacilitiesByClientID(clientID)) {
            listings.addAll(listingRepository.findListingsByFacilityID(f.getFacilityID()));
        }
        return listings;
    }


    /**
     * Creates a new listing with the provided details.
     *
     * @param clientID the ID of the client creating the listing
     * @param auth     the authentication token of the client
     * @param facilityID the ID of the facility the listing belongs to
     * @param listing  the details of the new listing
     * @return the created listing
     */
    public Listing createListing(Long clientID,
                                 String auth,
                                 Long facilityID,
                                 ListingRequestDTO listing) {
        // Constraints and validation could be added here if necessary.
        Client c = clientRepository.findById(clientID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Client not found with id: " + clientID));
        if (!auth.equals(c.getAuthentication())) {
            throw (new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "wrong auth"));
        }

        Facility f = facilityRepository.findById(facilityID)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id: " + facilityID));

        if (!clientID.equals(f.getAssociated_distributorID())) {
            throw (new ResourceNotFoundException("unmatched facilityID and client"));
        }

        try {
            Listing newListing = new Listing();
            newListing.setAssociatedFacility(f);
            newListing.setPublic(listing.getIsPublic());
            newListing.setGroupCode(listing.getGroupCode());
            newListing.setItemList(listing.getItemList());
            newListing.setAgeRequirement(listing.getAgeRequirement());
            newListing.setVeteranStatus(listing.getVeteranStatus());
            newListing.setGender(listing.getGender());
            return listingRepository.save(newListing);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }

    }

    /**
     * Updates an existing listing with the provided details.
     *
     *
     * @param clientID       the ID of the client updating the listing
     * @param auth           the authentication token of the client
     * @param id             the ID of the listing to update
     * @param updatedListing the new details for the listing
     * @return an Optional containing the updated listing, or empty if not found
     */
    public Optional<Listing> updateListing(Long id,
                                           Long clientID,
                                           String auth,
                                           ListingRequestDTO updatedListing) {
        Client c = clientRepository.findById(clientID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Client not found with id: " + clientID));

        if (!auth.equals(c.getAuthentication())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Authentication failed");
        }
        if (listingRepository.existsById(id)) {
            Listing toUpdate = listingRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "not exist"));
            if (!toUpdate.getAssociatedFacility().getAssociated_distributorID()
                    .equals(clientID)) {
                throw (new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "unmatched info"));
            }

            return Optional.of(listingRepository.findById(id)
                    .map(listing -> {
                        listing.setListingID(id);
                        listing.setAssociatedFacility(toUpdate.getAssociatedFacility());
                        listing.setPublic(updatedListing.getIsPublic());
                        listing.setGroupCode(updatedListing.getGroupCode());
                        listing.setItemList(updatedListing.getItemList());
                        listing.setAgeRequirement(updatedListing.getAgeRequirement());
                        listing.setVeteranStatus(updatedListing.getVeteranStatus());
                        listing.setGender(updatedListing.getGender());
                        return listingRepository.save(listing);
                    })
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "not exist")));
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Client not found"));

        if (!auth.equals(c.getAuthentication())) {

            throw (new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "auth and id does not match"));
        }

        if (listingRepository.existsById(id)) {
            Listing toDelete = listingRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Listing not found with id: " + id));
            if (!toDelete.getAssociatedFacility().getAssociated_distributorID()
                    .equals(clientID)) {
                throw (new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "unmatched info"));
            }
            listingRepository.deleteById(id);
            return true;
        }
        return false;
    }

//    Deprecated
//    /**
//     * Searches for listings based on a combination of criteria including public/private status,
//     * group code, item list, age requirement, veteran status, gender, and location.
//     *
//     * This method combines both non-location and location-based criteria to filter listings.
//     * It leverages a single database query to efficiently retrieve listings that match all specified criteria.
//     *
//     * @param isPublic        Boolean flag indicating if the listing is public. If null, this criterion is ignored.
//     * @param groupCode       Integer representing the group code for private listings. If null, this criterion is ignored.
//     * @param itemList        String representing the list of items in the listing. If null, this criterion is ignored.
//     * @param ageRequirement  Integer specifying the age requirement for the listing. If null, this criterion is ignored.
//     * @param veteranStatus   Boolean flag indicating if the listing is for veterans. If null, this criterion is ignored.
//     * @param gender          String indicating the gender requirement for the listing. If null, this criterion is ignored.
//     * @param latitude        Double representing the latitude for location-based filtering.
//     * @param longitude       Double representing the longitude for location-based filtering.
//     * @param range           Double representing the range (in appropriate units) for location-based filtering.
//     * @return                A list of {@link Listing} objects that match the specified criteria.
//     */
//    public List<Listing> searchListings(Boolean isPublic, Integer groupCode, String itemList,
//                                        Integer ageRequirement, Boolean veteranStatus, String gender,
//                                        Double latitude, Double longitude, Double range) {
//        // Call the repository method with all criteria
//        return listingRepository.findListingsByCriteria(isPublic, groupCode, itemList,
//                ageRequirement, veteranStatus, gender,
//                latitude, longitude, range);
//    }


    /**
     * Searches for listings based on a combination of criteria including location, item list, age requirement,
     * veteran status, and gender.
     *
     * @param latitude The latitude of the search center.
     * @param longitude The longitude of the search center.
     * @param range The range (in specified units) to search for listings.
     * @param itemList A delimited string of items to include in the search. Items are separated by '|'.
     * @param age The age requirement for the listings.
     * @param veteranStatus The veteran status requirement for the listings.
     * @param gender The gender requirement for the listings.
     * @return A list of {@link Listing} objects that match the specified criteria.
     */
    public List<Listing> searchListingsWithFilter(Double latitude, Double longitude, Double range,
                                                  String itemList, Integer age, Boolean veteranStatus, String gender) {
        // Process the itemList if it's not null or empty
        String processedItemList = null;
        if (itemList != null && !itemList.isEmpty()) {
            // Split the itemList by the delimiter and join with SQL wildcards
            processedItemList = "%" + itemList.replace("|", "%|%") + "%";
        }

        // Call the repository method with the processed item list and other criteria
        return listingRepository.findListingsWithFilter(latitude, longitude, range, processedItemList, age, veteranStatus, gender);
    }


    /**
     * Searches for listings based on a group code and location criteria.
     *
     * @param latitude The latitude of the search center.
     * @param longitude The longitude of the search center.
     * @param range The range (in specified units) to search for listings.
     * @param groupCode The group code for the listings. This is typically used to filter listings that belong to a specific group or category.
     * @return A list of {@link Listing} objects that match the group code and are within the specified range of the location.
     */
    public List<Listing> searchListingsWithGroupCode(Double latitude, Double longitude, Double range, Integer groupCode) {
        // Call the repository method with group code and location criteria
        return listingRepository.findListingsWithGroupCode(latitude, longitude, range, groupCode);
    }


}
