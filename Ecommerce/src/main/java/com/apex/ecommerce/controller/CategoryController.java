package com.apex.ecommerce.controller;

import com.apex.ecommerce.model.Category;
import com.apex.ecommerce.payload.CategoryResDTO;
import com.apex.ecommerce.service.serviceImpl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
    private ResponseEntity<CategoryResDTO> getAllCategories() {
        CategoryResDTO allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @PostMapping("/public/categories")
    private ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        String message = categoryService.addCategory(category);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    private ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId) {
            String message = categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @PathVariable Integer categoryId,
                                                        @RequestBody Category category){
            categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(" Updated Category with ID : " + categoryId, HttpStatus.OK);
    }
}
