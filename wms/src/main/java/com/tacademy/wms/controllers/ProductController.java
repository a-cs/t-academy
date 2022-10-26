package com.tacademy.wms.controllers;

import com.tacademy.wms.entities.Category;
import com.tacademy.wms.entities.Product;
import com.tacademy.wms.repositories.CategoryRepository;
import com.tacademy.wms.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1.0/products/")
public class ProductController {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @PostMapping("/{cat_id}")
    private ResponseEntity<String> addProduct (@PathVariable(value = "cat_id") Long category_id, @RequestBody Product product){

        Category category = categoryRepository.findById(category_id).stream().findFirst().orElse(null);
        product.setCategory(category);
        productRepository.save(product);

        return new ResponseEntity<>("Produto Cadastrado", HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Product>> getAllProducts(){

        List<Product> allproducts = new ArrayList<>();
        Iterable<Product> products = productRepository.findAll();
        products.forEach(allproducts::add);

        return new ResponseEntity<>(allproducts, HttpStatus.OK);
    }
}
