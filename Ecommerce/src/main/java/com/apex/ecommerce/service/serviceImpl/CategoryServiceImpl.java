package com.apex.ecommerce.service.serviceImpl;

import com.apex.ecommerce.exception.APIException;
import com.apex.ecommerce.exception.ResourceNotFoundException;
import com.apex.ecommerce.model.Category;
import com.apex.ecommerce.payload.CategoryReqDTO;
import com.apex.ecommerce.payload.CategoryResDTO;
import com.apex.ecommerce.repositories.CategoryRepo;
import com.apex.ecommerce.service.CategoryService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	public CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
	

	@Override
    public CategoryResDTO getAllCategories(Integer pageNo, Integer pageSize,  String sortBy, String sortOrder) {
        Sort sortCategory = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNo, pageSize, sortCategory);
        Page<Category> page = categoryRepo.findAll(pageDetails);
        List<Category> categories = page.getContent();
        if (categories.isEmpty()) {
            throw new APIException("No Category is Found");
        }
        List<CategoryReqDTO> categoryReqDTOList = new ArrayList<>();
        for (Category category : categories) {
            CategoryReqDTO categoryReqDTO = modelMapper.map(category, CategoryReqDTO.class);
            categoryReqDTOList.add(categoryReqDTO);
        }
        CategoryResDTO categoryResDTO = new CategoryResDTO();
        categoryResDTO.setContent(categoryReqDTOList);
        categoryResDTO.setPageNo(page.getNumber());
        categoryResDTO.setPageSize(page.getSize());
        categoryResDTO.setTotalElements(page.getTotalElements());
        categoryResDTO.setTotalPages(page.getTotalPages());
        categoryResDTO.setLastPage(page.isLast());
        return categoryResDTO;
    }

    @Override
    public CategoryReqDTO addCategory(CategoryReqDTO categoryDTO) {
        Category categoryEntity = modelMapper.map(categoryDTO, Category.class);
    	Category savedCategory = categoryRepo.findByCategoryName(categoryEntity.getCategoryName());
        if (savedCategory != null){
            throw new APIException("Category with name --> "+categoryEntity.getCategoryName()+" already exists");
        }
        savedCategory = categoryRepo.save(categoryEntity);

        return modelMapper.map(savedCategory, CategoryReqDTO.class);
    }

    @Override
    public CategoryReqDTO deleteCategoryById(int id) {
        Category findDeleteCategory = categoryRepo.findById(id)
    				                    .orElseThrow(() -> new ResourceNotFoundException
                                                ("Category", "CategoryID", id));

    	categoryRepo.delete(findDeleteCategory);

        return modelMapper.map(findDeleteCategory, CategoryReqDTO.class);
    }

    @Override
    public CategoryReqDTO updateCategory(int id, CategoryReqDTO categoryDTO) {


        Category updateCat = categoryRepo.findById(id)
    										.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryID", id));

        Category categoryEntitty = modelMapper.map(categoryDTO, Category.class);

        updateCat.setCategoryName(categoryEntitty.getCategoryName());

        Category updatedCategory = categoryRepo.save(updateCat);

        return modelMapper.map(updatedCategory, CategoryReqDTO.class);
    }


}
