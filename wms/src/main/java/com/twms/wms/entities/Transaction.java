package com.twms.wms.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Timestamp Date;

    private int quantity;

    @ManyToOne
    private WarehouseSlot warehouseSlot;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "sku_id")
    private SKU sku;

    @ManyToOne
    private User user;
}
