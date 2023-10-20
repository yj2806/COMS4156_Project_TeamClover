package com.example.webservice.model;

import jakarta.persistence.*;


@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listingID")
    private Integer listingID;

    @ManyToOne
    @JoinColumn(name = "associated_facilityID")
    private Facility associatedFacility;

    @Column(name = "public", nullable = false)
    private boolean isPublic;

    @Column(name = "group_code")
    private Integer groupCode;

    @Column(name = "item_list", nullable = false, columnDefinition = "TEXT")
    private String itemList;

    @Column(name = "age_requirement")
    private Integer ageRequirement;

    @Column(name = "veteran_status")
    private Boolean veteranStatus;

    @Column(name = "gender")
    private String gender;


    // Getter and Setter methods

    public Integer getListingID() {
        return listingID;
    }

    public void setListingID(Integer listingID) {
        this.listingID = listingID;
    }


    public Facility getAssociatedFacility() {
        return associatedFacility;
    }

    public void setAssociatedFacility(Facility associatedFacility) {
        this.associatedFacility = associatedFacility;
    }

}