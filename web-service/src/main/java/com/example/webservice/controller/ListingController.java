package com.example.webservice.controller;

import com.example.webservice.model.Listing;
import com.example.webservice.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;

    // GET request to retrieve all listings
    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        return new ResponseEntity<>(listingRepository.findAll(), HttpStatus.OK);
    }

    // GET request to retrieve a listing by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        return listingRepository.findById(id)
                .map(listing -> new ResponseEntity<>(listing, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST request to create a new listing
    @PostMapping
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
        Listing newListing = listingRepository.save(listing);
        return new ResponseEntity<>(newListing, HttpStatus.CREATED);
    }

    // PUT request to update a listing by its ID
    @PutMapping("/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long id, @RequestBody Listing updatedListing) {
        if (listingRepository.existsById(id)) {
            updatedListing.setListingID(id);
            Listing savedListing = listingRepository.save(updatedListing);
            return new ResponseEntity<>(savedListing, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE request to delete a listing by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        if (listingRepository.existsById(id)) {
            listingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // GET request to search listings based on latitude and longitude
    @GetMapping("/search")
    public ResponseEntity<List<Listing>> searchListingsByLocation(@RequestParam Double latitude, @RequestParam Double longitude) {
        // This method in the repository would still need to be defined.
        return new ResponseEntity<>(listingRepository.findListingsByLocation(latitude, longitude), HttpStatus.OK);
    }

    // Handle exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
