package com.twms.wms.services;


import com.twms.wms.dtos.BranchIdsProductIdsFilterDTO;
import com.twms.wms.dtos.ListIdsFilterDTO;
import com.twms.wms.dtos.WarehouseSlotDTO;
import com.twms.wms.entities.Branch;
import com.twms.wms.entities.SKU;
import com.twms.wms.entities.WarehouseSlot;
import com.twms.wms.entities.WarehouseSlotId;
import com.twms.wms.repositories.WarehouseSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class WarehouseSlotService {

    @Autowired
    WarehouseSlotRepository warehouseSlotRepository;

    @Autowired
    BranchService branchService;

    @Autowired
    SKUService skuService;

    public WarehouseSlotDTO post(WarehouseSlot ws) {
        WarehouseSlot persisted = warehouseSlotRepository.save(ws);
        return WarehouseSlotDTO.fromWarehouseSlot(persisted);
    }

    public List<WarehouseSlotDTO> getAll() {
        List<WarehouseSlot> warehouseSlots = warehouseSlotRepository.findAll();
        return WarehouseSlotDTO.fromListWarehouseSlot(warehouseSlots);
    }

    public List<WarehouseSlotDTO> getAllById(Long branchId) {
        Branch branch = branchService.readBranchById(branchId);
        List<WarehouseSlot> warehouseSlots = warehouseSlotRepository.findAllByWarehouseSlotIdBranch(branch);
        return WarehouseSlotDTO.fromListWarehouseSlot(warehouseSlots);
    }

    public List<WarehouseSlotDTO> getAllById(Long branchId, String aisleId) {
        Branch branch = branchService.readBranchById(branchId);
        List<WarehouseSlot> warehouseSlots = warehouseSlotRepository.findAllByWarehouseSlotIdBranchAndWarehouseSlotIdAisle(
                branch, aisleId);
        return WarehouseSlotDTO.fromListWarehouseSlot(warehouseSlots);
    }

    public WarehouseSlotDTO getById(Long branchId, String aisleId, int bay) {
        Branch branch = branchService.readBranchById(branchId);
        Optional<WarehouseSlot> expected = warehouseSlotRepository.findByWarehouseSlotIdBranchAndWarehouseSlotIdAisleAndWarehouseSlotIdBay(
                branch,
                aisleId,
                bay);
        WarehouseSlot ws = expected.orElseThrow(() -> new EntityNotFoundException("Warehouse slot does not exists."));
        return WarehouseSlotDTO.fromWarehouseSlot(ws);
    }

    public List<WarehouseSlotDTO> getByClientId(Long clientId) {
        List<WarehouseSlot> warehouseSlots = warehouseSlotRepository.findByClientId(clientId);
        return WarehouseSlotDTO.fromListWarehouseSlot(warehouseSlots);
    }

    public List<WarehouseSlotDTO> getByClientIdAndBranches(Long clientId, ListIdsFilterDTO branchIds) {
        List<Branch> branches = branchService.getBranchesByIds(branchIds.getIds());
        List<WarehouseSlot> warehouseSlots = warehouseSlotRepository.findByClientIdAndWarehouseSlotIdBranchIn(clientId, branches);
        return WarehouseSlotDTO.fromListWarehouseSlot(warehouseSlots);
    }

    public List<WarehouseSlotDTO> getByClientBranchAndProduct(Long clientId, BranchIdsProductIdsFilterDTO branchIdsProductIdsFilterDTO) {
        List<Long> branchIds = branchIdsProductIdsFilterDTO.getBranchIds();
        List<Long> skuIds = branchIdsProductIdsFilterDTO.getProductIds();
        List<Branch> branches = branchService.getBranchesByIds(branchIds);
        List<SKU> skus = skuService.findAllByIds(skuIds);
        List<WarehouseSlot> slots = warehouseSlotRepository.findByClientIdAndWarehouseSlotIdBranchInAndSkuIn(clientId, branches, skus);
        return WarehouseSlotDTO.fromListWarehouseSlot(slots);
    }

    public WarehouseSlotDTO putById(WarehouseSlot ws, Long branchId, String aisleId, int bayId) {
        Branch branch = branchService.readBranchById(branchId);
        Optional<WarehouseSlot> expectedToChange = warehouseSlotRepository.findByWarehouseSlotIdBranchAndWarehouseSlotIdAisleAndWarehouseSlotIdBay(
                branch,
                aisleId,
                bayId);
        WarehouseSlot toChange = expectedToChange.orElseThrow(() -> new EntityNotFoundException("Warehouse slot does not exists."));
        toChange.setSku(ws.getSku());
        toChange.setClient(ws.getClient());
        toChange.setQuantity(ws.getQuantity());
        return post(toChange);
    }

    public void deleteById(Long branchId, String aisleId, int bayId) {
        Branch branch = branchService.readBranchById(branchId);
        WarehouseSlotId id = new WarehouseSlotId(branch, bayId, aisleId);
        warehouseSlotRepository.deleteById(id);
    }
}
