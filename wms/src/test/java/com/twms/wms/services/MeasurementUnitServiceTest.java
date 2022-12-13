package com.twms.wms.services;

import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.repositories.MeasurementUnitRepository;
import com.twms.wms.repositories.SKURepository;
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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class MeasurementUnitServiceTest {
    @InjectMocks
    private MeasurementUnitService measurementUnitService;
    @Mock
    private MeasurementUnitRepository measurementUnitRepository;

    @Mock
    private SKURepository skuRepository;

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

        Mockito.when(skuRepository.existsByMeasurementUnitId(any())).thenReturn(false);

        Assertions.assertDoesNotThrow(() -> measurementUnitService.delete(measurementUnit.getId()));
    }
    @Test
    public void shouldThrowExceptionWhenDeletingALinkedField() {

        Mockito.when(measurementUnitRepository.findById(measurementUnit.getId()))
                                              .thenReturn(Optional.of(measurementUnit));
        Mockito.doNothing().when(measurementUnitRepository).delete(measurementUnit);

        Mockito.when(skuRepository.existsByMeasurementUnitId(any())).thenReturn(true);

        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,() -> measurementUnitService.delete(measurementUnit.getId()));

    }

    @Test
    public void shouldReturnListWhenReadAll(){
        List<MeasurementUnit> mus = new ArrayList<>();
        Mockito.when(measurementUnitRepository.findAll()).thenReturn(mus);

        Assertions.assertNotNull(measurementUnitService.read());

        Mockito.verify(measurementUnitRepository,Mockito.times(1)).findAll();

    }

    @Test
    public void shouldReturnListWhenSearchedTerm(){
        List<MeasurementUnit> mus = new ArrayList<>();
        Mockito.when(measurementUnitRepository.findByDescriptionContainingIgnoreCaseOrSymbolContainingIgnoreCase(any(),any())).thenReturn(mus);

        Assertions.assertNotNull(measurementUnitService.searchTerm("any"));

        Mockito.verify(measurementUnitRepository,Mockito.times(1)).findByDescriptionContainingIgnoreCaseOrSymbolContainingIgnoreCase(any(),any());

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
