package com.twms.wms.controllers;

<<<<<<< HEAD

import com.twms.wms.services.ClientService;
import com.twms.wms.services.RoleService;
import org.junit.jupiter.api.Test;
=======
import com.twms.wms.entities.Role;
import com.twms.wms.services.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
>>>>>>> f19cb49be298bcac07c45965093b6045a566a9ad
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
<<<<<<< HEAD
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
=======
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
>>>>>>> f19cb49be298bcac07c45965093b6045a566a9ad
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService service;

    @Test
<<<<<<< HEAD
    public void shouldReturnOkAfterGettingAllRoles() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/roles")
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
=======
    public void shouldReturnOkWhenGetAllRoles() throws Exception {
        List<Role> expceted = new ArrayList<>();
        Mockito.when(service.getAllRoles()).thenReturn(expceted);

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk());
>>>>>>> f19cb49be298bcac07c45965093b6045a566a9ad
    }
}
