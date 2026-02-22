package com.apex.ecommerce.service.serviceImpl;

import com.apex.ecommerce.model.Category;
import com.apex.ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    public List<Category> categories = new ArrayList<>();
    private int id;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public String addCategory(Category category) {
        category.setCategoryId(id++);
        categories.add(category);
        return "Category created Successfully";
    }

    @Override
    public String deleteCategoryById(int id) {
//        Category obj = categories.get(id);
//        categories.remove(obj);
        Category obj = categories.stream()
                            .filter(c -> c.getCategoryId() == id)
                            .findFirst()
                          //  .orElse(null);
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        if (obj != null) {
//            categories.remove(obj);
//        } else {
//            return "Category not found";
//        }

        categories.remove(obj);
        return "Category deleted Successfully with Id : " + id;
    }

    @Override
    public Category updateCategory(int id, Category category) {
       // categories.set(id, category);
        Optional<Category> optionalCategory = categories.stream()
                                            .filter(c -> c.getCategoryId() == id)
                                            .findFirst();
        if (optionalCategory.isPresent()) {
            Category obj = optionalCategory.get();
            obj.setCategoryName(category.getCategoryName());
            return obj;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }


}
