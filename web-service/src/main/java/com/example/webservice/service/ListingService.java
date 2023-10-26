package com.example.webservice.service;

import com.example.webservice.model.Listing;
import com.example.webservice.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Optional<Listing> getListingById(Long id) {
        return listingRepository.findById(id);
    }

    public Listing createListing(Listing listing) {
        // We can add constraint validation here if necessary
        return listingRepository.save(listing);
    }

    public Optional<Listing> updateListing(Long id, Listing updatedListing) {
        if (listingRepository.existsById(id)) {
            updatedListing.setListingID(id); // Assuming this method exists
            return Optional.of(listingRepository.save(updatedListing));
        }
        return Optional.empty();
    }

    public boolean deleteListing(Long id) {
        if (listingRepository.existsById(id)) {
            listingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Listing> searchListingsByLocation(Double latitude, Double longitude) {
        double radius = 10.0;  // radius in kilometers
        return listingRepository.findListingsByLocation(latitude, longitude, radius);
    }

}
