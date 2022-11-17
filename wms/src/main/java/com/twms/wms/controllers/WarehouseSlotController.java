package com.twms.wms.controllers;

import com.twms.wms.dtos.WarehouseSlotDTO;
import com.twms.wms.entities.WarehouseSlot;
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
    public ResponseEntity<WarehouseSlotDTO> post(@Valid @RequestBody WarehouseSlot ws) {
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouseSlotService.post(ws));
    }

    @GetMapping
    public ResponseEntity<List<WarehouseSlotDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getAll());
    }

//    @GetMapping("/{branchId}/{aisleId}/{bayId}")
//    public ResponseEntity<WarehouseSlotDTO> getByPK(@RequestParam("branchId") Long branchId,
//                                                 @RequestParam("aisleId") String aisleId,
//                                                 @RequestParam("bayId") int bayId) {
//        System.out.println(branchId);
//        System.out.println(aisleId);
//        System.out.println(bayId);
//        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getByPK(branchId, bayId, aisleId));
//    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<WarehouseSlotDTO>> getByTestId(@PathVariable Long branchId) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getAllById(branchId));
    }

    @GetMapping("/branch/{branchId}/aisle/{aisleId}")
    public ResponseEntity<List<WarehouseSlotDTO>> getByTestId(@PathVariable Long branchId,
                                                           @PathVariable String aisleId) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getAllById(
                branchId,
                aisleId));
    }

    @GetMapping("/branch/{branchId}/aisle/{aisleId}/bay/{bayId}")
    public ResponseEntity<WarehouseSlotDTO> getByTestId(@PathVariable("branchId") Long branchId,
                                                           @PathVariable("aisleId") String aisleId,
                                                           @PathVariable("bayId") int bay) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getById(branchId, aisleId, bay));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<WarehouseSlotDTO>> getByClientId(@PathVariable Long clientId) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getByClientId(clientId));
    }

    @PutMapping
    public ResponseEntity<WarehouseSlotDTO> putById(@RequestBody WarehouseSlot ws,
                                                    @PathVariable("branchId") Long branchId,
                                                    @PathVariable("aisleId") String aisledId,
                                                    @PathVariable("bayId") int bayId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(warehouseSlotService.putById(
                ws,
                branchId,
                aisledId,
                bayId));
    }

    @DeleteMapping("/branch/{branchId}/aisle/{aisleId}/bay/{bayId}")
    public ResponseEntity<Void> deleteById(@PathVariable("branchId") Long branchId,
                                           @PathVariable("aisleId") String aisleId,
                                           @PathVariable("bayId") int bayId) {
        warehouseSlotService.deleteById(branchId, aisleId, bayId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
