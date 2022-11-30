package com.twms.wms.repositories;

import com.twms.wms.entities.SKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SKURepository extends JpaRepository<SKU, Long> {

    public List<SKU> findByNameContainingIgnoreCase(String searchTerm);

    public List<SKU> findByIdIn(List<Long> ids);

}
