package com.twms.wms.services;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.entities.*;
import com.twms.wms.enums.AccessLevel;
import com.twms.wms.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {
    @InjectMocks
    @Spy
    private ClientService clientService;

    @Mock
    private ClientRepository repository;

    @Mock
    private UserService userService;

    @Mock
    private AddressService addressService;

    @Test
    public void ShouldRespondNotNullWhenSavingClient(){
        Client client = new Client();

        client.setName("Teste");
        client.setCNPJ("00623904000173");
        client.setEmail("client@email.com");

        User user = new User();
        user.setUsername("userTest");
        user.setPassword("passwordTest");

        Address address = new Address();
        address.setId(1L);

        client.setAddress(address);

        Mockito.when(userService.createClientUser(client.getCNPJ(), client.getEmail())).thenReturn(user);

        Mockito.when(repository.findByCNPJ(client.getCNPJ())).thenReturn(new ArrayList<Client>());

        Mockito.when(repository.findByName(client.getName())).thenReturn(new ArrayList<Client>());

        Mockito.when(addressService.getById(client.getAddress().getId())).thenReturn(new Address());

        Mockito.when(clientService.saveClient(client)).thenReturn(client);

        Assertions.assertNotNull(clientService.createClient(client));

        Mockito.verify(repository, Mockito.times(1)).save(client);
    }

    @Test
    public void shouldReturnClientWhenSearchedById(){
        Client client = new Client();

        client.setName("Teste");
        client.setCNPJ("00623904000173");

        Mockito.when(repository.findById(eq(1L))).thenReturn(Optional.of(client));
        Assertions.assertNotNull(clientService.readClientById(1L));
    }

    @Test
    public void shouldReturnVoidWhenDeletingAClient(){
        Client client = new Client();

        client.setName("Teste");
        client.setCNPJ("00623904000173");
        client.setId(1L);

        Mockito.when(repository.findById(client.getId())).thenReturn(Optional.of(client));
        Mockito.doNothing().when(repository).delete(client);

        Assertions.assertDoesNotThrow(()->clientService.deleteClient(client.getId()));
        Mockito.verify(repository, Mockito.times(1)).delete(client);
    }

    @Test
    public void shouldReturnModifiedClientWhenUpdate(){
        Client client = new Client();
        Address address = new Address();
        ClientService clientService1 = Mockito.spy(clientService);

        address.setId(1L);

        client.setName("Teste");
        client.setCNPJ("00623904000173");
        client.setId(1L);
        client.setAddress(address);


        Mockito.doReturn(client).when(clientService).readClientById(any());
        //Mockito.when(repository.findById(any())).thenReturn((Optional<Client>) Optional.of(client));
        Mockito.when(addressService.post(any())).thenReturn(new Address());
        Mockito.when(clientService.saveClient(any())).thenReturn(client);

        Assertions.assertDoesNotThrow(()->clientService.updateClient(client.getId(), client));
        Mockito.verify(repository, Mockito.times(1)).save(client);

    }

}
