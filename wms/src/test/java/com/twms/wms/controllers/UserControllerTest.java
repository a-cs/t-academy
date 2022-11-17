package com.twms.wms.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.dtos.UserDTO;
import com.twms.wms.entities.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        result.andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnOkWhenGettingUserByUsername() throws Exception {

        ResultActions result = mockMvc.perform(get("/user/manage/userTest")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkWhenUpdatingUserAccessLevel() throws Exception {

        ResultActions result = mockMvc.perform(put("/user/permissions/1")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

}
