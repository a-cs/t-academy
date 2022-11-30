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
        List<Branch> branchesToFilter;
        List<SKU> skusToFilter;

        List<Long> branchIds = branchIdsProductIdsFilterDTO.getBranchIds();
        List<Long> skuIds = branchIdsProductIdsFilterDTO.getProductIds();

        if (branchIds.size() > 0) {
            branchesToFilter = branchService.getBranchesByIds(branchIds);
        } else {
            branchesToFilter = branchService.readAllBranchs();
        }

        if (skuIds.size() > 0) {
            skusToFilter = skuService.findAllByIds(skuIds);
        } else {
            skusToFilter = skuService.read();
        }

        List<WarehouseSlot> slots = warehouseSlotRepository.findByClientIdAndWarehouseSlotIdBranchInAndSkuIn(
                clientId, branchesToFilter, skusToFilter);
        return WarehouseSlotDTO.fromListWarehouseSlot(slots);
    }

    public List<WarehouseSlotDTO> getByClientBranchFilteredByProductName(Long clientId, ListIdsFilterDTO branchIdsDTO, String searchTerm) {
        List<Branch> branchesToFilter;
        List<Long> branchIds = branchIdsDTO.getIds();

        if (branchIds.size() > 0) {
            branchesToFilter = branchService.getBranchesByIds(branchIds);
        } else {
            branchesToFilter = branchService.readAllBranchs();
        }

        List<WarehouseSlot> slots = warehouseSlotRepository.findByClientIdAndWarehouseSlotIdBranchInAndSkuNameContainingIgnoreCase(
                clientId,
                branchesToFilter,
                searchTerm
        );
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
