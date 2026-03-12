package com.apex.ecommerce.service;

import com.apex.ecommerce.payload.ProductReqDTO;
import com.apex.ecommerce.payload.ProductResDTO;

public interface ProductService {


    ProductReqDTO addProduct(ProductReqDTO product, Integer categoryId);

    ProductResDTO getAllProduct();

    ProductResDTO searchByCategory(Integer categoryId);

    ProductResDTO searchProductByKeyword(String keyword);

    ProductReqDTO updateProduct(ProductReqDTO product, Long productId);

    ProductResDTO deleteProduct(Long productId);
}
