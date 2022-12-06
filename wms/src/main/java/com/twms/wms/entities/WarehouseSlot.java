package com.twms.wms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WarehouseSlot {
    @EmbeddedId
    private WarehouseSlotId warehouseSlotId;

    private int quantity;

    @ManyToOne
    private SKU sku;

    @ManyToOne
    private Client client;

    private Instant arrivalDate;
}
