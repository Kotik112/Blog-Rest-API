package com.example.blog.service.impl;

import com.example.blog.entity.Category;
import com.example.blog.entity.Post;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
  @Autowired private PostRepository postRepository;
  @Autowired private CategoryRepository categoryRepository;
  @Autowired private ModelMapper mapper;

  @Override
  public PostDto createPost(PostDto postDto) {
    Category category = findByIdOrThrow(postDto);

    // Convert DTO to Entity
    Post post = mapToEntity(postDto);
    post.setCategory(category);

    Post newPost = postRepository.save(post);

    return mapToDto(newPost);
  }

  @Override
  public PostResponse getAllPosts(int page, int size, String sortBy, String sortDirection) {
    Sort sort =
        sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Post> posts = postRepository.findAll(pageable);

    List<Post> listOfPosts = posts.getContent();

    List<PostDto> content = listOfPosts.stream().map(this::mapToDto).toList();
    PostResponse postResponse = new PostResponse();
    postResponse.setContent(content);
    postResponse.setPage(page);
    postResponse.setSize(size);
    postResponse.setTotalElements(posts.getTotalElements());
    postResponse.setTotalPages(posts.getTotalPages());
    postResponse.setLast(posts.isLast());

    return postResponse;
  }

  @Override
  public List<PostDto> getPostsByCategoryId(Long categoryId) {
    Category category = findCategoryByIdOrThrow(categoryId);
    List<Post> posts = postRepository.findPostsByCategoryId(category.getId());
    return posts.stream().map(this::mapToDto).collect(Collectors.toList());
  }

  @Override
  public PostDto getPostById(Long id) {
    Post post =
        postRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    return mapToDto(post);
  }

  @Override
  public PostDto updatePost(Long id, PostDto postDto) {
    Category category = findByIdOrThrow(postDto);
    Post post =
        postRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

    post.setTitle(postDto.getTitle());
    post.setDescription(postDto.getDescription());
    post.setContent(postDto.getContent());
    post.setCategory(category);

    Post updatedPost = postRepository.save(post);

    return mapToDto(updatedPost);
  }

  @Override
  public void deletePost(Long id) {
    Post post =
        postRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    postRepository.delete(post);
  }

  public PostDto mapToDto(Post post) {
    return mapper.map(post, PostDto.class);
  }

  public Post mapToEntity(PostDto postDto) {
    return mapper.map(postDto, Post.class);
  }

  private Category findByIdOrThrow(PostDto postDto) {
    return categoryRepository
        .findById(postDto.getCategoryId())
        .orElseThrow(
            () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
  }

  private Category findCategoryByIdOrThrow(Long categoryId) {
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
  }
}
