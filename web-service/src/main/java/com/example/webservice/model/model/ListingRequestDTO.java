package com.example.webservice.model.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingRequestDTO {

    @NotNull(message = "Public status is required.")
    private Boolean isPublic;

    private Integer groupCode;

    @NotNull(message = "Item list is required.")
    @Size(min = 1, message = "Item list must not be empty.")
    private String itemList;

    private Integer ageRequirement;

    private Boolean veteranStatus;

    @Size(max = 255, message = "Gender description too long.")
    private String gender;
}
