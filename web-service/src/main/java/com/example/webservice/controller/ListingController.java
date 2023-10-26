package com.example.webservice.controller;

import com.example.webservice.model.Listing;
import com.example.webservice.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.webservice.model.model.ListingRequestDTO;

import java.util.List;

/**
 * Controller for handling operations related to the {@link Listing} entity.
 */
@RestController
@RequestMapping("/listing")
public class ListingController {

    @Autowired
    private ListingService listingService;

    /**
     * Retrieves a list of all listings.
     *
     * @return {@link ResponseEntity} containing a list of all listings.
     * Response Codes:
     * 200: Success
     */
    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        return ResponseEntity.ok(listingService.getAllListings());
    }

    /**
     * Retrieves a specific listing by its ID.
     *
     * @param id The ID of the desired listing.
     * @return {@link ResponseEntity} containing the specified listing or a not found status.
     * Response Codes:
     * 200: Success
     * 401: Invalid Token
     */
    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        return listingService.getListingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
    public ResponseEntity<Listing> createListing(@RequestParam Long clientID,
                                                 @RequestParam String auth,
                                                 @RequestBody ListingRequestDTO listing) {
        // We can include validations here for constraints if needed
        return ResponseEntity.ok(listingService.createListing(clientID, auth, listing));
    }

    /**
     * Updates an existing listing using provided details and authentication.
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
    public ResponseEntity<Listing> updateListing(@RequestParam Long clientID,
                                                 @RequestParam String auth,
                                                 @PathVariable Long id, @RequestBody ListingRequestDTO updatedListing) {
        return listingService.updateListing(clientID, auth, id, updatedListing)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a specified listing using provided details and authentication.
     *
     * @param clientID ID of the client requesting the delete.
     * @param auth     Client's authentication string.
     * @param id       ID of the listing to be deleted.
     * @return {@link ResponseEntity} with a no content or not found status.
     * Response Codes:
     * 200: Success
     * 401: Invalid Client ID or authentication
     * 500: Internal Server Error
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

    /**
     * Searches for listings based on a specified location and range.
     *
     * @param latitude  Latitude of the search center.
     * @param longitude Longitude of the search center.
     * @param range     Range (in specified units) to search for listings.
     * @return {@link ResponseEntity} containing a list of listings within the specified range of the location.
     * Response Codes:
     * 200: Success
     */
    @GetMapping("/search")
    public ResponseEntity<List<Listing>> searchListingsByLocation(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam Double range) {
        return ResponseEntity.ok(listingService.searchListingsByLocation(latitude, longitude, range));
    }
}
