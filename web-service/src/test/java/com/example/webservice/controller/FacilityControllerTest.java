package com.example.webservice.controller;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.model.FacilityRequestDTO;
import com.example.webservice.service.ClientService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacilityController.class)
public class FacilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private FacilityService facilityService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllFacilitiesSuccess() throws Exception {
        List<Facility> facilities = Arrays.asList(new Facility(), new Facility());
        when(facilityService.getPublicFacilities()).thenReturn(facilities);

        mockMvc.perform(get("/facility"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getFacilityByClientSuccess() throws Exception {
        Long clientID = 1L;
        String auth = "validAuth";
        List<Facility> facilities = Arrays.asList(new Facility());

        Client testClient = new Client();
        testClient.setAuthentication(auth); // Set the authentication field to match the auth parameter

        when(clientService.getClientById(clientID)).thenReturn(testClient);
        when(facilityService.getFacilitiesByClientID(clientID)).thenReturn(facilities);

        mockMvc.perform(get("/facility/by_client/{clientID}", clientID)
                        .param("auth", auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }


    @Test
    public void getFacilityByClientUnauthorized() throws Exception {
        Long clientID = 1L;
        String auth = "invalidAuth";
        when(clientService.getClientById(clientID)).thenReturn(new Client());

        mockMvc.perform(get("/facility/by_client/{clientID}", clientID)
                        .param("auth", auth))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getFacilityByIdSuccess() throws Exception {
        Long id = 1L;
        when(facilityService.getFacilityById(id)).thenReturn(new Facility());

        mockMvc.perform(get("/facility/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void getFacilityByIdNotFound() throws Exception {
        Long id = 1L;
        when(facilityService.getFacilityById(id)).thenThrow(new ResourceNotFoundException("unknown facility"));

        mockMvc.perform(get("/facility/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createFacilitySuccess() throws Exception {
        Long clientID = 1L;
        String auth = "validAuth";
        FacilityRequestDTO facilityDTO = new FacilityRequestDTO(); // Initialize with test data
        Facility facility = new Facility(); // Initialize with test data

        Client testClient = new Client();
        testClient.setAuthentication(auth); // Set the authentication field to match the auth parameter

        when(clientService.getClientById(clientID)).thenReturn(testClient);
        when(facilityService.createFacility(clientID, facilityDTO)).thenReturn(facility);

        mockMvc.perform(post("/facility/create")
                        .param("clientID", clientID.toString())
                        .param("auth", auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facilityDTO)))
                .andExpect(status().isOk());
    }


    @Test
    public void createFacilityUnauthorized() throws Exception {
        Long clientID = 1L;
        String auth = "invalidAuth";
        FacilityRequestDTO facilityDTO = new FacilityRequestDTO(); // Set properties as needed
        when(clientService.getClientById(clientID)).thenReturn(new Client());

        mockMvc.perform(post("/facility/create")
                        .param("clientID", clientID.toString())
                        .param("auth", auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facilityDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateFacilitySuccess() throws Exception {
        Long clientID = 1L;
        Long facilityID = 1L;
        String auth = "validAuth";
        FacilityRequestDTO facilityDTO = new FacilityRequestDTO(); // Initialize with test data
        Facility updatedFacility = new Facility(); // Initialize with test data

        Client client = new Client();
        client.setAuthentication(auth);
        when(clientService.getClientById(clientID)).thenReturn(client);
        when(facilityService.updateFacility(clientID, auth, facilityID, facilityDTO)).thenReturn(updatedFacility);

        mockMvc.perform(put("/facility/update/{facilityID}", facilityID)
                        .param("clientID", clientID.toString())
                        .param("auth", auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facilityDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateFacilityUnauthorized() throws Exception {
        Long clientID = 1L;
        Long facilityID = 1L;
        String auth = "invalidAuth";
        FacilityRequestDTO facilityDTO = new FacilityRequestDTO(); // Initialize with test data

        Client client = new Client();
        client.setAuthentication("validAuth");
        when(clientService.getClientById(clientID)).thenReturn(client);

        mockMvc.perform(put("/facility/update/{facilityID}", facilityID)
                        .param("clientID", clientID.toString())
                        .param("auth", auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facilityDTO)))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void deleteFacilitySuccess() throws Exception {
        Long clientID = 1L;
        Long id = 1L;
        String auth = "validAuth";

        Client testClient = new Client();
        testClient.setAuthentication(auth); // Set the authentication field to match the auth parameter

        when(clientService.getClientById(clientID)).thenReturn(testClient);

        mockMvc.perform(delete("/facility/delete/{id}", id)
                        .param("clientID", clientID.toString())
                        .param("auth", auth))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteFacilityUnauthorized() throws Exception {
        Long clientID = 1L;
        Long id = 1L;
        String auth = "invalidAuth";
        when(clientService.getClientById(clientID)).thenReturn(new Client());

        mockMvc.perform(delete("/facility/delete/{id}", id)
                        .param("clientID", clientID.toString())
                        .param("auth", auth))
                .andExpect(status().isUnauthorized());
    }



}
