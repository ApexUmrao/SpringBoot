package com.apex.ecommerce.service.serviceImpl;

import com.apex.ecommerce.exception.APIException;
import com.apex.ecommerce.exception.ResourceNotFoundException;
import com.apex.ecommerce.model.Category;
import com.apex.ecommerce.model.Product;
import com.apex.ecommerce.payload.ProductReqDTO;
import com.apex.ecommerce.payload.ProductResDTO;
import com.apex.ecommerce.repositories.CategoryRepo;
import com.apex.ecommerce.repositories.ProductRepo;
import com.apex.ecommerce.service.FileService;
import com.apex.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

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

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductReqDTO addProduct(ProductReqDTO productReqDTO, Integer categoryId) {
       Category category = categoryRepo.findById(categoryId)
                     .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        Product product = modelMapper.map(productReqDTO, Product.class);
//
//        Product savedProduct = productRepo.findByProductName(product);
//        if (savedProduct != null) {
//            throw new APIException("Product with name --> "+product.getProductName()+" already exists");
//        }

        Boolean ifProductNotPresent = true;
        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(product.getProductName())) {
                ifProductNotPresent = false;
            }
        }
        if (ifProductNotPresent) {
            product.setCategory(category);
            product.setSpecialPrice(product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice());
            product.setImage("default.png");

            Product productSaved = productRepo.save(product);
            return modelMapper.map(productSaved, ProductReqDTO.class);

        } else {
            throw new APIException("Product with name --> "+product.getProductName()+" already exists");
        }
    }

    @Override
    public ProductResDTO getAllProduct(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortProduct = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNo, pageSize, sortProduct);
        Page<Product> page = productRepo.findAll(pageDetails);
        List<Product> products = page.getContent();
        if (products.isEmpty()) {
            throw new APIException("No Product found");
        }
        List<ProductReqDTO> productReqDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductReqDTO productReqDTO = modelMapper.map(product,ProductReqDTO.class);
            productReqDTOS.add(productReqDTO);
        }
        ProductResDTO productResDTO = new ProductResDTO();
        productResDTO.setContent(productReqDTOS);
        productResDTO.setPageNo(page.getNumber());
        productResDTO.setPageSize(page.getSize());
        productResDTO.setTotalElements(page.getTotalElements());
        productResDTO.setTotalPages(page.getTotalPages());
        productResDTO.setLastPage(page.isLast());
        return productResDTO;
    }

    @Override
    public ProductResDTO searchByCategory(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        Sort sortProduct = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNo, pageSize, sortProduct);
        Page<Product> page = productRepo.findByCategoryOrderByPriceAsc(category,pageDetails);
        List<Product> products = page.getContent();

        if (products.isEmpty()) {
            throw new APIException("No Product found");
        }

        List<ProductReqDTO> productReqDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductReqDTO productReqDTO = modelMapper.map(product,ProductReqDTO.class);
            productReqDTOS.add(productReqDTO);
        }
        ProductResDTO productResDTO = new ProductResDTO();
        productResDTO.setContent(productReqDTOS);
        productResDTO.setPageNo(page.getNumber());
        productResDTO.setPageSize(page.getSize());
        productResDTO.setTotalElements(page.getTotalElements());
        productResDTO.setTotalPages(page.getTotalPages());
        productResDTO.setLastPage(page.isLast());
        return productResDTO;
    }

    @Override
    public ProductResDTO searchProductByKeyword(String keyword, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortProduct = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNo, pageSize, sortProduct);
        Page<Product> page = productRepo.findByProductNameLikeIgnoreCase('%' + keyword + '%',pageDetails);
        List<Product> products = page.getContent();

        if (products.isEmpty()) {
            throw new APIException("No Product found");
        }

        List<ProductReqDTO> productReqDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductReqDTO productReqDTO = modelMapper.map(product,ProductReqDTO.class);
            productReqDTOS.add(productReqDTO);
        }
        ProductResDTO productResDTO = new ProductResDTO();
        productResDTO.setContent(productReqDTOS);
        productResDTO.setPageNo(page.getNumber());
        productResDTO.setPageSize(page.getSize());
        productResDTO.setTotalElements(page.getTotalElements());
        productResDTO.setTotalPages(page.getTotalPages());
        productResDTO.setLastPage(page.isLast());
        return productResDTO;
    }

    @Override
    public ProductReqDTO updateProduct(ProductReqDTO productReqDTO, Long productId) {
        Product productToUpdate = productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","Product Id",productId));

        Product product = modelMapper.map(productReqDTO, Product.class);

        productToUpdate.setProductName(product.getProductName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setQuantity(product.getQuantity());
        productToUpdate.setDiscount(product.getDiscount());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setSpecialPrice(product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice());

        Product savedProduct = productRepo.save(productToUpdate);

        return modelMapper.map (savedProduct,ProductReqDTO.class);
    }

    @Override
    public ProductReqDTO deleteProduct(Long productId) {
        Product productToDelete = productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","Product Id",productId));
        productRepo.delete(productToDelete);
        return modelMapper.map(productToDelete,ProductReqDTO.class);
    }

    @Override
    public ProductReqDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
       Product productImageToUpdate = productRepo.findById(productId)
               .orElseThrow(()-> new ResourceNotFoundException("Product","Product Id",productId));

       String imageName = fileService.uploadImage(path, image);

       productImageToUpdate.setImage(imageName);

       Product updatedProduct = productRepo.save(productImageToUpdate);

       return modelMapper.map(updatedProduct,ProductReqDTO.class);
    }
}
