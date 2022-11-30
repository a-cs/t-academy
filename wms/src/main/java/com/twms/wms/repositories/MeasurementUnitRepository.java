package com.twms.wms.repositories;

import com.twms.wms.entities.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnit, Long> {

    public List<MeasurementUnit> findByDescriptionContainingIgnoreCaseOrSymbolContainingIgnoreCase(String searchDescription, String searchSymbol);
}
