package com.twms.wms.controllers;

import com.twms.wms.entities.SKU;
import com.twms.wms.services.SKUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/sku")
public class SKUController {

    @Autowired
    SKUService service;

    @GetMapping
    public ResponseEntity<List<SKU>> read() {
        return ResponseEntity.status(HttpStatus.OK).body(service.read());
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<SKU>> readPaginated(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.readPaginated(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SKU>> searchSku(@RequestParam String term, Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(service.searchTerm(term, pageable));
    }

    @PostMapping
    public ResponseEntity<SKU> create(@Valid @RequestBody SKU sku) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(sku));
    }

    @PutMapping("/{skuId}")
    public ResponseEntity<SKU> update(@PathVariable("skuId") Long skuId, @Valid @RequestBody SKU sku) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(skuId, sku));
    }

    @DeleteMapping("/{skuId}")
    public ResponseEntity<SKU> update(@PathVariable("skuId") Long skuId) throws SQLIntegrityConstraintViolationException {
        service.delete(skuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
