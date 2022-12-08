package com.twms.wms.dtos;

import com.twms.wms.entities.*;
import com.twms.wms.enums.TransactionType;
import com.twms.wms.services.ClientService;
import com.twms.wms.services.SKUService;
import com.twms.wms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

public class TransactionDTO {

    private long id;

    private Timestamp date;

    private int quantity;

    private String warehouseSlot;

    private String client;

    private String sku;

    private String user;

    private String branch;

    private TransactionType type;



    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.date = transaction.getDate();
        this.quantity = transaction.getQuantity();
        this.warehouseSlot  = transaction.getWarehouseSlot().getWarehouseSlotId().getAisle()+transaction.getWarehouseSlot().getWarehouseSlotId().getBay();
        this.client = transaction.getClient().getName();
        this.sku = transaction.getSku().getName();
        this.user = transaction.getUser().getUsername();
        this.branch =  transaction.getWarehouseSlot().getWarehouseSlotId().getBranch().getName();
        this.type = transaction.getType();
    }

    public long getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getWarehouseSlot() {
        return warehouseSlot;
    }

    public String getClient() {
        return client;
    }

    public String getSku() {
        return sku;
    }

    public String getUser() {
        return user;
    }

    public String getBranch() {
        return branch;
    }

    public TransactionType getType() {
        return type;
    }
}
