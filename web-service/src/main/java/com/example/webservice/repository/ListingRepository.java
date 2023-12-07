package com.example.webservice.repository;

import com.example.webservice.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

//    @Query(value = "SELECT l.* FROM listings l JOIN facility f ON l.associated_facilityID = f.facilityID WHERE ABS(f.latitude - :latitude) < :range AND ABS(f.longitude - :longitude) < :range", nativeQuery = true)
//    List<Listing> findListingsByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("range") Double range);

    @Query(value = "SELECT l.* FROM listings l WHERE l.associated_facilityID = :facilityID", nativeQuery = true)
    List<Listing> findListingsByFacilityID(@Param("facilityID") Long facilityID);

    @Query(value = "SELECT l.* FROM listings l JOIN facility f ON l.associated_facilityID = f.facilityID " +
            "WHERE (:isPublic IS NULL OR l.is_public = :isPublic) " +
            "AND (:groupCode IS NULL OR l.group_code = :groupCode) " +
            "AND (:itemList IS NULL OR l.item_list = :itemList) " +
            "AND (:ageRequirement IS NULL OR l.age_requirement = :ageRequirement) " +
            "AND (:veteranStatus IS NULL OR l.veteran_status = :veteranStatus) " +
            "AND (:gender IS NULL OR l.gender = :gender) " +
            "AND ABS(f.latitude - :latitude) < :range AND ABS(f.longitude - :longitude) < :range",
            nativeQuery = true)
    List<Listing> findListingsByCriteria(@Param("isPublic") Boolean isPublic,
                                         @Param("groupCode") Integer groupCode,
                                         @Param("itemList") String itemList,
                                         @Param("ageRequirement") Integer ageRequirement,
                                         @Param("veteranStatus") Boolean veteranStatus,
                                         @Param("gender") String gender,
                                         @Param("latitude") Double latitude,
                                         @Param("longitude") Double longitude,
                                         @Param("range") Double range);

    @Query(value = "SELECT l.* FROM listings l JOIN facility f ON l.associated_facilityID = f.facilityID " +
            "WHERE (:itemList IS NULL OR l.item_list LIKE :itemList) " +
            "AND (l.public = True) " +
            "AND (:ageRequirement IS NULL OR l.age_requirement >= :ageRequirement) " +
            "AND (:veteranStatus IS NULL OR l.veteran_status = :veteranStatus) " +
            "AND (:gender IS NULL OR l.gender = :gender) " +
            "AND ABS(f.latitude - :latitude) < :range AND ABS(f.longitude - :longitude) < :range",
            nativeQuery = true)
    List<Listing> findListingsWithFilter(@Param("latitude") Double latitude,
                                         @Param("longitude") Double longitude,
                                         @Param("range") Double range,
                                         @Param("itemList") String itemList,
                                         @Param("ageRequirement") Integer ageRequirement,
                                         @Param("veteranStatus") Boolean veteranStatus,
                                         @Param("gender") String gender);



    @Query(value = "SELECT l.* FROM listings l JOIN facility f ON l.associated_facilityID = f.facilityID " +
            "WHERE l.group_code = :groupCode " +
            "AND ABS(f.latitude - :latitude) < :range AND ABS(f.longitude - :longitude) < :range",
            nativeQuery = true)
    List<Listing> findListingsWithGroupCode(@Param("latitude") Double latitude,
                                            @Param("longitude") Double longitude,
                                            @Param("range") Double range,
                                            @Param("groupCode") Integer groupCode);



}
