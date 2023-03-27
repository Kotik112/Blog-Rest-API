package com.example.blog.service;

import com.example.blog.payload.CategoryDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
  CategoryDto addCategory(CategoryDto categoryDto);

  CategoryDto getCategory(Long categoryId);

  List<CategoryDto> getAllCategories();

  CategoryDto updateCategory(CategoryDto category, Long categoryId);

  void deleteCategory(Long categoryId);
}
