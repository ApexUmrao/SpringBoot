package com.apex.ecommerce.service;

import com.apex.ecommerce.model.Product;
import com.apex.ecommerce.payload.ProductReqDTO;
import com.apex.ecommerce.payload.ProductResDTO;
import org.springframework.beans.factory.annotation.Autowired;

public interface ProductService {


    ProductReqDTO addProduct(Product product, Integer categoryId);

    ProductResDTO getAllProduct();

    ProductResDTO searchByCategory(Integer categoryId);

    ProductResDTO searchProductByKeyword(String keyword);

    ProductReqDTO updateProduct(Product product, Long productId);

    ProductResDTO deleteProduct(Long productId);
}
