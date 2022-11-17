package com.twms.wms.services;

import com.twms.wms.entities.Category;
import com.twms.wms.repositories.CategoryRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.twms.wms.exceptions.*;

import javax.persistence.EntityNotFoundException;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @SneakyThrows
    @Transactional
    public Category createGategory(Category category){
        if(categoryRepository.findByName(category.getName()) != null){
            throw new SQLIntegrityConstraintViolationException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    public List<Category> readCategories(){
        return categoryRepository.findAll();
    }

    public Category readById(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        Category categ = category.orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return categ;
    }

    public Category update(Long id, Category category){
        Category categ = this.readById(id);
        categ.setName(category.getName());

        return this.createGategory(categ);
    }

    public void delete(Long id){
        // Disable if someone is already using it #
        Category category = this.readById(id);

        categoryRepository.delete(category);
    }
}
