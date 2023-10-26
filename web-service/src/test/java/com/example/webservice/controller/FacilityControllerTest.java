package com.example.webservice.controller;

import com.example.webservice.model.Facility;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.service.FacilityService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Test
    public void testGetAllFacilities() throws Exception {
        Facility facility = new Facility();
        // Set properties if necessary
        when(facilityService.getAllFacilities()).thenReturn(List.of(facility));

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
//    @Test
//    public void testUpdateFacility() throws Exception {
//        Long id = 1L;
//        FacilityRequestDTO requestDTO = new FacilityRequestDTO();
//        // Set properties if necessary
//        Facility updatedFacility = new Facility();
//        // Set properties if necessary
//        when(facilityService.updateFacility(id, requestDTO)).thenReturn(updatedFacility);
//
//        mockMvc.perform(put("/facility/update/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(updatedFacility)));
//    }

    @Test
    public void testDeleteFacility() throws Exception {
        Long id = 1L;
        doNothing().when(facilityService).deleteFacility(id);

        mockMvc.perform(delete("/facility/delete/{id}", id))
                .andExpect(status().isOk());

        verify(facilityService, times(1)).deleteFacility(id);
    }
}
