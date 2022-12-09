package com.twms.wms.repositories;

import com.twms.wms.entities.SKU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SKURepository extends JpaRepository<SKU, Long> {

    public Page<SKU> findByNameContainingIgnoreCase(String searchTerm, Pageable page);

    public List<SKU> findByNameAndCategoryIdAndMeasurementUnitId(String name,
                                                                 Long categoryId,
                                                                 Long unitId);

    public List<SKU> findByIdIn(List<Long> ids);

    public boolean existsByCategoryId(Long categoryId);

    public boolean existsByMeasurementUnitId(Long measurementUnitId);

}
