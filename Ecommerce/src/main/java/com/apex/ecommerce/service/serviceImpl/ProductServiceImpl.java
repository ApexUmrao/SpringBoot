package com.apex.ecommerce.service.serviceImpl;

import com.apex.ecommerce.exception.ResourceNotFoundException;
import com.apex.ecommerce.model.Category;
import com.apex.ecommerce.model.Product;
import com.apex.ecommerce.payload.ProductReqDTO;
import com.apex.ecommerce.repositories.CategoryRepo;
import com.apex.ecommerce.repositories.ProductRepo;
import com.apex.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductReqDTO addProduct(Product product, Integer categoryId) {
       Category category = categoryRepo.findById(categoryId)
                     .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        product.setCategory(category);
        product.setSpecialPrice(product.getPrice() - (product.getDiscount()*0.01)*product.getPrice());
        product.setImage("default.png");

        Product productSaved = productRepo.save(product);
        return modelMapper.map(productSaved,ProductReqDTO.class);
    }
}
