package com.example.webservice.model;

import javax.persistence.*;

@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listingID")
    private Long listingID;

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
}
