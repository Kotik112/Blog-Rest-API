package com.example.blog.controller;

import static com.example.blog.utils.AppConstants.BASE_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.blog.payload.CommentDto;
import com.example.blog.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BASE_URL)
@Tag(name = "CRUD REST APIs for Comment Resource")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping("/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(
      @PathVariable("postId") long postId, @Valid @RequestBody CommentDto commentDto) {
    return new ResponseEntity<>(commentService.createComment(postId, commentDto), CREATED);
  }

  @GetMapping("/{postId}/comments")
  public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(
      @PathVariable("postId") long postId) {
    return new ResponseEntity<>(commentService.getCommentByPostId(postId), OK);
  }

  @GetMapping("/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> getCommentById(
      @PathVariable(value = "postId") long postId, @PathVariable(value = "id") long commentId) {
    return new ResponseEntity<>(commentService.getCommentById(postId, commentId), OK);
  }

  @PatchMapping("/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> updateComment(
      @PathVariable(value = "postId") long postId,
      @PathVariable(value = "id") long commentId,
      @Valid @RequestBody CommentDto commentDto) {
    CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
    return new ResponseEntity<>(updatedComment, CREATED);
  }

  @DeleteMapping("/{postId}/comments/{id}")
  public ResponseEntity<String> deleteComment(
      @PathVariable("postId") long postId, @PathVariable("id") long commentId) {
    commentService.deleteComment(postId, commentId);
    return new ResponseEntity<>("Success", OK);
  }
}
