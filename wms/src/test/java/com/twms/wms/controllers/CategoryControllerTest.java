package com.twms.wms.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twms.wms.entities.Category;
import com.twms.wms.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.awt.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.accept;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnOkWhenGetCategories() throws Exception {
        ResultActions result = mockMvc.perform(get("/category")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCreatedWhenInsertCategory() throws Exception {
        Category category = new Category();
        category.setName("CategoryName");

        String categoryString = objectMapper.writeValueAsString(category);

        ResultActions result = mockMvc.perform(post("/category")
                .content(categoryString)
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.name").value("CategoryName"));
    }

    @Test
    public void shouldReturnOkWhenUpdateCategory() throws Exception {
        Category category = new Category();
        category.setName("CategoryName");

        Mockito.when(service.update(eq(1L),any())).thenReturn(category);

        String categoryString = objectMapper.writeValueAsString(category);

        ResultActions result = mockMvc.perform(put("/category/{idCategoy}", 1L)
                .content(categoryString)
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.name").value("CategoryName"));
    }

    @Test
    public void shouldReturnVoidWhenDeleteCategory() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/category/{idCategory}",1L)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnOkWhenGettingValidCategoryById() throws Exception {
        Category category = new Category();
        category.setName("CategoryName");
        Mockito.when(service.readById(anyLong())).thenReturn(category);

        mockMvc.perform(get("/category/{categoryId}", anyLong())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
