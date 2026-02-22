package com.apex.ecommerce.service;

import com.apex.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getAllCategories();

    public String addCategory( Category category);

    public String deleteCategoryById(int id);

    public Category updateCategory( int id, Category category);


}
