package com.apex.ecommerce.service.serviceImpl;

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
        product.setCategory(category);
        product.setSpecialPrice(product.getPrice() - (product.getDiscount()*0.01)*product.getPrice());
        product.setImage("default.png");

        Product productSaved = productRepo.save(product);
        return modelMapper.map(productSaved,ProductReqDTO.class);
    }

    @Override
    public ProductResDTO getAllProduct() {
        List<Product> products = productRepo.findAll();
        List<ProductReqDTO> productReqDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductReqDTO productReqDTO = modelMapper.map(product,ProductReqDTO.class);
            productReqDTOS.add(productReqDTO);
        }
        ProductResDTO productResDTO = new ProductResDTO();
        productResDTO.setContent(productReqDTOS);
        return productResDTO;
    }

    @Override
    public ProductResDTO searchByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        List<Product> products = productRepo.findByCategoryOrderByPriceAsc(category);
        List<ProductReqDTO> productReqDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductReqDTO productReqDTO = modelMapper.map(product,ProductReqDTO.class);
            productReqDTOS.add(productReqDTO);
        }
        ProductResDTO productResDTO = new ProductResDTO();
        productResDTO.setContent(productReqDTOS);
        return productResDTO;
    }

    @Override
    public ProductResDTO searchProductByKeyword(String keyword) {
        List<Product> products = productRepo.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductReqDTO> productReqDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductReqDTO productReqDTO = modelMapper.map(product,ProductReqDTO.class);
            productReqDTOS.add(productReqDTO);
        }
        ProductResDTO productResDTO = new ProductResDTO();
        productResDTO.setContent(productReqDTOS);
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
        productToUpdate.setSpecialPrice(product.getSpecialPrice());

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
