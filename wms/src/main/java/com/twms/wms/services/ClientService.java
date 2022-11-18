package com.twms.wms.services;

import com.twms.wms.entities.Client;
import com.twms.wms.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    UserService userService;

    @Transactional
    public Client saveClient(Client client){
        return clientRepository.save(client);
    }
    public Client createClient(Client client){

        client.setUser(userService.createClientUser(client.getCNPJ()));

        return this.saveClient(client);

    }

    public List<Client> readAllClients(){return clientRepository.findAll();}

    public Client readClientById(Long clientId){
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        return optionalClient.orElseThrow(()->new EntityNotFoundException("Client Not Created or Removed!!"));
    }

    public Client updateClient(Long clientId, Client client){
        Client oldClient = this.readClientById(clientId);

        oldClient.setAddress(client.getAddress());
        oldClient.setName(client.getName());
        oldClient.setCNPJ(client.getCNPJ());
        oldClient.setUser(client.getUser());

        return this.saveClient(oldClient);
    }

    @Transactional
    public void deleteClient(Long clientId){
        Client toDelete = this.readClientById(clientId);
        clientRepository.delete(toDelete);
    }

}
