package com.apex.ecommerce.service.serviceImpl;

import com.apex.ecommerce.exception.ResourceNotFoundException;
import com.apex.ecommerce.model.Category;
import com.apex.ecommerce.model.Product;
import com.apex.ecommerce.payload.ProductReqDTO;
import com.apex.ecommerce.payload.ProductResDTO;
import com.apex.ecommerce.repositories.CategoryRepo;
import com.apex.ecommerce.repositories.ProductRepo;
import com.apex.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ProductResDTO getAllProduct() {
        List<Product> products = productRepo.findAll();
        List<ProductReqDTO> productReqDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductReqDTO productReqDTO = modelMapper.map(product,ProductReqDTO.class);
            productReqDTOS.add(productReqDTO);
        }
        ProductResDTO productResDTO = new ProductResDTO();
        productResDTO.setContent(productReqDTOS);
        return productResDTO;
    }

    @Override
    public ProductResDTO searchByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        List<Product> products = productRepo.findByCategoryOrderByPriceAsc(category);
        List<ProductReqDTO> productReqDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductReqDTO productReqDTO = modelMapper.map(product,ProductReqDTO.class);
            productReqDTOS.add(productReqDTO);
        }
        ProductResDTO productResDTO = new ProductResDTO();
        productResDTO.setContent(productReqDTOS);
        return productResDTO;
    }

    @Override
    public ProductResDTO searchProductByKeyword(String keyword) {
        List<Product> products = productRepo.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductReqDTO> productReqDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductReqDTO productReqDTO = modelMapper.map(product,ProductReqDTO.class);
            productReqDTOS.add(productReqDTO);
        }
        ProductResDTO productResDTO = new ProductResDTO();
        productResDTO.setContent(productReqDTOS);
        return productResDTO;
    }
}
