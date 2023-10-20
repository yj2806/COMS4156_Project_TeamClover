package com.example.webservice.controller;

import com.example.webservice.model.Client;
import com.example.webservice.model.model.ClientRequestDTO;
import com.example.webservice.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void testGetAllClients() throws Exception {
        List<Client> clients = new ArrayList<>();
        // TODO:Add test clients to the list

        Mockito.when(clientService.getAllClients()).thenReturn(clients);

        mockMvc.perform(MockMvcRequestBuilders.get("/client"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // TODO: Add assertions for the response content
    }

    @Test
    public void testGetClientById() throws Exception {
        Long clientId = 1L;
        Client client = new Client();
        // TODO:Set properties of the test client

        Mockito.when(clientService.getClientById(clientId)).thenReturn(client);

        mockMvc.perform(MockMvcRequestBuilders.get("/client/" + clientId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // TODO:Add assertions for the response content
    }

    @Test
    public void testCreateClient() throws Exception {
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
        // TODO: Set properties of the test client request DTO

        Client createdClient = new Client();
        // TODO:Set properties of the created client

        Mockito.when(clientService.createClient(clientRequestDTO)).thenReturn(createdClient);

        mockMvc.perform(MockMvcRequestBuilders.post("/client/create")
                        .contentType("application/json")
                        .content("{\"property1\": \"value1\", \"property2\": \"value2\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //TODO: Add assertions
    }

    @Test
    public void testUpdateClient() throws Exception {
        Long clientId = 1L;
        ClientRequestDTO updatedClientDTO = new ClientRequestDTO();
        // TODO: Set properties of the updated client request DTO

        Client updatedClient = new Client();
        // TODO: Set properties of the updated client

        Mockito.when(clientService.updateClient(clientId, updatedClientDTO)).thenReturn(updatedClient);

        mockMvc.perform(MockMvcRequestBuilders.put("/client/update/" + clientId)
                        .contentType("application/json")
                        .content("{\"property1\": \"updatedValue1\", \"property2\": \"updatedValue2\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // TODO: Add assertions for the response content
    }

    @Test
    public void testDeleteClient() throws Exception {
        Long clientId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/client/delete/" + clientId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // TODO:Add assertions
    }
}
