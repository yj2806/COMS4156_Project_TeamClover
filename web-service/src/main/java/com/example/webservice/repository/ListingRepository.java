package com.example.webservice.repository;

import com.example.webservice.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Query(value = "SELECT l.* FROM listings l JOIN facility f ON l.associated_facilityID = f.facilityID WHERE ABS(f.latitude - :latitude) < :range AND ABS(f.longitude - :longitude) < :range", nativeQuery = true)
    List<Listing> findListingsByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("range") Double range);

    @Query(value = "SELECT l.* FROM listings l WHERE l.associated_facilityID = :facilityID", nativeQuery = true)
    List<Listing> findListingsByFacilityID(@Param("facilityID") Long facilityID);

}
