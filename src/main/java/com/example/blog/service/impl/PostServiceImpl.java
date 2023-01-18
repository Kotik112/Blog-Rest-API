package com.example.blog.service.impl;

import com.example.blog.entity.Post;
import com.example.blog.payload.PostDto;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //Convert DTO to Entity
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        return mapToDto(newPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : posts) {
            postDtoList.add(mapToDto(post));
        }

        // return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        return postDtoList;
    }

    @Override
    public PostDto getPostById(@PathVariable(name = "id") Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public String deletePost(Long id) {
        postRepository.deleteById(id);
        return "Post deleted successfully";
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

    public Post mapToEntity(PostDto postDto) {
        // Convert DTO to Entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

}
