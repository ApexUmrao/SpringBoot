package com.apex.ecommerce.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import com.apex.ecommerce.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Category findByCategoryName(String categoryName);
}
