package com.twms.wms.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twms.wms.entities.Address;
import com.twms.wms.services.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityNotFoundException;
import java.awt.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService service;

    @Autowired
    ObjectMapper objectMapper;

    final Address address = new Address();

    @BeforeEach
    void setUp() {
        address.setStreet("Rua João da Costa");
        address.setNumber("999");
        address.setCity("Brasília");
        address.setState("DF");
        address.setZipCode("01234567");
    }

    @Test
    public void shouldReturnOkWhenAddressIsValid() throws Exception {
        String addressAsString = objectMapper.writeValueAsString(address);
        ResultActions result = mockMvc.perform(post("/address")
                .content(addressAsString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequestWhenStreetIsBlank() throws Exception {
        address.setStreet("");
        String addressAsString = objectMapper.writeValueAsString(address);
        ResultActions result = mockMvc.perform(post("/address")
                        .content(addressAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenNumberIsBlank() throws Exception {
        address.setNumber("");
        String addressAsString = objectMapper.writeValueAsString(address);
        ResultActions result = mockMvc.perform(post("/address")
                        .content(addressAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenStateIsOver2Characters() throws Exception {
        address.setState("ABC");
        String addressAsString = objectMapper.writeValueAsString(address);
        ResultActions result = mockMvc.perform(post("/address")
                        .content(addressAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenStateIsUnder2Characters() throws Exception {
        address.setState("A");
        String addressAsString = objectMapper.writeValueAsString(address);
        ResultActions result = mockMvc.perform(post("/address")
                        .content(addressAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenZipIsOver8Characters() throws Exception {
        address.setZipCode("0123456789");
        String addressAsString = objectMapper.writeValueAsString(address);
        ResultActions result = mockMvc.perform(post("/address")
                        .content(addressAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturnBadRequestWhenZipIsUnder8Characters() throws Exception {
        address.setZipCode("0123456");
        String addressAsString = objectMapper.writeValueAsString(address);
        ResultActions result = mockMvc.perform(post("/address")
                        .content(addressAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenCityIsBlank() throws Exception {
        address.setCity("");
        String addressAsString = objectMapper.writeValueAsString(address);
        ResultActions result = mockMvc.perform(post("/address")
                        .content(addressAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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
    public void shouldReturnNotFoundWhenGettingByInvalidId() throws Exception {
        Mockito.when(service.getById(anyLong())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/address/{addressId}", anyLong())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNoContentWhenDelettingByValidId() throws Exception {
        Mockito.when(service.getById(anyLong())).thenReturn(address);

        mockMvc.perform(delete("/address/{addressId}", anyLong())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
