package com.tacademy.wms.controllers;

import com.tacademy.wms.entities.Category;
import com.tacademy.wms.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1.0/category")
@AllArgsConstructor
public class CategoryController {
    private CategoryRepository categoryRepository;

    @PostMapping("/")
    private ResponseEntity<String> addCategory (@RequestBody Category category){

        categoryRepository.save(category);

        return new ResponseEntity<>("Categoria adicionada", HttpStatus.OK);
    }

    @GetMapping("/all")
    private ResponseEntity<List<Category>> getAllCategories(){

        List<Category> allCategories = new ArrayList<>();
        Iterable<Category> categories = categoryRepository.findAll();
        categories.forEach(allCategories::add);
        
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @GetMapping("/")
    private ResponseEntity<Optional<Category>> getCategory(@RequestParam("id") Long id){

        Optional<Category> category = categoryRepository.findById(id);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
