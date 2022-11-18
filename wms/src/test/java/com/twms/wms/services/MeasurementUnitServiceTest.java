package com.twms.wms.services;

import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.repositories.MeasurementUnitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class MeasurementUnitServiceTest {
    @InjectMocks
    private MeasurementUnitService measurementUnitService;
    @Mock
    private MeasurementUnitRepository measurementUnitRepository;

    MeasurementUnit measurementUnit;
    @BeforeEach
    void setUp() {
        measurementUnit = new MeasurementUnit();
        measurementUnit.setId(1L);
        measurementUnit.setDescription("Kilogram");
        measurementUnit.setSymbol("KG");
    }
    @Test
    public void shouldReturnMeasurementUnitWhenCreating() {
        Mockito.when(measurementUnitRepository.save(measurementUnit)).thenReturn(measurementUnit);

        Assertions.assertNotNull(measurementUnitService.create(measurementUnit));
    }

    @Test
    public void shouldDoNothingWhenDeletingExistingEntity() {
        Mockito.when(measurementUnitRepository.findById(measurementUnit.getId()))
                                              .thenReturn(Optional.of(measurementUnit));
        Mockito.doNothing().when(measurementUnitRepository).delete(measurementUnit);

        Assertions.assertDoesNotThrow(() -> measurementUnitService.delete(measurementUnit.getId()));
    }

    @Test
    public void shouldReturnPageWhenReading() {
        Page<MeasurementUnit> page = Mockito.mock(Page.class);
        Pageable pageable = PageRequest.of(0,2);
        Mockito.when(measurementUnitRepository.findAll(pageable))
                                                .thenReturn(page);

        Assertions.assertNotNull(measurementUnitService.read(pageable));
    }

    @Test
    public void shouldReturnUpdatedEntityWhenUpdating() {
        Mockito.when(measurementUnitRepository.findById(measurementUnit.getId()))
                                              .thenReturn(Optional.of(measurementUnit));
        Mockito.when(measurementUnitRepository.save(measurementUnit)).thenReturn(measurementUnit);

        MeasurementUnit newMeasurementUnit = new MeasurementUnit();
        newMeasurementUnit.setId(1L);
        newMeasurementUnit.setDescription("KILOGRAM");
        newMeasurementUnit.setSymbol("KG");

        Assertions.assertNotNull(measurementUnitService.update(measurementUnit.getId(), newMeasurementUnit));

        Assertions.assertEquals("KILOGRAM", measurementUnit.getDescription());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenIdNotFound() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> measurementUnitService.read(1L));
    }
}
