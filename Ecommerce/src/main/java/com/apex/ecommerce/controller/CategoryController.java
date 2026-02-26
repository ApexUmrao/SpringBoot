package com.apex.ecommerce.controller;

import com.apex.ecommerce.config.AppConstant;
import com.apex.ecommerce.payload.CategoryReqDTO;
import com.apex.ecommerce.payload.CategoryResDTO;
import com.apex.ecommerce.service.serviceImpl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;


    @GetMapping("/public/categories")
    private ResponseEntity<CategoryResDTO> getAllCategories(
            @RequestParam (defaultValue = AppConstant.PAGE_NO , required = false) Integer pageNo,
            @RequestParam (defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam (defaultValue = AppConstant.SORT_CATEGORY_BY , required = false) String sortBy,
            @RequestParam (defaultValue = AppConstant.SORT_CATEGORY_ODR, required = false) String sortOrder) {
        CategoryResDTO allCategories = categoryService.getAllCategories(pageNo , pageSize, sortBy, sortOrder);
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
