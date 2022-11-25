package com.twms.wms.controllers;

import com.twms.wms.entities.SKU;
import com.twms.wms.services.SKUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @GetMapping("/search")
    public ResponseEntity<List<SKU>> searchSku(@RequestParam String term) {

        return ResponseEntity.status(HttpStatus.OK).body(service.searchTerm(term));
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
    public ResponseEntity<SKU> update(@PathVariable("skuId") Long skuId) {
        service.delete(skuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
