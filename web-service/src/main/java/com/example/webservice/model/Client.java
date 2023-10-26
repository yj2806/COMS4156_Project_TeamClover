package com.example.webservice.model;

import com.example.webservice.model.type.ClientType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import lombok.*;


@SuppressWarnings("CheckStyle")
@Entity
@Builder(access = AccessLevel.PACKAGE, toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
@Table(name = "client")
//Entity class for Client
public class Client implements Serializable {
    @SuppressWarnings("CheckStyle")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientID")
    private Long clientID;

    @SuppressWarnings("CheckStyle")
    @Column(name = "authentication", nullable = false)
    private String authentication;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "associated_facilityID")
    private Facility associatedFacility;

}
