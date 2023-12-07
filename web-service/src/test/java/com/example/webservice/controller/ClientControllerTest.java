package com.example.webservice.controller;

import com.example.webservice.model.Client;
import com.example.webservice.model.model.ClientRequestDTO;
import com.example.webservice.model.type.ClientType;
import com.example.webservice.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setup() {
//        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    static private  ClientRequestDTO clientRequst = new ClientRequestDTO("authentication", "DISTRIBUTOR");
//    @Test
//    public void testGetAllClients() throws Exception {
//        List<Client> clients = new ArrayList<>();
//        for(int i = 0; i < 5; i ++){
//            clients.add(new Client());
//        }
//
//        Mockito.when(clientService.getAllClients()).thenReturn(clients);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/client"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // TODO: Add assertions for the response content
//    }

    @Test
    public void testCreateClient200() throws Exception {

        Client createdClient = new Client();
        createdClient.setAuthentication(clientRequst.getAuthentication());
        createdClient.setType(ClientType.fromString(clientRequst.getType()));

        Mockito.when(clientService.createClient(clientRequst)).thenReturn(createdClient);

        ObjectMapper mapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/client/create")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(clientRequst)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals(content, mapper.writeValueAsString(createdClient));

    }

    @Test
    public void testCreateClient400() throws Exception {

        ClientRequestDTO request = new ClientRequestDTO();
        request.setType("bad");
        request.setAuthentication("authentication");

        Mockito.when(clientService.createClient(request)).thenThrow(new IllegalArgumentException());

        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writeValueAsString(request));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/client/create")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

    }

    @Test
    public void testDeleteClient401() throws Exception {
        Long clientId = 1L;

//        Client client = clientService.createClient(new ClientRequestDTO("authentication","DISTRIBUTION"));
        Client client = new Client();
        doNothing().when(clientService).deleteClient(clientId);

        Mockito.when(clientService.getClientById(clientId)).thenReturn(client);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/client/delete/" + clientId).param("auth","auth"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn();

    }

    @Test
    public void testDeleteClient200() throws Exception {
        Long clientId = 1L;

//        Client client = clientService.createClient(new ClientRequestDTO("authentication","DISTRIBUTION"));
        Client client = new Client();
        client.setAuthentication("auth");
        doNothing().when(clientService).deleteClient(clientId);

        Mockito.when(clientService.getClientById(clientId)).thenReturn(client);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/client/delete/" + clientId).param("auth","auth"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // TODO:Add assertions
    }

    @Test
    public void testDeleteClient404() throws Exception {
        Long clientId = 1L;

//        Client client = clientService.createClient(new ClientRequestDTO("authentication","DISTRIBUTION"));
        Client client = new Client();
        client.setAuthentication("auth");
        doNothing().when(clientService).deleteClient(clientId);

        Mockito.when(clientService.getClientById(clientId)).thenThrow(new ResourceNotFoundException(""));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/client/delete/" + clientId).param("auth","auth"))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

    }
}