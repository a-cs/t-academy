package com.twms.wms.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.entities.*;
import com.twms.wms.services.ClientService;
import com.twms.wms.services.WarehouseSlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class WarehouseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WarehouseSlotService service;

    @MockBean
    private ClientService clientService;

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

    final Long branchId = warehouseSlotId.getBranch().getId();
    final String aisleId = warehouseSlotId.getAisle();
    final Integer bayId = warehouseSlotId.getBay();

    @BeforeEach()
    public void setUp() {
        branch.setId(1L);
    }

    @Test
    public void shouldCreateNewWarehouseSlot() throws Exception {
        String addressAsString = objectMapper.writeValueAsString(warehouseSlot);

        ResultActions result = mockMvc.perform(post("/warehouseSlot")
                        .content(addressAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllWarehouseSlots() throws Exception {
        mockMvc.perform(get("/warehouseSlot")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetAllFromBranch() throws Exception {
        mockMvc.perform(get("/warehouseSlot/branch/" + anyLong())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetAllFromClientId() throws Exception {
        mockMvc.perform(get("/warehouseSlot/client/" + anyLong())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetSingleByFullKey() throws Exception {
        mockMvc.perform(get("/warehouseSlot/branch/" + anyLong()
                            + "/aisle/" + ArgumentMatchers.any()
                            + "/bay/" + anyInt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

