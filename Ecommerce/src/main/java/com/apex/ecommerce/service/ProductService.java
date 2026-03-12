package com.apex.ecommerce.service;

import com.apex.ecommerce.model.Product;
import com.apex.ecommerce.payload.ProductReqDTO;
import org.springframework.beans.factory.annotation.Autowired;

public interface ProductService {


    ProductReqDTO addProduct(Product product, Integer categoryId);
}
