package com.twms.wms.services;

import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.repositories.MeasurementUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementUnitService {

    @Autowired
    MeasurementUnitRepository measurementUnitRepository;
    public Page<MeasurementUnit> read(Pageable pageable) {
        return  measurementUnitRepository.findAll(pageable);
    }

    public List<MeasurementUnit> read() {
        return measurementUnitRepository.findAll();
    }

    public List<MeasurementUnit> searchTerm(String searchTerm) {
        String terms = searchTerm.replace("-", " ");
        return this.measurementUnitRepository.findByDescriptionContainingIgnoreCaseOrSymbolContainingIgnoreCase(terms, terms);
    }

    @Transactional
    public MeasurementUnit create(MeasurementUnit measurementUnit) {
        return measurementUnitRepository.save(measurementUnit);
    }

    public MeasurementUnit update(Long id, MeasurementUnit measurementUnit) {
        MeasurementUnit newMeasurementUnit = this.read(id);
        newMeasurementUnit.setDescription(measurementUnit.getDescription());
        newMeasurementUnit.setSymbol(measurementUnit.getSymbol());

        return this.create(newMeasurementUnit);
    }

    public MeasurementUnit read(Long id) {
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
