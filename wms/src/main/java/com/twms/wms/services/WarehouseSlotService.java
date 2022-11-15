package com.twms.wms.services;

import com.twms.wms.dtos.WarehouseSlotDTO;
import com.twms.wms.entities.Branch;
import com.twms.wms.entities.WarehouseSlot;
import com.twms.wms.entities.WarehouseSlotId;
import com.twms.wms.repositories.WarehouseSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WarehouseSlotService {

    @Autowired
    WarehouseSlotRepository warehouseSlotRepository;

    @Autowired
    BranchService branchService;

    public WarehouseSlotDTO post(WarehouseSlot ws) {
        WarehouseSlot persisted = warehouseSlotRepository.save(ws);
        return new WarehouseSlotDTO().fromWarehouseSlot(persisted);
    }

    public List<WarehouseSlotDTO> getAll() {
        List<WarehouseSlot> warehouseSlots = warehouseSlotRepository.findAll();
        List<WarehouseSlotDTO> warehouseSlotDTOs = new ArrayList<>(warehouseSlots.size());

        for (WarehouseSlot ws : warehouseSlots) {
            warehouseSlotDTOs.add(new WarehouseSlotDTO().fromWarehouseSlot(ws));
        }

        return warehouseSlotDTOs;
    }

    public WarehouseSlotDTO getByPK(Long branchId, int bayId, String aisleId) {
        Branch branch = branchService.readBranchById(branchId);
        WarehouseSlotId pk = new WarehouseSlotId(branch, bayId, aisleId);
        Optional<WarehouseSlot> possibleItem = warehouseSlotRepository.findById(pk);
        WarehouseSlot warehouseSlot = possibleItem.orElseThrow(() -> new NoSuchElementException("Address does not exist."));
        return new WarehouseSlotDTO().fromWarehouseSlot(warehouseSlot);
    }

    private WarehouseSlot getByPK(WarehouseSlotId wsId) {
        Optional<WarehouseSlot> possibleItem = warehouseSlotRepository.findById(wsId);
        WarehouseSlot warehouseSlot = possibleItem.orElseThrow(() -> new NoSuchElementException("Address does not exist."));
        return warehouseSlot;
    }

    public WarehouseSlotDTO putById(WarehouseSlotId wsId, WarehouseSlot newWarehouseSlot) {
        WarehouseSlot slotToChange = getByPK(wsId);
        slotToChange.setSku(newWarehouseSlot.getSku());
        slotToChange.setClient(newWarehouseSlot.getClient());
        slotToChange.setQuantity(newWarehouseSlot.getQuantity());
        return post(slotToChange);
    }

    public void deleteById(WarehouseSlotId wsId) {
        WarehouseSlot slotToDelete = getByPK(wsId);
        warehouseSlotRepository.delete(slotToDelete);
    }
}
