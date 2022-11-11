package com.twms.wms.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
public class Inventory {
    @EmbeddedId
    private InventoryId inventoryId;

    private int quantity;

    @ManyToOne
    private SKU sku;

    @ManyToOne
    private Client client;

    private Instant arrivalDate;
}
