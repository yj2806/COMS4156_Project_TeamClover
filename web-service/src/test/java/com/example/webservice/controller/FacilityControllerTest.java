package com.example.webservice.controller;

import com.example.webservice.exception.InvalidClientIDOrAuthException;
import com.example.webservice.exception.InvalidTokenException;
import com.example.webservice.model.Facility;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.service.FacilityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacilityController.class)
public class FacilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacilityService facilityService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Test case for retrieving all facilities.
     */
    @Test
    public void testGetAllFacilities() throws Exception {
        Facility facility = new Facility();
        // Set properties if necessary
        when(facilityService.getPublicFacilities()).thenReturn(List.of(facility));

        mockMvc.perform(get("/facility"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(facility))));
    }

    @Test
    public void testGetFacilityById() throws Exception {
        Long id = 1L;
        Facility facility = new Facility();
        // Set properties if necessary
        when(facilityService.getFacilityById(id)).thenReturn(facility);

        mockMvc.perform(get("/facility/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(facility)));
    }

    @Test
    public void testGetFacilityById_NotFound() throws Exception {
        Long id = 1L;
        Facility facility = new Facility();

        when(facilityService.getFacilityById(id)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/facility/{id}", id))
                .andExpect(status().isUnauthorized());
    }

//    @Test
//    public void testCreateFacility() throws Exception {
//        FacilityRequestDTO requestDTO = new FacilityRequestDTO();
//        // Set properties if necessary
//        Facility facility = new Facility();
//        // Set properties if necessary
//        when(facilityService.createFacility(requestDTO)).thenReturn(facility);
//
//        mockMvc.perform(post("/facility/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(facility)));
//    }
//

    @Test
    public void testUpdateFacility() throws Exception {
        Long id = 1L;
        String auth = "";
        Long facilityID = 1L;
        FacilityRequestDTO requestDTO = new FacilityRequestDTO();
        // Set properties if necessary
        Facility updatedFacility = new Facility();
        // Set properties if necessary
        when(facilityService.updateFacility(id, auth, facilityID, requestDTO)).thenReturn(updatedFacility);

        mockMvc.perform(put("/facility/update/{facilityID}", facilityID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .param("clientID",  String.valueOf(id))
                        .param("auth", auth)
                        .param("facilityID", String.valueOf(facilityID)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedFacility)));
    }

    @Test
    public void testUpdateFacility_InvalidToken() throws Exception {
        Long id = 1L;
        String auth = "";
        Long facilityID = 1L;
        FacilityRequestDTO requestDTO = new FacilityRequestDTO();
        when(facilityService.updateFacility(id, auth, facilityID, requestDTO)).thenThrow(InvalidTokenException.class);

        mockMvc.perform(put("/facility/update/{facilityID}", facilityID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .param("clientID",  String.valueOf(id))
                        .param("auth", auth)
                        .param("facilityID", String.valueOf(facilityID)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdateFacility_InvalidClientIDOrAuth() throws Exception {
        Long id = 1L;
        String auth = "";
        Long facilityID = 1L;
        FacilityRequestDTO requestDTO = new FacilityRequestDTO();
        when(facilityService.updateFacility(id, auth, facilityID, requestDTO)).thenThrow(InvalidClientIDOrAuthException.class);

        mockMvc.perform(put("/facility/update/{facilityID}", facilityID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .param("clientID",  String.valueOf(id))
                        .param("auth", auth)
                        .param("facilityID", String.valueOf(facilityID)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFacility() throws Exception {
        Long id = 1L;
        when(facilityService.deleteFacility(id)).thenReturn(true);

        mockMvc.perform(delete("/facility/delete/{id}", id))
                        .andExpect(status().isNoContent());
    }
}
