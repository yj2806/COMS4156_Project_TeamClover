package com.example.webservice.model.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FacilityRequestDTO {
    private float latitude;
    private float longitude;

    @NotNull(message = "Public status is required.")
    private Boolean isPublic;

    private String email;
    private String phone;
    private String hours;

//    public boolean getPublic() {
//        return isPublic;
//    }
}