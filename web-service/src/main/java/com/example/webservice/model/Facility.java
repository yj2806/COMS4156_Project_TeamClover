package com.example.webservice.model;

import javax.persistence.*;

@Entity
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int facilityID;
    private float latitude;
    private float longitude;
    private boolean isPublic;
    private String email;
    private String phone;
    private String hours;

}
