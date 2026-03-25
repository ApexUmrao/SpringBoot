package com.apex.ecommerce.service;

import com.apex.ecommerce.payload.ProductReqDTO;
import com.apex.ecommerce.payload.ProductResDTO;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface ProductService {


    ProductReqDTO addProduct(ProductReqDTO product, Integer categoryId);

    ProductResDTO getAllProduct(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    ProductResDTO searchByCategory(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    ProductResDTO searchProductByKeyword(String keyword, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    ProductResDTO updateProduct(ProductReqDTO product, Long productId);

    ProductResDTO deleteProduct(Long productId);

    ProductResDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
    
    ProductResDTO getAllProductsForAdmin(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResDTO getAllProductsForSeller(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
