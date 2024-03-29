package com.example.blog.repository;

import com.example.blog.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findPostsByCategoryId(Long categoryId);
}
