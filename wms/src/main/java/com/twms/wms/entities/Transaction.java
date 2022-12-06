package com.twms.wms.entities;

import com.twms.wms.enums.TransactionType;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Timestamp Date;

    private int quantity;

    private TransactionType type;

    @ManyToOne
    private WarehouseSlot warehouseSlot;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "sku_id")
    private SKU sku;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Transaction(){}
    public Transaction(Transaction transaction, WarehouseSlot warehouseSlot){
        this.setQuantity(transaction.getQuantity());
        this.setClient(transaction.getClient());
        this.setSku(transaction.getSku());
        this.setUser(transaction.getUser());
        this.setDate(Timestamp.from(Instant.now()));
        this.setWarehouseSlot(warehouseSlot);
    }
}
