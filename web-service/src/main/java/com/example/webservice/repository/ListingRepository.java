package com.example.webservice.repository;

import com.example.webservice.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Query(value = "SELECT l FROM Listing l WHERE acos(sin(:latitude) * sin(l.latitude) + cos(:latitude) * cos(l.latitude) * cos(l.longitude - :longitude)) * 6371 < :radius", nativeQuery = true)
    List<Listing> findListingsByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("radius") Double radius);
}
