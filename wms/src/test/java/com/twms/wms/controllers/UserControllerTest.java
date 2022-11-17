package com.twms.wms.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.dtos.UserDTO;
import com.twms.wms.entities.Role;
import com.twms.wms.entities.User;
import com.twms.wms.enums.AccessLevel;
import com.twms.wms.services.UserService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    User user;
    String userJson;

    @BeforeEach
    public void setup() throws Exception {
        user = new User();
        user.setUsername("userTest");
        user.setPassword("passwordTest");

        userJson = objectMapper.writeValueAsString(user);
    }

    @Test
    public void shouldReturnCreatedWhenCreatingUser() throws Exception {

        Mockito.when(userService.createUser(user)).thenReturn(new UserDTO(user));

        ResultActions result = mockMvc.perform(post("/user/signup")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(jsonPath("$.username").value("userTest"));
        result.andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnOkWhenGettingUserByUsername() throws Exception {

        Mockito.when(userService.getUserByUsername(user.getUsername())).thenReturn(new UserDTO(user));

        ResultActions result = mockMvc.perform(get("/user/search?username=userTest")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.username").value("userTest"));
    }
    @Test
    public void shouldReturnOkWhenGettingAllUsers() throws Exception {

        Mockito.when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>());

        ResultActions result = mockMvc.perform(get("/user/all")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    public void shouldReturnOkWhenUpdatingUserAccessLevel() throws Exception {
        Role role = new Role(1L, AccessLevel.ROLE_CLIENT);

        user.addAccessLevel(role);

        String rolestr = objectMapper.writeValueAsString(role);

        Mockito.when(userService.updateUserAccessLevel(any(), eq(1L))).thenReturn(new UserDTO(user));

        ResultActions result = mockMvc.perform(put("/user/permissions/1")
                .content(rolestr)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.username").value("userTest"));
        result.andExpect(jsonPath("$.accessLevel[0].authority").value(role.getAuthority().name()));

    }

}
