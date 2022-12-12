package com.twms.wms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.entities.Category;
import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.entities.SKU;
import com.twms.wms.services.SKUService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class SKUControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SKUService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnOkAfterGet() throws Exception {
        ResultActions result = mockMvc.perform(get("/sku").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCreatedAfterPost() throws Exception{
        SKU sku = new SKU();
        Category cat = new Category();
        MeasurementUnit unit = new MeasurementUnit();
        cat.setId(1L);
        unit.setId(1L);
        sku.setId(1L);
        sku.setName("testName");
        sku.setCategory(cat);
        sku.setMeasurementUnit(unit);

        String skuString = objectMapper.writeValueAsString(sku);

        Mockito.when(service.save(sku)).thenReturn(sku);
        ResultActions result = mockMvc.perform(post("/sku")
                .content(skuString)
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("testName"))
                ;
    }

    @Test
    public void shouldReturnOKAfterPut() throws Exception{
        SKU sku = new SKU();
        Category cat = new Category();
        MeasurementUnit unit = new MeasurementUnit();
        cat.setId(1L);
        unit.setId(1L);
        sku.setId(1L);
        sku.setName("testName");
        sku.setCategory(cat);
        sku.setMeasurementUnit(unit);
        String skuString = objectMapper.writeValueAsString(sku);

        Mockito.when(service.update(1L ,sku)).thenReturn(sku);
        ResultActions result = mockMvc.perform(put("/sku/{skuId}", sku.getId())
                .content(skuString)
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("testName"))
        ;
    }

    @Test
    public void shouldReturnNothingAfterDelete() throws Exception {
        ResultActions result = mockMvc.perform(delete("/sku/{skuId}", 1L).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNoContent());
    }
}
