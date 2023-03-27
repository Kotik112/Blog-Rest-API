package com.example.blog.repository;

import com.example.blog.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRespository extends JpaRepository<Comment, Long> {
  List<Comment> findByPostId(Long postId);
}
