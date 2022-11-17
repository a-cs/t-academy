package com.twms.wms.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twms.wms.entities.Address;
import com.twms.wms.services.AddressService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityNotFoundException;
import java.awt.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService service;

    @Autowired
    ObjectMapper objectMapper;

    final Address address = new Address(1L,
            "Rua João da Costa",
            "999",
            "Brasília",
            "DF",
            "7777777"
    );

    @Test
    public void contextLoads(){}

    @Test
    public void shouldReturnCreatedWhenCreatingNewAddress() throws Exception {
        String addressAsString = objectMapper.writeValueAsString(address);

        ResultActions result = mockMvc.perform(post("/address")
                .content(addressAsString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void shouldReturnOkWhenGettingAllAddresses() throws Exception {
        mockMvc.perform(get("/address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkWhenGettingValidAddressById() throws Exception {
        Mockito.when(service.getById(anyLong())).thenReturn(address);

        mockMvc.perform(get("/address/{addressId}", anyLong())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNoContentWhenDelettingByValidId() throws Exception {
        Mockito.when(service.getById(anyLong())).thenReturn(address);

        mockMvc.perform(delete("/address/{addressId}", anyLong())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

}
