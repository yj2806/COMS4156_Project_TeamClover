package com.example.webservice.repository;

import com.example.webservice.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    // Custom query to find listings based on latitude and longitude of associated facility
    @Query("SELECT l FROM Listing l JOIN l.associatedFacility f WHERE f.latitude = :latitude AND f.longitude = :longitude")
    List<Listing> findListingsByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
}