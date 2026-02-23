package com.apex.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apex.ecommerce.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
