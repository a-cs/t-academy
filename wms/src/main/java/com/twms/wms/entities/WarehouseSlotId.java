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
public class WarehouseSlotId implements Serializable {
    @ManyToOne
    private Branch branch;
    private int bay;
    private String aisle;

    public WarehouseSlotId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WarehouseSlotId warehouseSlotId = (WarehouseSlotId) o;
        return Objects.equals(branch, warehouseSlotId.branch) &&
                Objects.equals(bay, warehouseSlotId.bay) &&
                Objects.equals(aisle, warehouseSlotId.aisle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branch, bay, aisle);
    }
}
