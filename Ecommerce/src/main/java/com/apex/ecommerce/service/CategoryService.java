package com.apex.ecommerce.service;

import com.apex.ecommerce.model.Category;
import com.apex.ecommerce.payload.CategoryReqDTO;
import com.apex.ecommerce.payload.CategoryResDTO;

import java.util.List;

public interface CategoryService {

    public CategoryResDTO getAllCategories();

    public CategoryReqDTO addCategory( CategoryReqDTO category);

    public CategoryReqDTO deleteCategoryById(int id);

    public CategoryReqDTO updateCategory( int id, CategoryReqDTO category);


}
