package com.apex.ecommerce.controller;

import com.apex.ecommerce.model.Category;
import com.apex.ecommerce.payload.CategoryReqDTO;
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
    private ResponseEntity<CategoryReqDTO> createCategory(@Valid @RequestBody CategoryReqDTO category){
        CategoryReqDTO addedCategoryDTO = categoryService.addCategory(category);
        return new ResponseEntity<>(addedCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    private ResponseEntity<CategoryReqDTO> deleteCategory(@PathVariable Integer categoryId) {
            CategoryReqDTO deletedCat = categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(deletedCat);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryReqDTO> updateCategory(@Valid @PathVariable Integer categoryId,
                                                        @RequestBody CategoryReqDTO category){
            CategoryReqDTO updatedCat = categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(updatedCat, HttpStatus.OK);
    }
}
