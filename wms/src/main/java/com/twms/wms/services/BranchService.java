package com.twms.wms.services;

import com.twms.wms.dtos.ListIdsFilterDTO;
import com.twms.wms.entities.Branch;
import com.twms.wms.repositories.BranchRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    @Autowired
    BranchRepository branchRepository;

    @Autowired
    AddressService addressService;

    @SneakyThrows
    @Transactional
    public Branch createBranch(Branch branch){
        List<Branch> branchesWithSameName = branchRepository.findByName(branch.getName());
        if(branchesWithSameName.size()>0) throw new SQLIntegrityConstraintViolationException("Name should be unique!!");
        if(addressService.getById(branch.getAddress().getId())==null) throw new SQLIntegrityConstraintViolationException("Address does'nt exist!!");
        return branchRepository.save(branch);
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
    public void deleteBranch(Long branchId){
        Branch toDelete = this.readBranchById(branchId);
        branchRepository.delete(toDelete);
    }


}
