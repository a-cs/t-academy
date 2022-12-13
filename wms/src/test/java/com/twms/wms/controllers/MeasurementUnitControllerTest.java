package com.twms.wms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.services.MeasurementUnitService;

import org.junit.jupiter.api.BeforeEach;
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

import javax.persistence.EntityNotFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
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
    public void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;


            Mockito.doNothing().when(measurementUnitService).delete(existingId);
            //Mockito.when()
            Mockito.doThrow(EntityNotFoundException.class).when(measurementUnitService).delete(nonExistingId);


    }

    @Test
    public void shouldReturnOKWhenReading() throws Exception{
        ResultActions resultActions = mockMvc.perform(
                        get("/measurement-unit")
                                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCreatedWhenCreating() throws Exception {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        measurementUnit.setDescription("Kilogram");
        measurementUnit.setSymbol("KG");

        String measurementUnitString = objectMapper.writeValueAsString(measurementUnit);
        ResultActions resultActions = mockMvc.perform(
                                post("/measurement-unit")
                                        .content(measurementUnitString)
                                        .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNoContentWhenDeleting() throws Exception{
        ResultActions resultActions = mockMvc.perform(
                                delete("/measurement-unit/{id}",
                                                                existingId));
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnOkWhenGetPageableCategories() throws Exception {
        ResultActions result = mockMvc.perform(get("/measurement-unit/pages")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenDeletingNonExistentID() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                delete("/measurement-unit/{id}", nonExistingId));
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOKWhenUpdating() throws Exception {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        measurementUnit.setId(existingId);
        measurementUnit.setDescription("Kilogram");
        measurementUnit.setSymbol("KG");

        Mockito.when(measurementUnitService.update(eq(measurementUnit.getId()),
                                                   any())).thenReturn(measurementUnit);

        String measurementUnitString = objectMapper.writeValueAsString(measurementUnit);
        ResultActions resultActions = mockMvc.perform(put(
                                             "/measurement-unit/{id}", measurementUnit.getId())
                                             .content(measurementUnitString)
                                             .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.description").exists());
        resultActions.andExpect(jsonPath("$.description").value("Kilogram"));
    }

    @Test
    public void shouldReturnOkWhenSearchByName() throws Exception {
        List<MeasurementUnit> expected = new ArrayList<>();
        Mockito.when(measurementUnitService.searchTerm(anyString())).thenReturn(expected);

        mockMvc.perform(get("/measurement-unit/search")
                .param("term", anyString()))
                .andExpect(status().isOk());
    }
}
