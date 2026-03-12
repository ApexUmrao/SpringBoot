package com.apex.ecommerce.controller;

import com.apex.ecommerce.model.Product;
import com.apex.ecommerce.payload.ProductReqDTO;
import com.apex.ecommerce.payload.ProductResDTO;
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

    @GetMapping("/public/product")
    public ResponseEntity<ProductResDTO> getAllProducts() {
        ProductResDTO productResDTO = productService.getAllProduct();
        return new ResponseEntity<>(productResDTO,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{category_id}/products")
    public ResponseEntity<ProductResDTO> getProductByCategory(@PathVariable Integer category_id) {
        ProductResDTO productResDTO = productService.searchByCategory(category_id);
        return new ResponseEntity<>(productResDTO,HttpStatus.OK);
    }

    @GetMapping("/public/product/keyword/{keyword}")
    public ResponseEntity<ProductResDTO> getProductByKeyword(@PathVariable String keyword) {
        ProductResDTO productResDTO = productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(productResDTO,HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductReqDTO> updateProduct(@RequestBody Product product,
                                                       @PathVariable Long productId) {
        ProductReqDTO productReqDTO = productService.updateProduct(product, productId);
        return new ResponseEntity<>(productReqDTO,HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductResDTO> deleteProduct(@PathVariable Long productId) {
        ProductResDTO productResDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productResDTO,HttpStatus.OK);
    }
}
