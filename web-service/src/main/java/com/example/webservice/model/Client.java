package com.example.webservice.model;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientID")
    private Long clientID;

    @Column(name = "authentication", nullable = false)
    private String authentication;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "associated_facilityID")
    private Facility associatedFacility;

    // Getter and Setter methods
}
