package com.example.webservice.model.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;


@Data
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin
public class ListingRequestDTO {

    @NotNull(message = "Public status is required.")
    private Boolean isPublic;

//    public boolean isPublic() {
//        return isPublic;
//    }

    private Integer groupCode;

    @NotNull(message = "Item list is required.")
    @Size(min = 1, message = "Item list must not be empty.")
    private String itemList;

    private Integer ageRequirement;

    private Boolean veteranStatus;

    @Size(max = 255, message = "Gender description too long.")
    private String gender;
}
