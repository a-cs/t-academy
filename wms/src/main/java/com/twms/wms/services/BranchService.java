package com.twms.wms.services;

import com.twms.wms.entities.Branch;
import com.twms.wms.repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    @Autowired
    BranchRepository branchRepository;

    @Transactional
    public Branch createBranch(Branch branch){
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

    public Branch updateBranch(Long branchId, Branch branch){
        Branch oldBranch = this.readBranchById(branchId);
        oldBranch.setAddress(branch.getAddress());
        oldBranch.setName(branch.getName());
        oldBranch.setMax_rows(branch.getMax_rows());
        oldBranch.setMax_columns(branch.getMax_columns());

        return this.createBranch(oldBranch);
    }

    @Transactional
    public void deleteBranch(Long branchId){
        Branch toDelete = this.readBranchById(branchId);
        branchRepository.delete(toDelete);
    }


}
