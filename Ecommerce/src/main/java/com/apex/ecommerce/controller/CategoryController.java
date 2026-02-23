package com.apex.ecommerce.controller;

import com.apex.ecommerce.model.Category;
import com.apex.ecommerce.service.serviceImpl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;


    //@GetMapping("/public/categories")
    @RequestMapping(value = "/public/categories" , method = RequestMethod.GET)
    private ResponseEntity<List<Category>> getAllCategories() {
        List<Category> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @PostMapping("/public/categories")
    private ResponseEntity<String> createCategory(@RequestBody Category category){
        String message = categoryService.addCategory(category);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    private ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId) {
        try {
            String message = categoryService.deleteCategoryById(categoryId);
           // return ResponseEntity.ok(message);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Integer categoryId, @RequestBody Category category){
        try {
            categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(" Updated Category with ID : " + categoryId, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }
}
