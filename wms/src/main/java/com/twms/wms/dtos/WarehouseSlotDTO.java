package com.twms.wms.dtos;

import com.twms.wms.entities.WarehouseSlot;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class WarehouseSlotDTO {

    private String aisle;
    private int bay;
    private String branchName;
    private String skuName;
    private Long skuId;
    private int quantity;
    private String clientName;
    private Long clientId;
    private Instant arrivalDate;


    public static WarehouseSlotDTO fromWarehouseSlot(WarehouseSlot warehouseSlot) {
        WarehouseSlotDTO warehouseSlotDTO = new WarehouseSlotDTO();

        warehouseSlotDTO.setBranchName(warehouseSlot.getWarehouseSlotId().getBranch().getName());
        warehouseSlotDTO.setAisle(warehouseSlot.getWarehouseSlotId().getAisle());
        warehouseSlotDTO.setBay(warehouseSlot.getWarehouseSlotId().getBay());
        warehouseSlotDTO.setSkuId(warehouseSlot.getSku().getId());
        warehouseSlotDTO.setSkuName(warehouseSlot.getSku().getName());
        warehouseSlotDTO.setQuantity(warehouseSlot.getQuantity());
        warehouseSlotDTO.setClientId(warehouseSlot.getClient().getId());
        warehouseSlotDTO.setClientName(warehouseSlot.getClient().getName());
        warehouseSlotDTO.setArrivalDate(warehouseSlot.getArrivalDate());

        return warehouseSlotDTO;
    }

    public static List<WarehouseSlotDTO> fromListWarehouseSlot(List<WarehouseSlot> warehouseSlots) {
        List<WarehouseSlotDTO> warehouseSlotDTOs = new ArrayList<>();
        for (WarehouseSlot ws : warehouseSlots) {
           warehouseSlotDTOs.add(WarehouseSlotDTO.fromWarehouseSlot(ws));
        }
        return warehouseSlotDTOs;
    }
}
