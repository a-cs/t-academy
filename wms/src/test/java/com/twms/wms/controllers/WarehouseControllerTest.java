package com.twms.wms.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.entities.*;
import com.twms.wms.services.WarehouseSlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WarehouseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WarehouseSlotService service;

    @Autowired
    ObjectMapper objectMapper;

    Branch branch = new Branch();
    WarehouseSlotId warehouseSlotId = new WarehouseSlotId(branch, 7, "F");

    final WarehouseSlot warehouseSlot = new WarehouseSlot(
            warehouseSlotId,
            128,
            new SKU(),
            new Client(),
            Instant.now()
    );

    @BeforeEach()
    public void setUp() {
        branch.setId(1L);
    }

    @Test
    public void contextLoads(){}

    @Test
    public void shouldReturnCreatedWhenCreatingNewWarehouseSlot() throws Exception {
        String addressAsString = objectMapper.writeValueAsString(warehouseSlot);

        ResultActions result = mockMvc.perform(post("/warehouseSlot")
                        .content(addressAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void shouldReturnOkWhenGettingAllWarehouseSlot() throws Exception {
        mockMvc.perform(get("/warehouseSlot")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // TODO: extend test for this resource.
}

