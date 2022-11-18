package com.twms.wms.services;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.entities.Branch;
import com.twms.wms.entities.Client;
import com.twms.wms.entities.Role;
import com.twms.wms.entities.User;
import com.twms.wms.enums.AccessLevel;
import com.twms.wms.repositories.ClientRepository;
import com.twms.wms.repositories.RoleRepository;
import com.twms.wms.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository repository;

    @Mock
    private UserService userService;


    @Test
    public void ShouldRespondNotNullWhenSavingClient(){
        Client client = new Client();

        client.setName("Teste");
        client.setCNPJ("00623904000173");

        User user = new User();
        user.setUsername("userTest");
        user.setPassword("passwordTest");

        Mockito.when(userService.createClientUser(client.getCNPJ())).thenReturn(user);

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

        client.setName("Teste");
        client.setCNPJ("00623904000173");
        client.setId(1L);

        Mockito.when(clientService.saveClient(client)).thenReturn(client);
        Mockito.when(repository.findById(client.getId())).thenReturn(Optional.of(client));

        Assertions.assertDoesNotThrow(()->clientService.updateClient(client.getId(), client));
        Mockito.verify(repository, Mockito.times(1)).save(client);

    }

}
