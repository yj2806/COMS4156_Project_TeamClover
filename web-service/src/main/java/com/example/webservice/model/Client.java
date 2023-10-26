package com.example.webservice.model;

import com.example.webservice.model.type.ClientType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder(access=AccessLevel.PACKAGE,toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
@Table(name = "client")
//Entity class for Client
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientID")
    private Long clientID;

    @Column(name = "authentication", nullable = false)
    private String authentication;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "associated_facilityID")
    private Facility associatedFacility;

}
