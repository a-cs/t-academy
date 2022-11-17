package com.twms.wms.services;

import com.twms.wms.entities.Category;
import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.entities.SKU;
import com.twms.wms.repositories.CategoryRepository;
import com.twms.wms.repositories.MeasurementUnitRepository;
import com.twms.wms.repositories.SKURepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class SKUServiceTest {

    @InjectMocks
    private SKUService skuServiceservice;

    @Mock
    private CategoryService categoryService;

    @Mock
    private MeasurementUnitService measurementUnitService;

    @Mock
    private SKURepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private MeasurementUnitRepository measurementUnitRepository;

    @Test
    public void shouldReturnListAfterRead() {
        List<SKU> list = new ArrayList<>();

        Mockito.when(repository.findAll()).thenReturn(list);
        Assertions.assertNotNull(skuServiceservice.read());
        Mockito.verify(repository,Mockito.times(1)).findAll();
    }

    @Test
    public void shouldReturnSKUAfterSave() {
        SKU sku = new SKU();
        Category cat = new Category();
        MeasurementUnit unit = new MeasurementUnit();
        cat.setId(1L);
        unit.setId(1L);
        sku.setId(1L);
        sku.setName("testName");
        sku.setCategory(cat);
        sku.setMeasurementUnit(unit);

        Mockito.when(repository.save(sku)).thenReturn(sku);
        Assertions.assertNotNull(skuServiceservice.save(sku));
        Assertions.assertEquals(skuServiceservice.save(sku), sku);
        Mockito.verify(repository,Mockito.times(2)).save(sku);
    }

    @Test
    public void shouldReturnSKUAfterUpdate() {
        SKU sku = new SKU();
        Category cat = new Category();
        MeasurementUnit unit = new MeasurementUnit();
        cat.setId(1L);
        unit.setId(1L);
        sku.setId(1L);
        sku.setName("testName");
        sku.setCategory(cat);
        sku.setMeasurementUnit(unit);

        Mockito.when(repository.save(sku)).thenReturn(sku);
        Mockito.when(repository.findById(sku.getId())).thenReturn(Optional.of(sku));
        Assertions.assertNotNull(skuServiceservice.update(sku.getId(), sku));
        Assertions.assertEquals(skuServiceservice.update(sku.getId(), sku), sku);
        Mockito.verify(repository,Mockito.times(2)).save(sku);
    }


    @Test
    public void shouldReturnNothingAfterDelete() {
        SKU sku = new SKU();
        sku.setId(1L);
        sku.setName("testName");

        Mockito.when(repository.findById(sku.getId())).thenReturn(Optional.of(sku));

        Mockito.doNothing().when(repository).delete(sku);
        Assertions.assertDoesNotThrow(() -> skuServiceservice.delete(sku.getId()));
        Mockito.verify(repository,Mockito.times(1)).delete(sku);
    }

    @Test
    public void shouldReturnExceptionWhenIdNotFound() {
        Assertions.assertThrows(EntityNotFoundException.class,() -> skuServiceservice.findById(1L));
    }
}
