package com.twms.wms.dtos;

import com.twms.wms.entities.WarehouseSlot;
import lombok.Data;

import java.time.Instant;

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


    public WarehouseSlotDTO fromWarehouseSlot(WarehouseSlot warehouseSlot) {
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
}
