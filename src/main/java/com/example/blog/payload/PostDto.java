package com.example.blog.payload;


import com.example.blog.entity.Category;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "Title must be at least 2 characters long")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;
    @NotEmpty
    private String content;
    private Long categoryId;
    private Set<CommentDto> comments;
}
