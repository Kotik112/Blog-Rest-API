package com.example.blog.service.impl;

import static com.example.blog.utils.AppConstants.COMMENT_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.exception.BlogAPIException;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.CommentDto;
import com.example.blog.repository.CommentRespository;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.CommentService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
  private static final String COMMENT = "Comment";
  private final CommentRespository commentRespository;
  private final PostRepository postRepository;
  private final ModelMapper mapper;

  public CommentServiceImpl(
      CommentRespository commentRespository,
      PostRepository postRepository,
      ModelMapper modelMapper) {
    this.commentRespository = commentRespository;
    this.postRepository = postRepository;
    this.mapper = modelMapper;
  }

  @Override
  public CommentDto createComment(long id, CommentDto commentDto) {
    Comment comment = mapToEntity(commentDto);
    Post post = findPostById(id);
    comment.setPost(post);
    Comment newComment = commentRespository.save(comment);
    return mapToDto(newComment);
  }

  @Override
  public List<CommentDto> getCommentByPostId(long id) {
    List<Comment> commentList = commentRespository.findByPostId(id);
    return commentList.stream().map(this::mapToDto).toList();
  }

  @Override
  public CommentDto getCommentById(long postId, long commentId) {
    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    Comment comment =
        commentRespository
            .findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException(COMMENT, "id", commentId));

    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogAPIException(BAD_REQUEST, COMMENT_NOT_FOUND);
    }

    return mapToDto(comment);
  }

  @Override
  public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
    Post post = findPostById(postId);
    Comment comment =
        commentRespository
            .findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException(COMMENT, "id", commentId));
    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogAPIException(BAD_REQUEST, COMMENT_NOT_FOUND);
    }

    comment.setName(commentDto.getName());
    comment.setEmail(commentDto.getEmail());
    comment.setBody(commentDto.getBody());

    Comment updatedComment = commentRespository.save(comment);
    return mapToDto(updatedComment);
  }

  @Override
  public void deleteComment(long postId, long commentId) {
    Post post = findPostById(postId);
    Comment comment =
        commentRespository
            .findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException(COMMENT, "id", commentId));
    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogAPIException(BAD_REQUEST, COMMENT_NOT_FOUND);
    }
    commentRespository.deleteById(commentId);
  }

  public CommentDto mapToDto(Comment comment) {
    return mapper.map(comment, CommentDto.class);
  }

  public Comment mapToEntity(CommentDto commentDto) {
    return mapper.map(commentDto, Comment.class);
  }

  private Post findPostById(long postId) {
    return postRepository
        .findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
  }
}
