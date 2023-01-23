package com.example.blog.controller;

import com.example.blog.payload.CommentDto;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;


    public CommentController(CommentService commentService,
                             PostRepository postRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable("postId") long postId) {
        return new ResponseEntity<>(commentService.getCommentByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long commentId
    ) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }
}
