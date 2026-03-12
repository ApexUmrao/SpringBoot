package com.apex.ecommerce.controller;

import com.apex.ecommerce.model.Product;
import com.apex.ecommerce.payload.ProductReqDTO;
import com.apex.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{category_id}/product")
    public ResponseEntity<ProductReqDTO> addProduct(@RequestBody Product product,
                                                    @PathVariable Integer category_id) {
        ProductReqDTO productReq = productService.addProduct(product,category_id);
        return new ResponseEntity<>(productReq,HttpStatus.CREATED);
    }
}
