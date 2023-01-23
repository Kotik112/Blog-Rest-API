package com.example.blog.service.impl;

import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.exception.BlogAPIException;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.CommentDto;
import com.example.blog.payload.PostDto;
import com.example.blog.repository.CommentRespository;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRespository commentRespository;
    private final PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRespository commentRespository,
                              PostRepository postRepository,
                              ModelMapper modelMapper
    ) {
        this.commentRespository = commentRespository;
        this.postRepository = postRepository;
        this.mapper = modelMapper;
    }

    @Override
    public CommentDto createComment(
            long id,
            CommentDto commentDto
    ) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id));
        //Set post to comment
        comment.setPost(post);

        Comment newComment = commentRespository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long id) {
        List<Comment> commentList = commentRespository.findByPostId(id);
        return commentList.stream().map(comment -> mapToDto(comment)).toList();
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRespository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the given post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRespository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the given post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRespository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRespository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the given post");
        }
        commentRespository.deleteById(commentId);
    }

    public CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return commentDto;
    }

    public Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}
