package com.twms.wms.repositories;

import com.twms.wms.entities.Branch;
import com.twms.wms.entities.SKU;
import com.twms.wms.entities.WarehouseSlot;
import com.twms.wms.entities.WarehouseSlotId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseSlotRepository extends JpaRepository<WarehouseSlot, WarehouseSlotId> {
     List<WarehouseSlot> findAllByWarehouseSlotIdBranch(Branch branch);
     List<WarehouseSlot> findAllByWarehouseSlotIdBranchAndWarehouseSlotIdAisle(Branch branch, String aisle);
     Optional<WarehouseSlot> findByWarehouseSlotIdBranchAndWarehouseSlotIdAisleAndWarehouseSlotIdBay(Branch branch,
                                                                                                     String aisle,
                                                                                                     int bay);

     List<WarehouseSlot> findByClientId(Long clientId);

     List<WarehouseSlot> findByClientIdAndWarehouseSlotIdBranchIn(Long clientId, List<Branch> branches);

     List<WarehouseSlot> findByClientIdAndWarehouseSlotIdBranchInAndSkuIn(Long clientId, List<Branch> branches, List<SKU> skus);

     List<WarehouseSlot> findAllBySkuIn(List<SKU> skus);
}
