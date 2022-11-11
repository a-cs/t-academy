package com.twms.wms.controllers;

import com.twms.wms.entities.Branch;
import com.twms.wms.entities.WarehouseSlot;
import com.twms.wms.entities.WarehouseSlotId;
import com.twms.wms.services.WarehouseSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/warehouseSlot")
public class WarehouseSlotController {

    @Autowired
    WarehouseSlotService warehouseSlotService;

    @PostMapping
    public ResponseEntity<WarehouseSlot> post(@Valid @RequestBody WarehouseSlot ws) {
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouseSlotService.post(ws));
    }

    @GetMapping
    public ResponseEntity<List<WarehouseSlot>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getAll());
    }

    @GetMapping("/{branchId}/{aisleId}/{bayId}")
    public ResponseEntity<WarehouseSlot> getByPK(@RequestParam("branchId") Long branchId,
                                                 @RequestParam("aisleId") String aisleId,
                                                 @RequestParam("bayId") int bayId) {
        System.out.println(branchId);
        System.out.println(aisleId);
        System.out.println(bayId);
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getByPK(branchId, bayId, aisleId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<WarehouseSlot>> getByClientId(@PathVariable Long clientId) {
        return null;
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<WarehouseSlot>> getByBranchId(@PathVariable Long branchId) {
        return null;
    }

    @GetMapping("/sku/{SKUId}")
    public ResponseEntity<List<WarehouseSlot>> getBySKUId(@PathVariable Long SKUId) {
        return null;
    }

    @PutMapping
    public ResponseEntity<WarehouseSlot> putById(@RequestBody WarehouseSlotId wsId, @RequestBody WarehouseSlot ws) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(warehouseSlotService.putById(wsId, ws));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody WarehouseSlotId wsId) {
        warehouseSlotService.deleteById(wsId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
