package com.twms.wms.services;

import com.twms.wms.entities.Category;
import com.twms.wms.entities.SKU;
import com.twms.wms.repositories.CategoryRepository;
import com.twms.wms.repositories.SKURepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SKURepository skuRepository;

    @SneakyThrows
    @Transactional
    public Category createGategory(Category category){
        if(categoryRepository.findByName(category.getName()) != null){
            throw new SQLIntegrityConstraintViolationException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    public Page<Category> readCategories(Pageable pageable){
        return categoryRepository.findAll(pageable);
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

    public void delete(Long id) throws Exception{
        // Disable if someone is already using it #
        boolean isAssociated = skuRepository.existsByCategoryId(id);

        if (isAssociated) {
            throw new SQLIntegrityConstraintViolationException("Cannot delete category because it is associated with a product");
        }
        skuRepository.deleteById(id);
    }

    public Page<Category> searchTerm(String searchTerm, Pageable pageable) {
        String terms = searchTerm.replace("-", " ");
        return categoryRepository.findByNameContainingIgnoreCase(terms, pageable);
    }
}
