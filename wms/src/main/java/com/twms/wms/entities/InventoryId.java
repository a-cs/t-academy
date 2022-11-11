package com.twms.wms.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class InventoryId implements Serializable {
    @ManyToOne
    private Branch branch;
    private int r;
    private int c;

    public InventoryId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InventoryId inventoryId = (InventoryId) o;
        return Objects.equals(branch, inventoryId.branch) &&
                Objects.equals(r, inventoryId.r) &&
                Objects.equals(c, inventoryId.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branch, r, c);
    }
}
