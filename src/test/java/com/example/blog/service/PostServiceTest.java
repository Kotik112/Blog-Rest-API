package com.example.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.blog.entity.Category;
import com.example.blog.entity.Post;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.blog.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class PostServiceTest {
  @InjectMocks
  PostServiceImpl postService;
  @Mock PostRepository postRepository;
  @Mock
  private CategoryRepository categoryRepository;
  @Mock
  ModelMapper modelMapper;
  @Captor private ArgumentCaptor<Long> idCaptor;

  @Test
  public void getPostById() {
    Post post = new Post();
    post.setId(1L);
    post.setTitle("title");
    post.setContent("content");
    post.setDescription("description");

    // Expected output data
    PostDto expectedNoteDto = new PostDto();
    expectedNoteDto.setId(1L);
    expectedNoteDto.setTitle("title");
    expectedNoteDto.setContent("content");
    expectedNoteDto.setDescription("description");
    
    // Mock the dependencies
    when(postRepository.findById(idCaptor.capture())).thenReturn(Optional.of(post));
    when(modelMapper.map(post, PostDto.class)).thenReturn(expectedNoteDto);

    // Call the service
    PostDto savedPostDto = postService.getPostById(1L);
    Long capturedId = idCaptor.getValue();

    assertNotNull(savedPostDto);
    assertEquals(capturedId, savedPostDto.getId());
    assertEquals(expectedNoteDto.getTitle(), savedPostDto.getTitle());
    assertEquals(expectedNoteDto.getContent(), savedPostDto.getContent());
    assertEquals(expectedNoteDto.getDescription(), savedPostDto.getDescription());
  }
  
  @Test
  public void getPostByIdThrowsResourceNotFoundExceptionWithInvalidId() {
    Long invalidId = 999L;
    
    when(postRepository.findById(invalidId)).thenReturn(Optional.empty());
  
    assertThrows(
            ResourceNotFoundException.class, () -> postService.getPostById(invalidId));
  }
  
  @Test
  public void getAllPosts() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
    List<Post> postList = new ArrayList<>();
    postList.add(new Post(1L, "title1", "content1", "description1", null, null));
    postList.add(new Post(2L, "title2", "content2", "description2", null, null));
    postList.add(new Post(3L, "title3", "content3", "description3", null, null));
    Page<Post> expectedPostPage = new PageImpl<>(postList, pageable, postList.size());
    
    when(postRepository.findAll(pageable)).thenReturn(expectedPostPage);
    
    PostResponse postResponse = postService.getAllPosts(0, 10, "createdAt", "desc");
  
    assertNotNull(postResponse);
    assertEquals(0, postResponse.getPage());
    assertEquals(10, postResponse.getSize());
    assertEquals(3, postResponse.getTotalElements());
    assertEquals(1, postResponse.getTotalPages());
    assertEquals(3, postResponse.getContent().size());
  }
  
  @Test
  public void getPostsByCategoryId() {
    Category category = new Category(1L, "test", "description1", null);
    
    List<Post> postList = new ArrayList<>();
    postList.add(new Post(1L, "title1", "content1", "description1", null, category));
    postList.add(new Post(2L, "title2", "content2", "description2", null, category));
    
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
    when(postRepository.findPostsByCategoryId(idCaptor.capture())).thenReturn(postList);
    
    List<PostDto> posts = postService.getPostsByCategoryId(1L);
    
    assertNotNull(posts);
    assertFalse(posts.isEmpty());
    assertEquals(2, posts.size());
    assertEquals(1L, idCaptor.getValue());
  }
  
  @Test
  public void getPostByInvalidCategoryIdThrowsResourceNotFoundException() {
    Long invalidId = 999L;
    
    when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());
  
    assertThrows(
            ResourceNotFoundException.class, () -> postService.getPostsByCategoryId(invalidId));
  }
  
  @Test
  public void createPost() {
    Category category = new Category(1L, "test", "description1", null);
  
    PostDto postDto = new PostDto();
    postDto.setTitle("title");
    postDto.setContent("content");
    postDto.setDescription("description");
    postDto.setCategoryId(category.getId());
    
    // Expected data
    Post post = new Post();
    post.setId(1L);
    post.setTitle("title");
    post.setContent("content");
    post.setDescription("description");
    post.setCategory(category);
    
    when(modelMapper.map(postDto, Post.class)).thenReturn(post);
    when(postRepository.save(post)).thenReturn(post);
    when(modelMapper.map(post, PostDto.class)).thenReturn(postDto);
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
    
    // Call the service
    PostDto savedPostDto = postService.createPost(postDto);
    
    assertNotNull(savedPostDto);
    assertEquals(postDto.getTitle(), savedPostDto.getTitle());
    assertEquals(postDto.getContent(), savedPostDto.getContent());
    assertEquals(postDto.getDescription(), savedPostDto.getDescription());
  }
  
  @Test
  public void updatePost() {
    Category category = new Category(1L, "test", "description1", null);
  
    PostDto postDto = new PostDto();
    postDto.setId(1L);
    postDto.setTitle("title");
    postDto.setContent("content");
    postDto.setDescription("description");
    postDto.setCategoryId(category.getId());
    
    // Expected data
    Post post = new Post();
    post.setId(1L);
    post.setTitle("title");
    post.setContent("content");
    post.setDescription("description");
    post.setCategory(category);
    
    when(modelMapper.map(postDto, Post.class)).thenReturn(post);
    when(postRepository.save(post)).thenReturn(post);
    when(modelMapper.map(post, PostDto.class)).thenReturn(postDto);
    when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
    
    // Call the service
    PostDto savedPostDto = postService.updatePost(postDto.getId(), postDto);
    
    assertNotNull(savedPostDto);
    assertEquals(postDto.getTitle(), savedPostDto.getTitle());
    assertEquals(postDto.getContent(), savedPostDto.getContent());
    assertEquals(postDto.getDescription(), savedPostDto.getDescription());
  }
  
  @Test
  public void deletePost() {
    Post post = new Post();
    post.setId(1L);
    post.setTitle("title");
    post.setContent("content");
    post.setDescription("description");
    
    when(postRepository.findById(idCaptor.capture())).thenReturn(Optional.of(post));
    doNothing().when(postRepository).delete(post);
    
    postService.deletePost(1L);
    
    assertEquals(1L, idCaptor.getValue());
  }
}
