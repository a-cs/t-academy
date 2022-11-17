package com.twms.wms.services;

import com.twms.wms.dtos.WarehouseSlotDTO;
import com.twms.wms.entities.Branch;
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

    public void deleteById(WarehouseSlotId wsId) {
        warehouseSlotRepository.deleteById(wsId);
    }

    public void deleteById(Long branchId, String aisleId, int bayId) {
        Branch branch = branchService.readBranchById(branchId);
        WarehouseSlotId id = new WarehouseSlotId(branch, bayId, aisleId);
        warehouseSlotRepository.deleteById(id);
    }
}
