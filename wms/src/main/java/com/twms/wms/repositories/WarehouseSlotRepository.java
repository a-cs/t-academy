package com.twms.wms.repositories;

import com.twms.wms.entities.WarehouseSlot;
import com.twms.wms.entities.WarehouseSlotId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseSlotRepository extends JpaRepository<WarehouseSlot, WarehouseSlotId> {
}
