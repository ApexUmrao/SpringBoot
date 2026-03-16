package com.apex.ecommerce.controller;

import com.apex.ecommerce.config.AppConstant;
import com.apex.ecommerce.payload.ProductReqDTO;
import com.apex.ecommerce.payload.ProductResDTO;
import com.apex.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{category_id}/product")
    public ResponseEntity<ProductReqDTO> addProduct(@Valid @RequestBody ProductReqDTO productReqDTO,
                                                    @PathVariable Integer category_id) {
        ProductReqDTO productReq = productService.addProduct(productReqDTO,category_id);
        return new ResponseEntity<>(productReq,HttpStatus.CREATED);
    }

    @GetMapping("/public/product")
    public ResponseEntity<ProductResDTO> getAllProducts(
            @RequestParam (defaultValue = AppConstant.PAGE_NO , required = false) Integer pageNo,
            @RequestParam (defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam (defaultValue = AppConstant.SORT_PRODUCT_NAME , required = false) String sortBy,
            @RequestParam (defaultValue = AppConstant.SORT_PRODUCT_ODR, required = false) String sortOrder) {
        ProductResDTO productResDTO = productService.getAllProduct(pageNo , pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResDTO,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{category_id}/products")
    public ResponseEntity<ProductResDTO> getProductByCategory(@PathVariable Integer category_id,
    		@RequestParam (defaultValue = AppConstant.PAGE_NO , required = false) Integer pageNo,
    		@RequestParam (defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
    		@RequestParam (defaultValue = AppConstant.SORT_PRODUCT_NAME , required = false) String sortBy,
    		@RequestParam (defaultValue = AppConstant.SORT_PRODUCT_ODR, required = false) String sortOrder) {

        ProductResDTO productResDTO = productService.searchByCategory(category_id, pageNo , pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResDTO,HttpStatus.OK);
    }

    @GetMapping("/public/product/keyword/{keyword}")
    public ResponseEntity<ProductResDTO> getProductByKeyword(@PathVariable String keyword,
    		@RequestParam (defaultValue = AppConstant.PAGE_NO , required = false) Integer pageNo,
    		@RequestParam (defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize,
    		@RequestParam (defaultValue = AppConstant.SORT_PRODUCT_NAME , required = false) String sortBy,
    		@RequestParam (defaultValue = AppConstant.SORT_PRODUCT_ODR, required = false) String sortOrder) {
        ProductResDTO productResDTO = productService.searchProductByKeyword(keyword,pageNo , pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResDTO,HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductReqDTO> updateProduct(@Valid @RequestBody ProductReqDTO productReqDTO,
                                                       @PathVariable Long productId) {
        ProductReqDTO productReq = productService.updateProduct(productReqDTO, productId);
        return new ResponseEntity<>(productReq,HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductReqDTO> deleteProduct(@PathVariable Long productId) {
        ProductReqDTO productReqDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productReqDTO,HttpStatus.OK);
    }

    @PutMapping("/admin/product/{productId}/image")
    public ResponseEntity<ProductReqDTO> updateProductImage(@PathVariable Long productId,
                                                            @RequestParam("image") MultipartFile image) throws IOException {

    ProductReqDTO productReqDTO = productService.updateProductImage(productId,image);
    return new ResponseEntity<>(productReqDTO,HttpStatus.OK);
    }
}
