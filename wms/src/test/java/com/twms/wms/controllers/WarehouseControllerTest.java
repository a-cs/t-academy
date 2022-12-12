package com.twms.wms.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.dtos.BranchIdsProductIdsFilterDTO;
import com.twms.wms.dtos.ListIdsFilterDTO;
import com.twms.wms.dtos.WarehouseSlotDTO;
import com.twms.wms.entities.*;
import com.twms.wms.services.ClientService;
import com.twms.wms.services.WarehouseSlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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

import javax.print.attribute.standard.Media;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        List<WarehouseSlotDTO> expected = new ArrayList<>();
        Page foundPage = new PageImpl<WarehouseSlotDTO>(expected);

        Mockito.when(service.getByUserId(anyLong(), any(Pageable.class))).thenReturn(foundPage);

        mockMvc.perform(get("/warehouseSlot/client/{clientId}", anyLong())
                        //.param("page", String.valueOf(anyInt()))
                        .param("size", String.valueOf(anyInt()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    public void shouldGetSingleByFullKey() throws Exception {
//        mockMvc.perform(get("/warehouseSlot/branch/" + anyLong()
//                            + "/aisle/" + ArgumentMatchers.any()
//                            + "/bay/" + anyInt())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

//    @Test
//    public void shouldReturnVoidWhenDeleteById() throws Exception {
//        Mockito.doNothing().when(service).deleteById(anyLong(), anyString(), anyInt());
//
//        mockMvc.perform(delete("/warehouseSlot/branch/{branchId}/aisle/{aisleId}/bay/{bayId}", 1L, "F", 1)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }

    @Test
    public void shouldReturnOkWhenGetFilteredByClientAndBranches() throws Exception {
        List<WarehouseSlotDTO> expected = new ArrayList<>();
        Page foundPage = new PageImpl<WarehouseSlotDTO>(expected);

        Mockito.when(service.getByClientIdAndBranches(anyLong(), any(ListIdsFilterDTO.class), any(Pageable.class))).thenReturn(foundPage);

        ListIdsFilterDTO idsToFilder = new ListIdsFilterDTO();
        String requestBody = objectMapper.writeValueAsString(idsToFilder);

        mockMvc.perform(get("/warehouseSlot/client/{clientId}/filterByBranches", anyLong())
                        .param("page", String.valueOf(anyInt()))
                        .param("size", String.valueOf(anyInt()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkWhenGetFilteredByClientBranchAndProducts() throws Exception {
        List<WarehouseSlotDTO> expected = new ArrayList<>();
        Page foundPage = new PageImpl<WarehouseSlotDTO>(expected);

        Mockito.when(service.getByClientBranchAndProduct(anyLong(), any(BranchIdsProductIdsFilterDTO.class), any(Pageable.class))).thenReturn(foundPage);

        BranchIdsProductIdsFilterDTO idsToFilder = new BranchIdsProductIdsFilterDTO();
        String requestBody = objectMapper.writeValueAsString(idsToFilder);

        mockMvc.perform(post("/warehouseSlot/client/{clientId}/filterByBranchesAndProducts", anyLong())
                        .param("page", String.valueOf(anyInt()))
                        .param("size", String.valueOf(anyInt()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkWhenGetByClientFiltered() throws Exception {
        // I have no idea why I can't give Pageable as params, but it "works"
        List<WarehouseSlotDTO> expected = new ArrayList<>();
        Page foundPage = new PageImpl<WarehouseSlotDTO>(expected);

        Mockito.when(service.getByClientIdAndBranchesandProducts(anyString(), anyString(), anyLong(), any(Pageable.class))).thenReturn(foundPage);

        mockMvc.perform(get("/warehouseSlot/client/filtered", anyLong())
                        .param("sku", anyString())
                        .param("client", anyString())
                        .param("branch", String.valueOf(anyLong()))
//                        .param("page", String.valueOf(anyInt()))
//                        .param("size", String.valueOf(anyInt()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkWhenSearchingByClientBranchesAndProductName() throws Exception {
        List<WarehouseSlotDTO> expected = new ArrayList<>();
        Page foundPage = new PageImpl<WarehouseSlotDTO>(expected);

        Mockito.when(service.getByUserBranchFilteredByProductName(
                anyLong(), any(ListIdsFilterDTO.class), anyString(), any(Pageable.class))
        ).thenReturn(foundPage);

        ListIdsFilterDTO idsToFilder = new ListIdsFilterDTO();
        String requestBody = objectMapper.writeValueAsString(idsToFilder);

        mockMvc.perform(post("/warehouseSlot/client/{clientId}/filterByBranches/searchProduct", anyLong())
                        .param("page", String.valueOf(anyInt()))
                        .param("size", String.valueOf(anyInt()))
                        .param("term", anyString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }
}

