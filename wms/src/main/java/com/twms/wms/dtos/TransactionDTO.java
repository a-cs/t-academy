package com.twms.wms.dtos;

import com.twms.wms.entities.Client;
import com.twms.wms.entities.SKU;
import com.twms.wms.entities.User;
import com.twms.wms.entities.WarehouseSlot;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

public class TransactionDTO {

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
