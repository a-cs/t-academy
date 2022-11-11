package com.twms.wms.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class WarehouseId implements Serializable {
    @ManyToOne
    private Branch branch;
    private String bay;
    private int aisle;

    public WarehouseId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WarehouseId warehouseId = (WarehouseId) o;
        return Objects.equals(branch, warehouseId.branch) &&
                Objects.equals(bay, warehouseId.bay) &&
                Objects.equals(aisle, warehouseId.aisle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branch, bay, aisle);
    }
}
