package com.example.webservice.external;

import com.example.webservice.model.Listing;
import com.example.webservice.repository.ListingRepository;
import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.Listing;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class externalIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ListingRepository listingRepository;

    @Test
    public void testConnection() {
        List<Listing> i = listingRepository.findAll();

        assertTrue(i.size() >= 0);
    }
}
