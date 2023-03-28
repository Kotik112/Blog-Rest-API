package com.example.blog.controller;

import static com.example.blog.utils.AppConstants.BASE_URL;

import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;
import com.example.blog.service.PostService;
import com.example.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BASE_URL)
@Tag(name = "CRUD REST APIs for Post Resource.")
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(
      summary = "Create Post REST API",
      description = "Create Post REST API is used to save post to the database.")
  @ApiResponse(responseCode = "201", description = "Http status 201 CREATED")
  @PostMapping()
  public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Get All Post REST API",
      description = "Get All Post REST API is used to get a Page of Posts from the database.")
  @ApiResponse(responseCode = "200", description = "Http status 200 OK")
  @GetMapping
  public ResponseEntity<PostResponse> getAllPosts(
      @RequestParam(
              value = "pageNo",
              defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
              required = false)
          int page,
      @RequestParam(
              value = "pageSize",
              defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
              required = false)
          int size,
      @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
          String sortBy,
      @RequestParam(
              value = "sortDir",
              defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
              required = false)
          String sortDirection) {
    return new ResponseEntity<>(
        postService.getAllPosts(page, size, sortBy, sortDirection), HttpStatus.OK);
  }

  @Operation(
      summary = "Get Post By ID REST API",
      description = "Get Post By ID REST API is used to get a single post from the database.")
  @ApiResponse(responseCode = "200", description = "Http status 200 OK")
  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
    return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
  }

  @Operation(
      summary = "Get Post By Category REST API",
      description =
          "Get All Posts By Category REST API is used to get all Posts by a particular Category from the database.")
  @ApiResponse(responseCode = "200", description = "Http status 200 OK")
  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<PostDto>> getAllPostsByCategoryId(
      @PathVariable("categoryId") Long id) {
    return ResponseEntity.ok(postService.getPostsByCategoryId(id));
  }

  @Operation(
      summary = "Update Post REST API",
      description = "Update a Post REST API is used to Update an existing Post on the database.")
  @ApiResponse(responseCode = "200", description = "Http status 200 OK")
  @PreAuthorize("hasRole('ADMIN')")
  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping("/{id}")
  public ResponseEntity<PostDto> updatePost(
      @PathVariable("id") Long id, @Valid @RequestBody PostDto postDto) {
    return new ResponseEntity<>(postService.updatePost(id, postDto), HttpStatus.OK);
  }

  @Operation(
      summary = "Delete Post REST API",
      description = "Delete a Post REST API is used to delete an existing Post on the database.")
  @ApiResponse(responseCode = "200", description = "Http status 200 OK")
  @PreAuthorize("hasRole('ADMIN')")
  @SecurityRequirement(name = "Bearer Authentication")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
    postService.deletePost(id);
    return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
  }
}
