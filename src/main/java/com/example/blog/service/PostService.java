package com.example.blog.service;

import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int page, int size, String sortBy, String sortDirection);
    
    List<PostDto> getPostsByCategoryId(Long categoryId);

    PostDto getPostById(Long id);

    PostDto updatePost(Long id, PostDto postDto);

    void deletePost(Long id);
}
