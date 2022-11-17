package com.twms.wms.controllers;

import com.twms.wms.entities.MeasurementUnit;
import com.twms.wms.services.MeasurementUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/measurement-unit")
public class MeasurementUnitController {

    @Autowired
    MeasurementUnitService measurementUnitService;

    @GetMapping
    public ResponseEntity<Page<MeasurementUnit>> read(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(measurementUnitService.read(pageable));
    }

    @PostMapping
    public ResponseEntity<MeasurementUnit> create(@Valid @RequestBody MeasurementUnit measurementUnit) {
        return ResponseEntity.status(HttpStatus.CREATED).body(measurementUnitService.create(measurementUnit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeasurementUnit> update(@PathVariable("id") Long id, @Valid @RequestBody MeasurementUnit measurementUnit) {
        return ResponseEntity.status(HttpStatus.OK).body(measurementUnitService.update(id, measurementUnit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        measurementUnitService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
