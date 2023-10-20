package com.example.webservice.model.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FacilityRequestDTO {
    private float latitude;
    private float longitude;
    private boolean isPublic;
    private String email;
    private String phone;
    private String hours;

    public boolean getPublic(){
        return isPublic;
    }
}