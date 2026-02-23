package com.apex.ecommerce.service.serviceImpl;

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
		
		return categoryRepo.findAll();
    }

    @Override
    public String addCategory(Category category) {
    	
    	categoryRepo.save(category);
        return "Category created Successfully";
    }

    @Override
    public String deleteCategoryById(int id) {
    	
    	categoryRepo.findById(id)
    				.orElseThrow(() -> new ResponseStatusException(
    						HttpStatus.NOT_FOUND, "Resource Not Found"));
    	
    	categoryRepo.deleteById(id);
        return "Category deleted Successfully with Id : " + id;
    }

    @Override
    public Category updateCategory(int id, Category category) {
    	
    	Category updateCat = categoryRepo.findById(id)
    										.orElseThrow(() -> new ResponseStatusException(
    												HttpStatus.NOT_FOUND, "Resource Not Found"));
    	
    	updateCat.setCategoryName(category.getCategoryName());
    	return categoryRepo.save(updateCat);
    }


}
