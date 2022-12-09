package com.twms.wms.services;

import com.twms.wms.entities.SKU;
import com.twms.wms.repositories.SKURepository;
import com.twms.wms.repositories.WarehouseSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SKUService {
    @Autowired
    SKURepository skuRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    MeasurementUnitService measurementUnitService;

    @Autowired
    WarehouseSlotRepository warehouseSlotRepository;

    public List<SKU> read(){
        return skuRepository.findAll();
    }

    public SKU findById(Long id){
        return skuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("SKU not found"));
    }

    @Transactional
    public SKU save(SKU sku) {
        if(sku.getCategory().getId() == null)
            throw new IllegalArgumentException("Category Id is missing.");
        if(sku.getMeasurementUnit().getId() == null)
            throw new IllegalArgumentException("Measurement Unit Id is missing.");
        if(skuRepository.findByNameAndCategoryIdAndMeasurementUnitId(sku.getName(), sku.getCategory().getId(),sku.getMeasurementUnit().getId()).isEmpty()){
            throw new IllegalArgumentException("Element Duplicated");
        }
        categoryService.readById(sku.getCategory().getId());
        measurementUnitService.read(sku.getMeasurementUnit().getId());
        return skuRepository.save(sku);
    }

    public SKU update(Long id, SKU sku){
        SKU s = this.findById(id);
        s.setName(sku.getName());
        s.setCategory(sku.getCategory());
        s.setMeasurementUnit(sku.getMeasurementUnit());
        return this.save(s);
    }

    public void delete(Long id) throws SQLIntegrityConstraintViolationException {
        if (warehouseSlotRepository.existsBySkuId(id)) {
            throw new SQLIntegrityConstraintViolationException("Cannot delete product because it is stored somewhere in the warehouse.");
        }
        SKU s = this.findById(id);
        skuRepository.delete(s);
    }

    public Page<SKU> searchTerm(String searchTerm, Pageable pageable){
        String terms = searchTerm.replace("-", " ");
        return skuRepository.findByNameContainingIgnoreCase(terms, pageable);
    }

    public List<SKU> findAllByIds(List<Long> ids) {
        return skuRepository.findByIdIn(ids);
    }

    public Page<SKU> readPaginated(Pageable pageable) {
        return skuRepository.findAll(pageable);
    }
}
