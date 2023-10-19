package com.example.webservice.model.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientRequestDTO {
    private String authentication;
    private String type;
    private Long associatedFacilityId;
}