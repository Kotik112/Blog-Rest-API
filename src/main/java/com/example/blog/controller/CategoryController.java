package com.example.blog.controller;

import static com.example.blog.utils.AppConstants.BASE_CATEGORY_URL;

import com.example.blog.payload.CategoryDto;
import com.example.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BASE_CATEGORY_URL)
@Tag(name = "CRUD REST APIs for Category Resource")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
    CategoryDto savedCategory = categoryService.addCategory(categoryDto);
    return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
    return ResponseEntity.ok(categoryService.getCategory(id));
  }

  @GetMapping("")
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    return ResponseEntity.ok(categoryService.getAllCategories());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}")
  public ResponseEntity<CategoryDto> updateCategory(
      @RequestBody CategoryDto categoryToUpdate, @PathVariable Long id) {
    return ResponseEntity.ok(categoryService.updateCategory(categoryToUpdate, id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.ok("Category successfully deleted!");
  }
}
