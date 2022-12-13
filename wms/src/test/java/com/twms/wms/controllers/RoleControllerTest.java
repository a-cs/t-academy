package com.twms.wms.controllers;

import com.twms.wms.services.RoleService;
import org.junit.jupiter.api.Test;
import com.twms.wms.entities.Role;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService service;

    @Test
    public void shouldReturnOkAfterGettingAllRoles() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/roles")
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }
    public void shouldReturnOkWhenGetAllRoles() throws Exception {
        List<Role> expceted = new ArrayList<>();
        Mockito.when(service.getAllRoles()).thenReturn(expceted);

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk());
    }
}
