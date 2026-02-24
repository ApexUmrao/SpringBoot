package com.apex.ecommerce.service.serviceImpl;

import com.apex.ecommerce.exception.APIException;
import com.apex.ecommerce.exception.ResourceNotFoundException;
import com.apex.ecommerce.model.Category;
import com.apex.ecommerce.repositories.CategoryRepo;
import com.apex.ecommerce.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	public CategoryRepo categoryRepo;
	

	@Override
    public List<Category> getAllCategories() {
		List<Category> categories = categoryRepo.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No Category is Found");
        }
		return categories;
    }

    @Override
    public String addCategory(Category category) {
    	Category savedCategory = categoryRepo.findByCategoryName(category.getCategoryName());
        if (savedCategory != null){
            throw new APIException("Category with name --> "+category.getCategoryName()+" already exists");
        }
    	categoryRepo.save(category);
        return "Category created Successfully";
    }

    @Override
    public String deleteCategoryById(int id) {
    	
    	categoryRepo.findById(id)
    				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", id));
    	
    	categoryRepo.deleteById(id);
        return "Category deleted Successfully with Id : " + id;
    }

    @Override
    public Category updateCategory(int id, Category category) {
    	
    	Category updateCat = categoryRepo.findById(id)
    										.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", id));
    	
    	updateCat.setCategoryName(category.getCategoryName());
    	return categoryRepo.save(updateCat);
    }


}
