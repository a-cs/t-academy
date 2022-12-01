package com.twms.wms.repositories;

import com.twms.wms.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Long> {
    public List<Branch> findByName(String Name);

    public List<Branch> findByIdIn(List<Long> ids);

    public List<Branch> findByNameContainingIgnoreCase(String name);

}
