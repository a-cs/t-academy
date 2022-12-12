package com.twms.wms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.entities.Branch;
import com.twms.wms.entities.Client;
import com.twms.wms.services.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    public void shouldReturnOkAfterGettingAllClient() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/client")
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void shouldReturnVoidAfterDeletingAClient() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/client/{idcategoria}",1L)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnCreatedAfterTheCreationOfClient() throws Exception {
        Client client = new Client();
        client.setName("Test");

        String clientString = objectMapper.writeValueAsString(client);

        Mockito.when(service.createClient(client)).thenReturn(client);
        ResultActions resultActions = mockMvc.perform(post("/client")
                .content(clientString)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test"));
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
