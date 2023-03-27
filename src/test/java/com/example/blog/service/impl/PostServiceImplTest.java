/*package com.example.blog.service.impl;

import com.example.blog.entity.Post;
import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;
import com.example.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PostServiceImplTest {

    private PostServiceImpl postServiceImplUnderTest;
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        postServiceImplUnderTest = new PostServiceImpl(postRepository);
    }

    public PostDto mapToDto(Post post) {
        // Convert Entity to DTO
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    @Test
    void testCreatePost() {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Title");
        postDto.setDescription("Test Description");
        postDto.setContent("Test Content");

        // Convert DTO to Entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        when(postRepository.save(any(Post.class))).thenReturn(post);

        // Act
        PostDto result = postServiceImplUnderTest.createPost(postDto);

        // Assert
        assertEquals(result.getTitle(), postDto.getTitle());
        assertEquals(result.getDescription(), postDto.getDescription());
        assertEquals(result.getContent(), postDto.getContent());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testGetAllPosts() {
        // Arrange
        Post post1 = new Post();
        post1.setId(0L);
        post1.setTitle("Test Title");
        post1.setDescription("Test Description");
        post1.setContent("Test Content");

        Post post2 = new Post();
        post2.setId(1L);
        post2.setTitle("Test Title 2");
        post2.setDescription("Test Description 2");
        post2.setContent("Test Content 2");


        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);
        posts.stream().map(this::mapToDto).toList();

        when(postRepository.findAll()).thenReturn(posts);

        // Act
        PostResponse result = postServiceImplUnderTest.getAllPosts(0, 10);

        // Assert
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getTitle(), post1.getTitle());
        assertEquals(result.get(0).getDescription(), post1.getDescription());
        assertEquals(result.get(0).getContent(), post1.getContent());

        assertEquals(result.get(1).getTitle(), post2.getTitle());
        assertEquals(result.get(1).getDescription(), post2.getDescription());
        assertEquals(result.get(1).getDescription(), post2.getDescription());
        assertEquals(result.get(1).getContent(), post2.getContent());

        verify(postRepository, times(1)).findAll();
    }

}
*/
