package com.example.blog.controller;

import com.example.blog.payload.CommentDto;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST APIs for Comment Resource")
public class CommentController {

  private final CommentService commentService;
  private final PostRepository postRepository;

  public CommentController(CommentService commentService, PostRepository postRepository) {
    this.commentService = commentService;
    this.postRepository = postRepository;
  }

  @PostMapping("/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(
      @PathVariable("postId") long postId, @Valid @RequestBody CommentDto commentDto) {
    return new ResponseEntity<>(
        commentService.createComment(postId, commentDto), HttpStatus.CREATED);
  }

  @GetMapping("/{postId}/comments")
  public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(
      @PathVariable("postId") long postId) {
    return new ResponseEntity<>(commentService.getCommentByPostId(postId), HttpStatus.OK);
  }

  @GetMapping("/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> getCommentById(
      @PathVariable(value = "postId") long postId, @PathVariable(value = "id") long commentId) {
    return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
  }

  @PatchMapping("/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> updateComment(
      @PathVariable(value = "postId") long postId,
      @PathVariable(value = "id") long commentId,
      @Valid @RequestBody CommentDto commentDto) {
    CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
    return new ResponseEntity<>(updatedComment, HttpStatus.CREATED);
  }

  @DeleteMapping("/{postId}/comments/{id}")
  public ResponseEntity<String> deleteComment(
      @PathVariable("postId") long postId, @PathVariable("id") long commentId) {
    commentService.deleteComment(postId, commentId);
    return new ResponseEntity<>("Success", HttpStatus.OK);
  }
}
