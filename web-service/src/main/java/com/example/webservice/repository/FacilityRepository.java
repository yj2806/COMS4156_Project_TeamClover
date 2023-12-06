package com.example.webservice.repository;

import com.example.webservice.model.Facility;
import com.example.webservice.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import javax.persistence.*;
import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    @Query(value = "SELECT f.* FROM facility f WHERE f.associated_distributorID = :clientID", nativeQuery = true)
    List<Facility> findFacilitiesByClientID(@Param("clientID") Long clientID);

    @Query(value = "SELECT f.* FROM facility f WHERE f.public = True", nativeQuery = true)
    List<Facility> findPublicFacilities();

}
