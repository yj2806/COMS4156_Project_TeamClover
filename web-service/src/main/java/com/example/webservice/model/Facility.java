package com.example.webservice.model;

import javax.persistence.*;

@Entity
@Table(name = "facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int facilityID;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "public")
    private boolean isPublic;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "hours")
    private String hours;

    public Facility() {
    }

}
