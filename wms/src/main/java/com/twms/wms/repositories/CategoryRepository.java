package com.twms.wms.repositories;

import com.twms.wms.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    List<Category> findByNameContainingIgnoreCase(String searchTerm);
}
