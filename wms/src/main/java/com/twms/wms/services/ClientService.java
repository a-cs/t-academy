package com.twms.wms.services;

import com.twms.wms.entities.Client;
import com.twms.wms.entities.SKU;
import com.twms.wms.repositories.ClientRepository;
import io.swagger.v3.core.util.Json;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;


    @Transactional
    public Client saveClient(Client client){

        return clientRepository.save(client);
    }
    @SneakyThrows
    public Client createClient(Client client){

        if(clientRepository.findByCNPJ(client.getCNPJ()).size()>0) throw new SQLIntegrityConstraintViolationException("CNPJ is a unique field!!");
        if(clientRepository.findByName(client.getName()).size()>0) throw new SQLIntegrityConstraintViolationException("Name should be unique!!");
        client.setUser(userService.createClientUser(client.getName(), client.getEmail()));
        if(client.getAddress().getId()==null) client.setAddress(addressService.post(client.getAddress()));
        else if(addressService.getById(client.getAddress().getId())==null) throw new SQLIntegrityConstraintViolationException("Address not Found!!");
        Client clientToReturn = this.saveClient(client);
        return clientToReturn;

    }

    public List<Client> readAllClients(){return clientRepository.findAll();}

    public Client readClientById(Long clientId){
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        return optionalClient.orElseThrow(()->new EntityNotFoundException("Client Not Created or Removed!!"));
    }

    public Client updateClient(Long clientId, Client client){
        Client oldClient = this.readClientById(clientId);

        long idAdress = oldClient.getAddress().getId();
        oldClient.setAddress(client.getAddress());
        oldClient.getAddress().setId(idAdress);
        oldClient.setName(client.getName());
        oldClient.setCNPJ(client.getCNPJ());
        //oldClient.setEmail(client.getEmail());
        //oldClient.setUser(client.getUser());

        addressService.post(oldClient.getAddress());

        return this.saveClient(oldClient);
    }

    @Transactional
    public void deleteClient(Long clientId){
        Client toDelete = this.readClientById(clientId);
        clientRepository.delete(toDelete);
    }

    public Page<Client> searchTerm(String searchTerm, Pageable pageable){
        String terms = searchTerm.replace("-", " ");
        return clientRepository.findByNameContainingIgnoreCaseOrCNPJContainingIgnoreCase(terms, terms, pageable);
    }

    public Page<Client> readAllClientsPaginated(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
}
