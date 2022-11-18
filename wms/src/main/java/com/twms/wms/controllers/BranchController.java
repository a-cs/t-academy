package com.twms.wms.controllers;

import com.twms.wms.entities.Branch;
import com.twms.wms.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchController {
    @Autowired
    BranchService branchService;

    @GetMapping
    public ResponseEntity<List<Branch>> readAllBranch(){
        return ResponseEntity.status(HttpStatus.OK).body(branchService.readAllBranchs());
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<Branch> readBranchById(@PathVariable("branchId") Long branchId){
        return ResponseEntity.status(HttpStatus.OK).body(branchService.readBranchById(branchId));
    }

    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody Branch branch){
        return ResponseEntity.status(HttpStatus.CREATED).body(branchService.createBranch(branch));
    }

    @PutMapping("/{branchId}")
    public  ResponseEntity<Branch> updateBranch(@PathVariable("branchId") Long branchId,
                                                @RequestBody Branch branch){
        return ResponseEntity.status(HttpStatus.OK).body(branchService.updateBranch(branchId,branch));
    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<Void> deleteBranch(@PathVariable("branchId") Long branchId){
        branchService.deleteBranch(branchId);
        return ResponseEntity.noContent().build();
    }
}
