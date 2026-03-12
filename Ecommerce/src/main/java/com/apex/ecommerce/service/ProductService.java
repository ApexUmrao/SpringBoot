package com.apex.ecommerce.service;

import com.apex.ecommerce.payload.ProductReqDTO;
import com.apex.ecommerce.payload.ProductResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {


    ProductReqDTO addProduct(ProductReqDTO product, Integer categoryId);

    ProductResDTO getAllProduct(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    ProductResDTO searchByCategory(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    ProductResDTO searchProductByKeyword(String keyword, Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    ProductReqDTO updateProduct(ProductReqDTO product, Long productId);

    ProductReqDTO deleteProduct(Long productId);

    ProductReqDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
