package com.twms.wms.services;

import com.twms.wms.dtos.ListIdsFilterDTO;
import com.twms.wms.entities.Branch;
import com.twms.wms.entities.WarehouseSlot;
import com.twms.wms.entities.WarehouseSlotId;
import com.twms.wms.repositories.BranchRepository;
import com.twms.wms.repositories.SKURepository;
import com.twms.wms.repositories.WarehouseSlotRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    @Autowired
    BranchRepository branchRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    WarehouseSlotRepository warehouseSlotRepository;

    @Autowired
    SKURepository skuRepository;

    @SneakyThrows
    @Transactional
    public Branch createBranch(Branch branch){
        List<Branch> branchesWithSameName = branchRepository.findByName(branch.getName());
        if(branchesWithSameName.size()>0) throw new SQLIntegrityConstraintViolationException("Name should be unique!!");

        if(branch.getAddress().getId()==null) branch.setAddress(addressService.post(branch.getAddress()));
        else if(addressService.getById(branch.getAddress().getId())==null) throw new SQLIntegrityConstraintViolationException("Address not Found!!");


        Branch savedBranch =  branchRepository.save(branch);
        for (int i = 1; i <= branch.getMax_rows(); i++) {

            for (int j = 0; j < branch.getMax_columns(); j++) {
                WarehouseSlotId warehouseSlotId = new WarehouseSlotId();
                warehouseSlotId.setBranch(savedBranch);
                warehouseSlotId.setBay(i);

                warehouseSlotId.setAisle(toAlphabetic(j));

                WarehouseSlot warehouseSlot = new WarehouseSlot();
                warehouseSlot.setWarehouseSlotId(warehouseSlotId);
                warehouseSlot.setClient(null);
                warehouseSlot.setSku(null);
                warehouseSlot.setQuantity(0);
                warehouseSlot.setArrivalDate(null);
                warehouseSlotRepository.save(warehouseSlot);
            }
        }

        return savedBranch;
    }

    private String toAlphabetic(int i) {
//        if( i<0 ) {
//            return "-"+toAlphabetic(-i-1);
//        }

        int quot = i/26;
        int rem = i%26;
        char letter = (char)((int)'A' + rem);
        if( quot == 0 ) {
            return ""+letter;
        } else {
            return toAlphabetic(quot-1) + letter;
        }
    }

    public List<Branch> readAllBranchs(){
        return branchRepository.findAll();
    }

    public Branch readBranchById(Long branchId){
        Optional<Branch> opt = branchRepository.findById(branchId);
        Branch branch = opt.orElseThrow(()->new EntityNotFoundException("Branch Not Created or Removed!!"));
        return branch;
    }

    // Refatorar para usar apenas o que recebe lista de ids
    public List<Branch> getBranchesByIds(ListIdsFilterDTO ids) {
        List<Long> idsAsLong = ids.getIds();
        return branchRepository.findByIdIn(idsAsLong);
    }

    public List<Branch> getBranchesByIds(List<Long> ids) {
        return branchRepository.findByIdIn(ids);
    }

    public List<Branch> searchTerm(String searchTerm) {
        String terms = searchTerm.replace("-", " ");
        return this.branchRepository.findByNameContainingIgnoreCase(terms);
    }

    public Branch updateBranch(Long branchId, Branch branch){
        Branch oldBranch = this.readBranchById(branchId);
        //oldBranch.setAddress(branch.getAddress());
        oldBranch.setName(branch.getName());
        oldBranch.setMax_rows(branch.getMax_rows());
        oldBranch.setMax_columns(branch.getMax_columns());

        return branchRepository.save(oldBranch);
    }

    @Transactional
    public void deleteBranch(Long branchId) throws SQLIntegrityConstraintViolationException {
        List<Long> skusId = new ArrayList<>();

        skuRepository.findAll().forEach(
                (sku) -> {
                    skusId.add(sku.getId());
                }
        );

        if (warehouseSlotRepository.existsBySkuIdIn(skusId)) {
            throw new SQLIntegrityConstraintViolationException("Cannot delete this branch because there are products in it.");
        }

        Branch toDelete = this.readBranchById(branchId);
        branchRepository.delete(toDelete);
    }


}
