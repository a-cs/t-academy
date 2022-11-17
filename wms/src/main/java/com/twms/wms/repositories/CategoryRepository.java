package com.twms.wms.repositories;

import com.twms.wms.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(@NotBlank String name);
}
