package com.example.blog.service;

import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int page, int size, String sortBy, String sortDirection);

    PostDto getPostById(Long id);

    PostDto updatePost(Long id, PostDto postDto);

    String deletePost(Long id);
}
