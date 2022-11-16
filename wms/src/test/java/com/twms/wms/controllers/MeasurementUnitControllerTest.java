package com.twms.wms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.services.MeasurementUnitService;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
public class MeasurementUnitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeasurementUnitService measurementUnitService;

    @Autowired
    ObjectMapper objectMapper;

    Long existingId;
    Long nonExistingId;
    @BeforeEach
    public void setUp() {
        existingId = 1L;
        nonExistingId = 2L;

        Mockito.doNothing().when(measurementUnitService).delete(existingId);
        Mockito.doThrow(EntityNotFoundException.class).when(measurementUnitService).delete(nonExistingId);
    }

    @Test
    public void returnsOKWhenReading() throws Exception{
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.get("/measurement-unit/read")
                                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void returnsCreatedWhenCreating() throws Exception {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        //measurementUnit.setId(1L);
        measurementUnit.setDescription("Kilogram");
        measurementUnit.setSymbol("KG");

        String measurementUnitString = objectMapper.writeValueAsString(measurementUnit);
        ResultActions resultActions = mockMvc.perform(
                                MockMvcRequestBuilders.post("/measurement-unit/create")
                                        .content(measurementUnitString)
                                        .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void returnsNoContentWhenDeleting() throws Exception{
        ResultActions resultActions = mockMvc.perform(
                                MockMvcRequestBuilders.delete("/measurement-unit/delete/{id}",
                                                                existingId));
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void throwsEntityNotFoundExceptionWhenDeletingNonExistentID() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/measurement-unit/delete/{id}", nonExistingId));
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());

//        resultActions.andExpect(res ->
//                Assertions.assertTrue(res.getResolvedException() instanceof EntityNotFoundException));
    }
}
