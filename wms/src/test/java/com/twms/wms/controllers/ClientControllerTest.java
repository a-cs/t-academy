package com.twms.wms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.entities.Address;
import com.twms.wms.entities.Client;
import com.twms.wms.entities.Role;
import com.twms.wms.entities.User;
import com.twms.wms.enums.AccessLevel;
import com.twms.wms.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @Autowired
    ObjectMapper objectMapper;

    Client client = new Client();

    @BeforeEach
    public void setup() throws Exception {
        client.setId(1L);
        client.setName("client");
        client.setEmail("client@email.com");
        User user = new User();
        user.setUsername("username");
        user.setAccessLevel(new Role(1L, AccessLevel.ROLE_ADMIN));
        client.setUser(user);
        client.setCNPJ("12345678912345");
        client.setAddress(new Address());
    }

    @Test
    public void shouldReturnOkAfterSearchingClient() throws Exception {

        List<Client> clients = new ArrayList<>();
        clients.add(client);
        Page<Client> clientsPaginated = new PageImpl<>(clients);

        Mockito.when(service.searchTerm(anyString(), any(Pageable.class))).thenReturn(clientsPaginated);

        mockMvc.perform(get("/client/search?term=client&page=0&size=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("client"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnOkAfterGettingAllClientsPaginated() throws Exception {

        List<Client> clients = new ArrayList<>();
        clients.add(client);
        Page<Client> clientsPaginated = new PageImpl<>(clients);

        Mockito.when(service.readAllClientsPaginated(any(Pageable.class))).thenReturn(clientsPaginated);

        mockMvc.perform(get("/client/pages")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("client"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    public void shouldReturnOkAfterGettingAllClient() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/client")
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkAfterGettingClientById() throws Exception {
        Mockito.when(service.readClientById(anyLong())).thenReturn(client);

        mockMvc.perform(get("/client/" + client.getId())
                .content(objectMapper.writeValueAsString(client))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("client"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnVoidAfterDeletingAClient() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/client/{idcategoria}",1L)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnCreatedAfterTheCreationOfClient() throws Exception {
        Client client2 = new Client();
        client2.setName("client");

        String clientString = objectMapper.writeValueAsString(client2);

        Mockito.when(service.createClient(client2)).thenReturn(client);
        ResultActions resultActions = mockMvc.perform(post("/client")
                .content(clientString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("client"));
    }

    @Test
    public void shouldReturnTheModifiedClientAfterUpdate() throws Exception {
        Client client = new Client();
        client.setName("Test");

        String clientString = objectMapper.writeValueAsString(client);

        Mockito.when(service.updateClient(eq(1L),any())).thenReturn(client);
        ResultActions resultActions = mockMvc.perform(put("/client/{idClient}",1L)
                .content(clientString)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"));
    }

}
