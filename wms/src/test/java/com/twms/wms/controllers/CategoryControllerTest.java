package com.twms.wms.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twms.wms.entities.Category;
import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
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

    @Mock
    private Pageable pageableMock;

    @Mock
    private Page<Category> categoriesPage;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnOkWhenGetPageableCategories() throws Exception {
        ResultActions result = mockMvc.perform(get("/category/pageable")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkWhenGetCategories() throws Exception {
        ResultActions result = mockMvc.perform(get("/category")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkWhenGetByValidCategoryId() throws Exception {
        when(service.readById(anyLong())).thenReturn(new Category());

        ResultActions result = mockMvc.perform(get("/category/{idcategory}", anyLong())
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void shouldThrowExceptionWhenGetByInvalidCategoryId() throws Exception {
        when(service.readById(anyLong())).thenThrow(EntityNotFoundException.class);

        ResultActions result = mockMvc.perform(get("/category/{idcategory}", anyLong())
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCreatedWhenInsertCategory() throws Exception {
        Category category = new Category();
        category.setName("CategoryName");

        String categoryString = objectMapper.writeValueAsString(category);

        Mockito.when(service.createGategory(category)).thenReturn(category);

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
    public void shouldReturnVoidWhenDeleteUnassociatedCategory() throws Exception {
        doNothing().when(service).delete(anyLong());

        ResultActions resultActions = mockMvc.perform(delete("/category/{idCategory}",1L)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void shouldThrowExcpetionOnDeletingAssociatedItem() throws Exception {
        doThrow(SQLIntegrityConstraintViolationException.class).when(service).delete(anyLong());

        ResultActions resultActions = mockMvc.perform(delete("/category/{idCategory}",1L)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNotFound());
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

    @Test
    public void shouldReturnOkWhenSearching() throws Exception
    {
        when(service.searchTerm("test", pageableMock)).thenReturn(categoriesPage);

        ResultActions result = mockMvc.perform(get("/category/search")
                        .param("term", "test")
                        .param("page", "1")
                        .param("size", "1")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }
}
