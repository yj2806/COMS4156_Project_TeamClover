package com.example.webservice.controller;

import com.example.webservice.model.Listing;
import com.example.webservice.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listing")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        return ResponseEntity.ok(listingService.getAllListings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        return listingService.getListingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
        // We can include validations here for constraints if needed
        return ResponseEntity.ok(listingService.createListing(listing));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable Long id, @RequestBody Listing updatedListing) {
        return listingService.updateListing(id, updatedListing)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        if (listingService.deleteListing(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // If the location-based search gets implemented
    @GetMapping("/search")
    public ResponseEntity<List<Listing>> searchListingsByLocation(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam Double range) {
        return ResponseEntity.ok(listingService.searchListingsByLocation(latitude, longitude, range));
    }
}
