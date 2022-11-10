package com.twms.wms.services;

import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.repositories.MeasurementUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MeasurementUnitService {

    @Autowired
    MeasurementUnitRepository measurementUnitRepository;
    public Page<MeasurementUnit> read(Pageable pageable) {
        return  measurementUnitRepository.findAll(pageable);
    }

    @Transactional
    public MeasurementUnit create(MeasurementUnit measurementUnit) {
        MeasurementUnit newMeasurementUnit = new MeasurementUnit();
        newMeasurementUnit.setDescription(measurementUnit.getDescription());
        newMeasurementUnit.setSymbol(measurementUnit.getSymbol());

        return measurementUnitRepository.save(newMeasurementUnit);
    }

    public MeasurementUnit update(Long id, MeasurementUnit measurementUnit) {
        MeasurementUnit newMeasurementUnit = this.read(id);
        newMeasurementUnit.setDescription(measurementUnit.getDescription());
        newMeasurementUnit.setSymbol(measurementUnit.getSymbol());

        return this.create(newMeasurementUnit);
    }

    private MeasurementUnit read(Long id) {
        Optional<MeasurementUnit> optional = measurementUnitRepository.findById(id);
        MeasurementUnit measurementUnit = optional.orElseThrow(
                                    () -> new EntityNotFoundException("Measurement unit not found"));
        return measurementUnit;
    }

    @Transactional
    public void delete(Long id) {
        MeasurementUnit measurementUnit = this.read(id);
        measurementUnitRepository.delete(measurementUnit);
    }
}
