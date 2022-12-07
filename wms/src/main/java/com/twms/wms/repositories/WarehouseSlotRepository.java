package com.twms.wms.repositories;

import com.twms.wms.entities.Branch;
import com.twms.wms.entities.SKU;
import com.twms.wms.entities.WarehouseSlot;
import com.twms.wms.entities.WarehouseSlotId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseSlotRepository extends JpaRepository<WarehouseSlot, WarehouseSlotId> {
     List<WarehouseSlot> findAllByWarehouseSlotIdBranch(Branch branch);
     List<WarehouseSlot> findAllByWarehouseSlotIdBranchAndWarehouseSlotIdAisle(Branch branch, String aisle);
     Optional<WarehouseSlot> findByWarehouseSlotIdBranchAndWarehouseSlotIdAisleAndWarehouseSlotIdBay(Branch branch,
                                                                                                     String aisle,
                                                                                                     int bay);

     Page<WarehouseSlot> findByClientId(Long clientId, Pageable pageable);


     Page<WarehouseSlot> findByClientIdAndWarehouseSlotIdBranchIn(Long clientId, List<Branch> branches, Pageable pageable);

     Page<WarehouseSlot> findByClientIdAndWarehouseSlotIdBranchInAndSkuIn(Long clientId, List<Branch> branches, List<SKU> skus, Pageable pageable);

     List<WarehouseSlot> findAllBySkuIn(List<SKU> skus);
    Page<WarehouseSlot> findByClientIdAndWarehouseSlotIdBranchInAndSkuNameContainingIgnoreCase(Long clientId, List<Branch> branches, String searchTerm, Pageable pageable);

     WarehouseSlot findFirstBySkuIsNullAndWarehouseSlotIdBranchId(Long branchId);

     WarehouseSlot findFirstByClientIdAndWarehouseSlotIdBranchIdAndSkuIdOrderByArrivalDateAsc(Long clientId,
                                                                                               Long branchId,
                                                                                               Long skuId);

    Page<WarehouseSlot> findAllByWarehouseSlotIdBranchIdAndSkuNameContainingIgnoreCaseOrWarehouseSlotIdBranchIdAndClientNameContainingIgnoreCaseOrderByArrivalDateAsc(
            Long branchId,
            String term,
            Long branchId2,
            String term2,
            Pageable pageable
    );

    List<WarehouseSlot> findAllByClientIdAndWarehouseSlotIdBranchIdAndSkuIdOrderByArrivalDateAsc(Long clientId,
                                                                                                 Long branchId,
                                                                                                 Long skuId);
}
