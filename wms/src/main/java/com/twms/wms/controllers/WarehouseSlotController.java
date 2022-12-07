package com.twms.wms.controllers;

import com.twms.wms.dtos.ListIdsFilterDTO;
import com.twms.wms.dtos.WarehouseSlotDTO;
import com.twms.wms.dtos.BranchIdsProductIdsFilterDTO;
import com.twms.wms.entities.WarehouseSlot;
import com.twms.wms.services.WarehouseSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<WarehouseSlotDTO>> getByTestId(@PathVariable Long branchId) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getAllById(branchId));
    }

//    @GetMapping("/branch/{branchId}/aisle/{aisleId}")
//    public ResponseEntity<List<WarehouseSlotDTO>> getByTestId(@PathVariable Long branchId,
//                                                           @PathVariable String aisleId) {
//        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getAllById(
//                branchId,
//                aisleId));
//    }
//
//    @GetMapping("/branch/{branchId}/aisle/{aisleId}/bay/{bayId}")
//    public ResponseEntity<WarehouseSlotDTO> getByTestId(@PathVariable("branchId") Long branchId,
//                                                           @PathVariable("aisleId") String aisleId,
//                                                           @PathVariable("bayId") int bay) {
//        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getById(branchId, aisleId, bay));
//    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<WarehouseSlotDTO>> getByClientId(@PathVariable Long clientId,
                                                                Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getByClientId(clientId, pageable));
    }

//    @GetMapping("/client/{clientId}/filterByBranches")
//    public ResponseEntity<List<WarehouseSlotDTO>> getByClientAndBranches(@PathVariable Long clientId,
//                                                                         @RequestBody ListIdsFilterDTO branchIds) {
//        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getByClientIdAndBranches(clientId, branchIds));
//    }

    @GetMapping("/client/{clientId}/filterByBranches")
    public ResponseEntity<Page<WarehouseSlotDTO>> getByClientAndBranches(@PathVariable Long clientId,
                                                                         @RequestBody ListIdsFilterDTO branchIds,
                                                                         Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getByClientIdAndBranches(clientId, branchIds, pageable));
    }
    @GetMapping("/client/filtered")
    public ResponseEntity<Page<WarehouseSlotDTO>> getByClientAndBranchesandProducts(@RequestParam(defaultValue = "") String term,
                                                                                    @RequestParam Long branch,
                                                                         Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getByClientIdAndBranchesandProducts(term, branch, pageable));
    }


    @PostMapping("/client/{clientId}/filterByBranches/searchProduct")
    public ResponseEntity<Page<WarehouseSlotDTO>> getByClientAndBranchesSearchByProductName(@PathVariable Long clientId,
                                                                                            @RequestBody ListIdsFilterDTO branchIds,
                                                                                            @RequestParam(defaultValue = "") String term,
                                                                                            Pageable pageable
                                                                       ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                warehouseSlotService.getByClientBranchFilteredByProductName(clientId, branchIds, term, pageable));
    }

    @PostMapping("/client/{clientId}/filterByBranchesAndProducts")
    public ResponseEntity<Page<WarehouseSlotDTO>> getByClientBranchAndProduct(@PathVariable Long clientId,
                                                                       @RequestBody BranchIdsProductIdsFilterDTO branchIdsAndProductIds,
                                                                              Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseSlotService.getByClientBranchAndProduct(clientId, branchIdsAndProductIds, pageable));
    }


    @PutMapping("/branch/{branchId}/aisle/{aisleId}/bay/{bayId}")
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
