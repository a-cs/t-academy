package com.twms.wms.services;


import com.twms.wms.dtos.BranchIdsProductIdsFilterDTO;
import com.twms.wms.dtos.ListIdsFilterDTO;
import com.twms.wms.dtos.WarehouseSlotDTO;
import com.twms.wms.entities.*;
import com.twms.wms.repositories.WarehouseSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class WarehouseSlotService {

    @Autowired
    WarehouseSlotRepository warehouseSlotRepository;

    @Autowired
    BranchService branchService;
    @Autowired
    ClientService clientService;

    @Autowired
    SKUService skuService;

    @Autowired
    UserService userService;

    public WarehouseSlotDTO post(WarehouseSlot ws) {
        WarehouseSlot persisted = warehouseSlotRepository.save(ws);
        return WarehouseSlotDTO.fromWarehouseSlot(persisted);
    }

    public List<WarehouseSlotDTO> getAll() {
        List<WarehouseSlot> warehouseSlots = warehouseSlotRepository.findAll();
        return WarehouseSlotDTO.fromListWarehouseSlot(warehouseSlots);
    }

    public List<WarehouseSlotDTO> getAllById(Long branchId) {
        Branch branch = branchService.readBranchById(branchId);
        List<WarehouseSlot> warehouseSlots = warehouseSlotRepository.findAllByWarehouseSlotIdBranch(branch);
        return WarehouseSlotDTO.fromListWarehouseSlot(warehouseSlots);
    }

//    public List<WarehouseSlotDTO> getAllById(Long branchId, String aisleId) {
//        Branch branch = branchService.readBranchById(branchId);
//        List<WarehouseSlot> warehouseSlots = warehouseSlotRepository.findAllByWarehouseSlotIdBranchAndWarehouseSlotIdAisle(
//                branch, aisleId);
//        return WarehouseSlotDTO.fromListWarehouseSlot(warehouseSlots);
//    }

    public WarehouseSlotDTO getById(Long branchId, String aisleId, int bay) {
        Branch branch = branchService.readBranchById(branchId);
        Optional<WarehouseSlot> expected = warehouseSlotRepository.findByWarehouseSlotIdBranchAndWarehouseSlotIdAisleAndWarehouseSlotIdBay(
                branch,
                aisleId,
                bay);
        WarehouseSlot ws = expected.orElseThrow(() -> new EntityNotFoundException("Warehouse slot does not exists."));
        return WarehouseSlotDTO.fromWarehouseSlot(ws);
    }

    public Page<WarehouseSlotDTO> getByClientId(Long clientId, Pageable pageable) {
        Page<WarehouseSlot> slots = warehouseSlotRepository.findByClientId(clientId, pageable);
        return slots.map(WarehouseSlotDTO::new);
    }

    public Page<WarehouseSlotDTO> getByUserId(Long userId, Pageable pageable) {
        User user = userService.getUserById(userId);
        Client client = clientService.getClientByEmail(user.getEmail());
        return this.getByClientId(client.getId(), pageable);
    }

    public Page<WarehouseSlotDTO> getByClientIdAndBranches(Long clientId, ListIdsFilterDTO branchIds, Pageable pageable) {
        List<Branch> branches = branchService.getBranchesByIds(branchIds.getIds());
        Page<WarehouseSlot> warehouseSlots = warehouseSlotRepository.findByClientIdAndWarehouseSlotIdBranchIn(clientId, branches, pageable);
        return warehouseSlots.map(WarehouseSlotDTO::new);
    }

    public Page<WarehouseSlotDTO> getByClientBranchAndProduct(Long clientId, BranchIdsProductIdsFilterDTO branchIdsProductIdsFilterDTO, Pageable pageable) {
        List<Branch> branchesToFilter;
        List<SKU> skusToFilter;

        List<Long> branchIds = branchIdsProductIdsFilterDTO.getBranchIds();
        List<Long> skuIds = branchIdsProductIdsFilterDTO.getProductIds();

        if (branchIds.size() > 0) {
            branchesToFilter = branchService.getBranchesByIds(branchIds);
        } else {
            branchesToFilter = branchService.readAllBranchs();
        }

        if (skuIds.size() > 0) {
            skusToFilter = skuService.findAllByIds(skuIds);
        } else {
            skusToFilter = skuService.read();
        }

        Page<WarehouseSlot> slots = warehouseSlotRepository.findByClientIdAndWarehouseSlotIdBranchInAndSkuIn(
                clientId, branchesToFilter, skusToFilter, pageable);
        return slots.map(WarehouseSlotDTO::new);
    }

    public Page<WarehouseSlotDTO> getByClientBranchFilteredByProductName(Long clientId, ListIdsFilterDTO branchIdsDTO, String searchTerm, Pageable pageable) {
        List<Branch> branchesToFilter;
        List<Long> branchIds = branchIdsDTO.getIds();

        if (branchIds.size() > 0) {
            branchesToFilter = branchService.getBranchesByIds(branchIds);
        } else {
            branchesToFilter = branchService.readAllBranchs();
        }

        Page<WarehouseSlot> slots = warehouseSlotRepository.findByClientIdAndWarehouseSlotIdBranchInAndSkuNameContainingIgnoreCase(
                clientId,
                branchesToFilter,
                searchTerm,
                pageable
        );
        return slots.map(WarehouseSlotDTO::new);
    }

    public Page<WarehouseSlotDTO> getByUserBranchFilteredByProductName(Long userId, ListIdsFilterDTO branchIdsDTO, String searchTerm, Pageable pageable) {
        User user = userService.getUserById(userId);
        Client client = clientService.getClientByEmail(user.getEmail());
        return this.getByClientBranchFilteredByProductName(client.getId(), branchIdsDTO, searchTerm, pageable);
    }

    public WarehouseSlotDTO putById(WarehouseSlot ws, Long branchId, String aisleId, int bayId) {
        Branch branch = branchService.readBranchById(branchId);
        Optional<WarehouseSlot> expectedToChange = warehouseSlotRepository.findByWarehouseSlotIdBranchAndWarehouseSlotIdAisleAndWarehouseSlotIdBay(
                branch,
                aisleId,
                bayId);
        WarehouseSlot toChange = expectedToChange.orElseThrow(() -> new EntityNotFoundException("Warehouse slot does not exists."));
        toChange.setSku(ws.getSku());
        toChange.setClient(ws.getClient());
        toChange.setQuantity(ws.getQuantity());
        return post(toChange);
    }

    public void deleteById(Long branchId, String aisleId, int bayId) {
        Branch branch = branchService.readBranchById(branchId);
        WarehouseSlotId id = new WarehouseSlotId(branch, bayId, aisleId);
        warehouseSlotRepository.deleteById(id);
    }

    public WarehouseSlot getFirstEmptySlot(Long branchId){
        Branch branch = branchService.readBranchById(branchId);
        WarehouseSlot slot = warehouseSlotRepository.findFirstBySkuIsNullAndWarehouseSlotIdBranchId(branchId);
        if(slot == null)
                throw new EntityNotFoundException("The branch "+ branch.getName() + " is full! There is no Warehouse slot currently available!");
        return slot;
    }

    public List<WarehouseSlot> getOldestSlotByClientAndSkuAndBranch(Long clientId,
                                                              Long branchId,
                                                              Long skuId){
        Branch branch = branchService.readBranchById(branchId);
        Client client = clientService.readClientById(clientId);
        List<WarehouseSlot> slotList = warehouseSlotRepository.findAllByClientIdAndWarehouseSlotIdBranchIdAndSkuIdOrderByArrivalDateAsc(clientId, branchId, skuId);
        if(slotList.size() == 0)
            throw new EntityNotFoundException("The branch "+ branch.getName() + " and/or client "+ client.getName() +" do not have this item!");
        return slotList;
    }

    public Page<WarehouseSlotDTO> getByClientIdAndBranchesandProducts(String sku, String client, Long branch, Pageable pageable) {
        return warehouseSlotRepository.findAllByWarehouseSlotIdBranchIdAndSkuNameContainingIgnoreCaseAndWarehouseSlotIdBranchIdAndClientNameContainingIgnoreCaseOrderByArrivalDateAsc(branch,sku,branch,client,pageable).map(WarehouseSlotDTO::new);
        //OrWarehouseSlotIdBranchIdAndSkuNameContainingIgnoreCase
    }
}
