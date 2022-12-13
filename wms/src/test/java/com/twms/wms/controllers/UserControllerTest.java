package com.twms.wms.controllers;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles(profiles = "test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    User user;
    UserDTO userDTO;
    String userJson;

    @BeforeEach
    public void setup() throws Exception {
        user = new User();
        user.setId(1L);
        user.setUsername("userTest");
        user.setEmail("user@email.com");
        user.setAccessLevel(new Role(1L, AccessLevel.ROLE_ADMIN));
        user.setPassword("passwordTest");

        userDTO = new UserDTO(user);

        userJson =  "{\"id\":1,\"username\":\"userTest\",\"password\":\"passwordTest\",\"email\":\"user@email.com\",\"accessLevel\":{\"id\":1,\"authority\":\"ROLE_ADMIN\"},\"enabled\":false}";
    }

    @Test
    public void shouldReturnCreatedWhenCreatingUser() throws Exception {

        Mockito.when(userService.createUser(any(User.class))).thenReturn(userDTO);

        ResultActions result = mockMvc.perform(post("/user/signup")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("user@email.com"))
                .andExpect(jsonPath("$.username").value("userTest"));
    }

    @Test
    public void shouldReturnUserDTOPaginated() throws Exception {

        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(userDTO);
        Page<UserDTO> dtoPage = new PageImpl<>(userDTOList);
        Mockito.when(userService.getUserFilteredByUsername(any(String.class), any(Pageable.class)))
                .thenReturn(dtoPage);

        ResultActions result = mockMvc.perform(get("/user/search?username=userTest&page=0&size=1")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].username").value("userTest"));
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnFilteredUserDTOWhenUpdatingUser() throws Exception {

        Mockito.when(userService.updateUser(any(UserDTO.class), any(Long.class))).thenReturn(userDTO);

        ResultActions result = mockMvc.perform(put("/user/" + user.getId().toString())
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("userTest"));
    }

    @Test
    public void shouldReturnListOfUserDTO() throws Exception {

        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(userDTO);
        Mockito.when(userService.getAllUsers()).thenReturn(userDTOList);

        ResultActions result = mockMvc.perform(get("/user/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("userTest"));
    }

    @Test
    public void shouldReturnAllUserDTOPaginated() throws Exception {

        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(userDTO);
        Page<UserDTO> dtoPage = new PageImpl<>(userDTOList);
        Mockito.when(userService.getUsersPaginated(any(Pageable.class)))
                .thenReturn(dtoPage);

        ResultActions result = mockMvc.perform(get("/user/pages?page=0&size=1")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].username").value("userTest"));
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnOkStatusWhenSetPassword() throws Exception {
        ResultActions result = mockMvc.perform(put("/user/setpassword"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkStatusWhenResetPassword() throws Exception {
        ResultActions result = mockMvc.perform(get("/user/resetpassword?email=" + user.getEmail()))
                .andExpect(status().isOk());
    }
}