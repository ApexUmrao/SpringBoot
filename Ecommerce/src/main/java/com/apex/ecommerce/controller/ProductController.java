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
    ProductService productService;

    @PostMapping("/admin/categories/{category_id}/product")
    public ResponseEntity<ProductReqDTO> addProduct(@Valid @RequestBody ProductReqDTO productReqDTO,
                                                    @PathVariable Integer category_id) {
        ProductReqDTO productReq = productService.addProduct(productReqDTO,category_id);
        return new ResponseEntity<>(productReq,HttpStatus.CREATED);
    }

    @PostMapping("/seller/categories/{categoryId}/product")
    public ResponseEntity<ProductReqDTO> addProductSeller(@Valid @RequestBody ProductReqDTO productDTO,
                                                 @PathVariable Integer categoryId){
    	ProductReqDTO savedProductDTO = productService.addProduct( productDTO,categoryId);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
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
    public ResponseEntity<ProductResDTO> updateProduct(@Valid @RequestBody ProductReqDTO productReqDTO,
                                                       @PathVariable Long productId) {
    	ProductResDTO productReq = productService.updateProduct(productReqDTO, productId);
        return new ResponseEntity<>(productReq,HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductResDTO> deleteProduct(@PathVariable Long productId) {
    	ProductResDTO productReqDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productReqDTO,HttpStatus.OK);
    }

    @PutMapping("/admin/product/{productId}/image")
    public ResponseEntity<ProductResDTO> updateProductImage(@PathVariable Long productId,
                                                            @RequestParam("image") MultipartFile image) throws IOException {

    ProductResDTO productReqDTO = productService.updateProductImage(productId,image);
    return new ResponseEntity<>(productReqDTO,HttpStatus.OK);
    }
    
    @GetMapping("/admin/products")
    public ResponseEntity<ProductResDTO> getAllProductsForAdmin(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NO, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCT_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_PRODUCT_ODR, required = false) String sortOrder
    ){
    	ProductResDTO productResponse = productService.getAllProductsForAdmin(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    
    @GetMapping("/seller/products")
    public ResponseEntity<ProductResDTO> getAllProductsForSeller(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NO, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCT_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_PRODUCT_ODR, required = false) String sortOrder
    ){
    	ProductResDTO productResponse = productService.getAllProductsForSeller(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @PutMapping("/seller/products/{productId}")
    public ResponseEntity<ProductResDTO> updateProductSeller(@Valid @RequestBody ProductReqDTO productDTO,
                                                    @PathVariable Long productId){
    	ProductResDTO updatedProductDTO = productService.updateProduct(productDTO, productId);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/seller/products/{productId}")
    public ResponseEntity<ProductResDTO> deleteProductSeller(@PathVariable Long productId){
    	ProductResDTO deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    @PutMapping("/seller/products/{productId}/image")
    public ResponseEntity<ProductResDTO> updateProductImageSeller(@PathVariable Long productId,
                                                         @RequestParam("image")MultipartFile image) throws IOException {
    	ProductResDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
