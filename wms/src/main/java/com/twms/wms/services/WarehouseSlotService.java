package com.twms.wms.services;

import com.twms.wms.entities.Branch;
import com.twms.wms.entities.WarehouseSlot;
import com.twms.wms.entities.WarehouseSlotId;
import com.twms.wms.repositories.WarehouseSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WarehouseSlotService {

    @Autowired
    WarehouseSlotRepository warehouseSlotRepository;

    @Autowired
    BranchService branchService;

    public WarehouseSlot post(WarehouseSlot ws) {
        return warehouseSlotRepository.save(ws);
    }

    public List<WarehouseSlot> getAll() {
        return warehouseSlotRepository.findAll();
    }

    public WarehouseSlot getByPK(Long branchId, int bayId, String aisleId) {
        Branch branch = branchService.readBranchById(branchId);
        WarehouseSlotId pk = new WarehouseSlotId(branch, bayId, aisleId);
        Optional<WarehouseSlot> possibleItem = warehouseSlotRepository.findById(pk);
        WarehouseSlot warehouseSlot = possibleItem.orElseThrow(() -> new NoSuchElementException("Address does not exist."));
        return warehouseSlot;
    }

    private WarehouseSlot getByPK(WarehouseSlotId wsId) {
        Optional<WarehouseSlot> possibleItem = warehouseSlotRepository.findById(wsId);
        WarehouseSlot warehouseSlot = possibleItem.orElseThrow(() -> new NoSuchElementException("Address does not exist."));
        return warehouseSlot;
    }

    public WarehouseSlot putById(WarehouseSlotId wsId, WarehouseSlot newWarehouseSlot) {
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
