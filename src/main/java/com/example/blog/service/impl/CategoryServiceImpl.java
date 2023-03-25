package com.example.blog.service.impl;

import com.example.blog.entity.Category;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.CategoryDto;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category) -> modelMapper
                            .map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto category, Long categoryId) {
        Category categoryToUpdate = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryToUpdate.setName(categoryToUpdate.getName());
        categoryToUpdate.setDescription(categoryToUpdate.getDescription());
        categoryToUpdate.setId(category.getId());

        Category savedCategory = categoryRepository.save(categoryToUpdate);
        return modelMapper.map(savedCategory, CategoryDto.class);

    }
}
