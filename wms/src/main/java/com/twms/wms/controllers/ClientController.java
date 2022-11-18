package com.twms.wms.controllers;

import com.twms.wms.entities.Client;
import com.twms.wms.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(){return ResponseEntity.status(HttpStatus.OK).body(clientService.readAllClients());}

    @GetMapping("/{idClient}")
    public ResponseEntity<Client> getClientById(@PathVariable("idClient") Long idClient){return ResponseEntity.status(HttpStatus.OK).body(clientService.readClientById(idClient));}

    @PostMapping
    public ResponseEntity<Client> postClient(@RequestBody Client client){
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(client));
    }

    @PutMapping("/{idClient}")
    public ResponseEntity<Client> putClient(@PathVariable("idClient") Long idClient,
                                            @RequestBody Client client){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClient(idClient,client));
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity<Void> deleteClient(@PathVariable("idClient") Long idClient){
        clientService.deleteClient(idClient);
        return ResponseEntity.noContent().build();
    }

}
