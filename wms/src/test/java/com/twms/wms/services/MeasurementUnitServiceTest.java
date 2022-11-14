package com.twms.wms.services;

import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.repositories.MeasurementUnitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class MeasurementUnitServiceTest {
    @InjectMocks
    private MeasurementUnitService measurementUnitService;
    @Mock
    private MeasurementUnitRepository measurementUnitRepository;

    @Test
    public void returnMeasurementUnitWhenCreating() {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        measurementUnit.setDescription("Kilogram");
        measurementUnit.setSymbol("KG");

        Mockito.when(measurementUnitRepository.save(measurementUnit)).thenReturn(measurementUnit);

        Assertions.assertNotNull(measurementUnitService.create(measurementUnit));
    }
}
