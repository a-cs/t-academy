package com.twms.wms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.dtos.TransactionDTO;
import com.twms.wms.entities.*;
import com.twms.wms.enums.AccessLevel;
import com.twms.wms.enums.TransactionType;
import com.twms.wms.services.TransactionService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    Transaction transaction = new Transaction();
    TransactionDTO transactionDTO;
    String transactionJson;

    @BeforeEach
    public void setup() throws Exception {
        transaction.setId(1);
        transaction.setDate(Timestamp.from(Instant.now()));
        transaction.setQuantity(10);
        Branch branch = new Branch();
        branch.setName("branch name");
        WarehouseSlot wSlot = new WarehouseSlot();
        wSlot.setWarehouseSlotId(new WarehouseSlotId(branch, 1, "A"));
        transaction.setWarehouseSlot(wSlot);
        Client client = new Client();
        client.setName("client");
        transaction.setClient(client);
        SKU sku = new SKU();
        sku.setName("sku name");
        transaction.setSku(sku);
        User user = new User();
        user.setUsername("username");
        user.setAccessLevel(new Role(1L, AccessLevel.ROLE_ADMIN));
        transaction.setUser(user);
        transaction.setType(TransactionType.IN);

        transactionDTO = new TransactionDTO(transaction);

        transactionJson = "{\"id\":1,\"quantity\":10,\"type\":\"IN\",\"warehouseSlot\":{\"warehouseSlotId\":{\"branch\":{\"id\":null,\"name\":\"branch name\",\"max_rows\":0,\"max_columns\":0,\"address\":null},\"bay\":1,\"aisle\":\"A\"},\"quantity\":0,\"sku\":null,\"client\":null,\"arrivalDate\":null},\"client\":{\"id\":null,\"name\":\"client\",\"email\":null,\"address\":null,\"user\":null,\"cnpj\":null},\"sku\":{\"id\":null,\"name\":\"sku name\",\"category\":null,\"measurementUnit\":null},\"user\":{\"id\":1,\"username\":\"username\"}}";
    }

    @Test
    public void ShouldReturnOkWhenGettingTransactionDTOPaginated() throws Exception {

        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(transactionDTO);
        Page<TransactionDTO> dtoPage = new PageImpl<>(transactionDTOList);

        Mockito.when(transactionService.readAllTransactionsPaginatedViaDTO(any(Pageable.class)))
                .thenReturn(dtoPage);

        ResultActions result = mockMvc.perform(get("/transaction/prettify")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void ShouldReturnOkWhenGettingAllTransactions() throws Exception {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        Mockito.when(transactionService.readAllTransactions())
                .thenReturn(transactionList);

        ResultActions result = mockMvc.perform(get("/transaction")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void ShouldReturnOkWhenGettingTransactionById() throws Exception {

        Mockito.when(transactionService.readTransactionById(transaction.getId()))
                .thenReturn(transaction);

        ResultActions result = mockMvc.perform(get("/transaction/"+transaction.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void ShouldReturnCreatedWhenPostingTransaction() throws Exception {

        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(transactionDTO);

        Mockito.when(transactionService.createTransaction(transaction))
                .thenReturn(transactionDTOList);
        System.out.println(objectMapper.writeValueAsString(transaction));
        ResultActions result = mockMvc.perform(post("/transaction")
                        .content(transactionJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

//    @Test
//    public void ShouldReturnCreatedWhenUpdatingTransaction() throws Exception {
//
//        Mockito.when(transactionService.updateTransaction(transaction.getId(), transaction))
//                .thenReturn(transactionDTO);
//
//        ResultActions result = mockMvc.perform(put("/transaction/" + transaction.getId())
//                        .content(transactionJson)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void ShouldReturnNoContentWhenDeletingById() throws Exception {
//
//        Mockito.doNothing().when(transactionService).deleteTransaction(anyLong());
//
//        ResultActions result = mockMvc.perform(delete("/transaction/" + transaction.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }

}
