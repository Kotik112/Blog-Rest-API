package com.example.blog.service;

import com.example.blog.payload.CommentDto;


import java.util.List;


public interface CommentService {

    CommentDto createComment(long id, CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long id);

    CommentDto getCommentById(long postId, long commentId);

    //public String updateComment();

    //public String deleteComment();
}
