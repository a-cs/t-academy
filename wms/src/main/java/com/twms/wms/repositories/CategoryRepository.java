package com.twms.wms.repositories;

import com.twms.wms.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    Page<Category> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);
}
