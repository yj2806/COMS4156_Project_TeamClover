package com.example.webservice.service;

import com.example.webservice.model.Client;
import com.example.webservice.model.Facility;
import com.example.webservice.model.Listing;
import com.example.webservice.model.model.ClientRequestDTO;
import com.example.webservice.model.type.ClientType;
import com.example.webservice.repository.ClientRepository;
import com.example.webservice.repository.FacilityRepository;
import com.example.webservice.repository.ListingRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
public class ClientService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final FacilityRepository facilityRepository;
    private final ListingRepository listingRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository,
                         FacilityRepository facilityRepository,
                         ListingRepository listingRepository) {
        this.clientRepository = clientRepository;
        this.facilityRepository = facilityRepository;
        this.listingRepository = listingRepository;
    }

//    /**
//     * !!deprecated!!
//     *
//     * Retrieves a list of all clients.
//     *
//     * @return the list of all clients
//     */
//    @Transactional
//    public List<Client> getAllClients() {
//        return clientRepository.findAll();
//    }

    /**
     * Retrieves a client based on the given ID.
     *
     * @param id the ID of the client to retrieve
     * @return the client with the given ID
     * @throws ResourceNotFoundException if no client is found with the given ID
     */
    @Transactional
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    /**
     * Creates a new client using the given ClientRequestDTO.
     *
     * @param client the client data transfer object containing client details
     * @return the newly created client
     */
    @Transactional
    public Client createClient(ClientRequestDTO client) {
        boolean validType = ClientType.isValid(client.getType());
        if (!validType) {
            throw new IllegalArgumentException("Valid type are DISTRIBUTOR and NON_DISTRIBUTOR");
        }

        Client newClient = new Client();
//        Create facility and listing later
//        newClient.setAssociatedFacility(createFacility());
        newClient.setType(ClientType.fromString(client.getType()));
        newClient.setAuthentication(client.getAuthentication());
        return clientRepository.save(newClient);
    }

//    // Update an existing client based on ID and new client info
//    @Transactional
//    public Client updateClient(Long id, ClientRequestDTO updatedClient) {
//        return clientRepository.findById(id)
//                .map(client -> {
//                    client.setAssociatedFacility(getFacility(updatedClient.getAssociatedFacilityId()));
//                    client.setClientID(id);
//                    client.setType(ClientType.fromString(updatedClient.getType()));
//                    return clientRepository.save(client);
//                })
//                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
//    }

    /**
     * Deletes a client with the given ID.
     * This also deletes associated listings and facility.
     *
     * @param id the ID of the client to delete
     */
    @Transactional
    public void deleteClient(Long id) {
//        Client client = getClientById(id);

        List<Facility> facilityIDs = facilityRepository.findFacilitiesByClientID(id);

        for (Facility f : facilityIDs) {
            Long facilityID = f.getFacilityID();
            List<Listing> listingID = listingRepository.findListingsByFacilityID(facilityID);
            for (Listing l : listingID) {
                listingRepository.deleteById(l.getListingID());
            }
            facilityRepository.deleteById(facilityID);
        }
        clientRepository.deleteById(id);
    }

    /**
     * Helper method to retrieve a facility based on its ID.
     *
     * @param id the ID of the facility to retrieve
     * @return the facility with the given ID
     * @throws ResourceNotFoundException if no facility is found with the given ID
     */
    private Facility getFacility(Long id) {
        Optional<Facility> f = facilityRepository.findById(id);
        if (f.isPresent()) {
            return f.get();
        }
        throw new ResourceNotFoundException("Facility not found with id: " + id);
    }

    private Facility createFacility() {
        Facility newFacility = new Facility();
        return facilityRepository.save(newFacility);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findById(Long.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Assign a generic role or no authorities
        return new org.springframework.security.core.userdetails.User(
                String.valueOf(client.getClientID()), // Assuming this is the username
                client.getAuthentication(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))); // Generic role
    }

}
