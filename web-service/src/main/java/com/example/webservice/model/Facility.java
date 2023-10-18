package com.example.webservice.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
@Table(name = "facility")
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int facilityID;

    @Column(name = "latitude")
    public float latitude;

    @Column(name = "longitude")
    public float longitude;

    @Column(name = "public")
    public boolean isPublic;

    @Column(name = "email")
    public String email;

    @Column(name = "phone")
    public String phone;

    @Column(name = "hours")
    public String hours;

    public Facility() {
    }

}
