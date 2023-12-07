package com.example.webservice.controller;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.Listing;
import com.example.webservice.service.ListingService;
import com.example.webservice.service.ClientService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.webservice.model.model.ListingRequestDTO;
import org.springframework.web.server.ResponseStatusException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling operations related to the {@link Listing} entity.
 */
@RestController
@CrossOrigin
@RequestMapping("/listing")
public class ListingController {

    @Autowired
    private ListingService listingService;
    @Autowired
    private ClientService clientService;

//    /**
//     * Retrieves a list of all listings.
//     *
//     * @return {@link ResponseEntity} containing a list of all listings.
//     * Response Codes:
//     * 200: Success
//     */
//    @GetMapping
//    public ResponseEntity<List<Listing>> getAllListings() {
//        return ResponseEntity.ok(listingService.getAllListings());
//    }

    /**
     * Retrieves a specific listing by its ID.
     *
     * @param id The ID of the desired listing.
     * @return {@link ResponseEntity} containing the specified listing or a not found status.
     * Response Codes:
     * 200: Success
     * 404: Invalid Token
     */
    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        return listingService.getListingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves listing created by client.
     *
     * @param clientID The ID of the client.
     * @param auth authentication.
     * Response Codes:
     * 200: Success
     * 404: Invalid Token
     */
    @GetMapping("/by_client/{clientID}")
    public ResponseEntity<List<Listing>> getListingByClient(@PathVariable Long clientID,
                                                      @RequestParam String auth) {

        try {
            Client client = clientService.getClientById(clientID);

            if (!auth.equals(client.getAuthentication())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "auth and id does not match");
            }
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    e.getMessage());
        }
        List<Listing> listings = listingService.getListingsByClientID(clientID);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    /**
     * Creates a new listing using provided details and authentication.
     *
     * @param clientID ID of the client creating the listing.
     * @param auth     Client's authentication string.
     * @param listing  {@link ListingRequestDTO} containing listing details.
     * @return {@link ResponseEntity} containing the created listing.
     * Response Codes:
     * 200: Success
     * 400: Invalid Input
     * 404: Invalid Client ID or authentication
     */
    @PostMapping("/create")
    @SuppressFBWarnings
    public ResponseEntity<?> createListing(@RequestParam Long clientID,
                                           @RequestParam String auth,
                                           @RequestParam Long facilityID,
                                           @RequestBody ListingRequestDTO listing) {
        // Validate the listing details
        if (!listing.getIsPublic() && listing.getGroupCode() == null) {
            return ResponseEntity.badRequest().body("Group code is required for private listings.");
        }

        // Assuming listingService.createListing returns a Listing object or null
        Listing createdListing = listingService.createListing(clientID, auth, facilityID, listing);
        if (createdListing != null) {
            return ResponseEntity.ok(createdListing);
        } else {
            // Handle the case where listing creation fails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating listing");
        }
    }

    /**
     * Updates an existing listing using provided details and authentication.
     * Update related facility is NOT allowed, must remove the listing first and re-list
     *
     * @param clientID       ID of the client updating the listing.
     * @param auth           Client's authentication string.
     * @param id             ID of the listing to be updated.
     * @param updatedListing {@link ListingRequestDTO} containing updated listing details.
     * @return {@link ResponseEntity} containing the updated listing or a not found status.
     * Response Codes:
     * 200: Success
     * 400: Invalid Input
     * 404: Invalid Client ID or authentication
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateListing(@PathVariable Long id,
                                           @RequestParam Long clientID,
                                           @RequestParam String auth,
                                           @RequestBody ListingRequestDTO updatedListing) {
        if (updatedListing.getIsPublic() == null) {
            return ResponseEntity.badRequest().body("invalid public status");
        }
        // Validate the listing details
        if (!updatedListing.getIsPublic() && updatedListing.getGroupCode() == null) {
            return ResponseEntity.badRequest().body("Group code is required for private listings.");
        }

        // Attempt to update the listing
        Optional<Listing> updated = listingService.updateListing(id, clientID, auth, updatedListing);

        // Check if the update operation was successful
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            // Handle the case where listing update fails
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Listing not found or update failed");
        }
    }


    /**
     * Deletes a specified listing using provided details and authentication.
     *
     * @param clientID ID of the client requesting delete.
     * @param auth     Client's authentication string.
     * @param id       ID of the listing to be deleted.
     * @return {@link ResponseEntity} with a no content or not found status.
     * Response Codes:
     * 204: Success
     * 401: Invalid Client ID or authentication
     * 404: Listing not found
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteListing(@RequestParam Long clientID,
                                              @RequestParam String auth,
                                              @PathVariable Long id) {
        if (listingService.deleteListing(clientID, auth, id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

//    Deprecated
//    /**
//     * Searches for listings based on a specified location and range.
//     *
//     * @param latitude  Latitude of the search center.
//     * @param longitude Longitude of the search center.
//     * @param range     Range (in specified units) to search for listings.
//     * @return {@link ResponseEntity} containing a list of listings within the specified range of the location.
//     * Response Codes:
//     * 200: Success
//     */
//    @GetMapping("/search")
//    public ResponseEntity<List<Listing>> searchListings(@RequestParam(required = false) Boolean isPublic,
//                                                        @RequestParam(required = false) Integer groupCode,
//                                                        @RequestParam(required = false) String itemList,
//                                                        @RequestParam(required = false) Integer ageRequirement,
//                                                        @RequestParam(required = false) Boolean veteranStatus,
//                                                        @RequestParam(required = false) String gender,
//                                                        @RequestParam Double latitude,
//                                                        @RequestParam Double longitude,
//                                                        @RequestParam Double range) {
//        List<Listing> listings = listingService.searchListings(isPublic, groupCode, itemList,
//                ageRequirement, veteranStatus, gender,
//                latitude, longitude, range);
//        return ResponseEntity.ok(listings);
//    }


    /**
     * Searches for listings based on various filters.
     *
     * @param latitude       Latitude of the search center.
     * @param longitude      Longitude of the search center.
     * @param range          Range (in specified units) to search for listings.
     * @param itemContained  Items contained in the listing (separated by '|').
     * @param age            Age requirement for the listing.
     * @param veteranStatus  Veteran status requirement.
     * @param gender         Gender requirement.
     * @return {@link ResponseEntity} containing a list of listings matching the filters.
     * Response Codes:
     * 200: Success
     */
    @GetMapping("/search_with_filter")
    public ResponseEntity<List<Listing>> searchListingsWithFilter(@RequestParam Double latitude,
                                                                  @RequestParam Double longitude,
                                                                  @RequestParam Double range,
                                                                  @RequestParam(required = false) String itemContained,
                                                                  @RequestParam(required = false) Integer age,
                                                                  @RequestParam(required = false) Boolean veteranStatus,
                                                                  @RequestParam(required = false) String gender) {
        List<Listing> listings = listingService.searchListingsWithFilter(latitude, longitude, range, itemContained, age, veteranStatus, gender);
        return ResponseEntity.ok(listings);
    }

    /**
     * Searches for listings based on a group code.
     *
     * @param latitude   Latitude of the search center.
     * @param longitude  Longitude of the search center.
     * @param range      Range (in specified units) to search for listings.
     * @param groupCode  Group code for the listings.
     * @return {@link ResponseEntity} containing a list of listings matching the group code.
     * Response Codes:
     * 200: Success
     */
    @GetMapping("/search_with_group_code")
    public ResponseEntity<List<Listing>> searchListingsWithGroupCode(@RequestParam Double latitude,
                                                                     @RequestParam Double longitude,
                                                                     @RequestParam Double range,
                                                                     @RequestParam Integer groupCode) {
        List<Listing> listings = listingService.searchListingsWithGroupCode(latitude, longitude, range, groupCode);
        return ResponseEntity.ok(listings);
    }
}
