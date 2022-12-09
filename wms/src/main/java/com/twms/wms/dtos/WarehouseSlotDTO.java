package com.twms.wms.dtos;

import com.twms.wms.entities.WarehouseSlot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WarehouseSlotDTO {

    private String aisle;
    private int bay;
    private String branchName;
    private String skuName;
    private Long skuId;
    private int quantity;
    private String symbol;
    private String clientName;
    private Long clientId;
    private Instant arrivalDate;

    public WarehouseSlotDTO(WarehouseSlot warehouseSlot) {
        this.aisle = warehouseSlot.getWarehouseSlotId().getAisle();
        this.bay = warehouseSlot.getWarehouseSlotId().getBay();
        this.branchName = warehouseSlot.getWarehouseSlotId().getBranch().getName();
        this.skuName = warehouseSlot.getSku().getName();
        this.skuId = warehouseSlot.getSku().getId();
        this.quantity = warehouseSlot.getQuantity();
        this.symbol = warehouseSlot.getSku().getMeasurementUnit().getSymbol();
        this.clientName = warehouseSlot.getClient().getName();
        this.clientId = warehouseSlot.getClient().getId();
        this.arrivalDate = warehouseSlot.getArrivalDate();
    }

    public static WarehouseSlotDTO fromWarehouseSlot(WarehouseSlot warehouseSlot) {
        WarehouseSlotDTO warehouseSlotDTO = new WarehouseSlotDTO();

        warehouseSlotDTO.setBranchName(warehouseSlot.getWarehouseSlotId().getBranch().getName());
        warehouseSlotDTO.setAisle(warehouseSlot.getWarehouseSlotId().getAisle());
        warehouseSlotDTO.setBay(warehouseSlot.getWarehouseSlotId().getBay());
        if(warehouseSlot.getSku() != null) {
            warehouseSlotDTO.setSkuId(warehouseSlot.getSku().getId());
            warehouseSlotDTO.setSymbol(warehouseSlot.getSku().getMeasurementUnit().getSymbol());
            warehouseSlotDTO.setSkuName(warehouseSlot.getSku().getName());
        }
        warehouseSlotDTO.setQuantity(warehouseSlot.getQuantity());
        if(warehouseSlot.getClient() != null) {
            warehouseSlotDTO.setClientId(warehouseSlot.getClient().getId());
            warehouseSlotDTO.setClientName(warehouseSlot.getClient().getName());
        }
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
