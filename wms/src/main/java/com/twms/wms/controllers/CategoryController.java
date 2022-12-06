package com.twms.wms.controllers;

import com.twms.wms.entities.Category;
import com.twms.wms.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/pageable")
    public ResponseEntity<Page<Category>> readAllCategories(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.readCategories(pageable));
    }

    @GetMapping
    public ResponseEntity<List<Category>> readAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.readCategories());
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<Category> readById(@PathVariable("idCategory") Long idCategory){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.readById(idCategory));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createGategory(category));
    }

    @PutMapping("/{idCategory}")
    public ResponseEntity<Category> update(@PathVariable("idCategory") Long idCategory, @RequestBody Category category){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(idCategory, category));
    }

    @DeleteMapping("/{idCategory}")
    public ResponseEntity<Void> delete(@PathVariable("idCategory") Long idCategory){
        categoryService.delete(idCategory);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Category>> searchCategory(@RequestParam String term, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.searchTerm(term, pageable));
    }

}
