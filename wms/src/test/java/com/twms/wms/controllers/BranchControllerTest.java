package com.twms.wms.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.entities.Branch;
import com.twms.wms.services.BranchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BranchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BranchService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void MustReturnCreatedAfterTheCreationOfBranch() throws Exception {
        Branch branch = new Branch();
        branch.setName("Test");

        String branchString = objectMapper.writeValueAsString(branch);

        Mockito.when(service.createBranch(branch)).thenReturn(branch);
        ResultActions resultActions = mockMvc.perform(post("/branch")
                                                      .content(branchString)
                                                      .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated())
                     .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    public void MustReturnOkAfterGettingAllBranchs() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/branch")
                                                      .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void MustReturnVoidAfterDeletingABranch() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/branch/{idcategoria}",1L)
                                                      .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNoContent());
    }

}
