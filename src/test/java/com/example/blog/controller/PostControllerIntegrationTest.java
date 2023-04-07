package com.example.blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.blog.payload.PostDto;
import com.example.blog.repository.PostRepository;
import com.example.blog.security.JwtAuthenticationFilter;
import com.example.blog.util.SpringBootComponentTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
public class PostControllerIntegrationTest extends SpringBootComponentTest {
  @Autowired MockMvc mvc;
  @Autowired PostRepository postRepository;
  @Autowired ObjectMapper mapper;
  @Autowired JwtAuthenticationFilter filter;

  //  @Test
  //  public void when_addPost_then_returnPost() throws Exception {
  //    PostDto postDto = createPost();
  //    PostDto expectedPostDto = new PostDto();
  //    expectedPostDto.setId(postDto.getId());
  //    expectedPostDto.setTitle("title");
  //    expectedPostDto.setContent("content");
  //    expectedPostDto.setDescription("description");
  //
  //    // then
  //    assertEquals(expectedPostDto, postDto);
  //
  //  }

  private PostDto createPost() throws Exception {
    PostDto postDto = new PostDto();
    postDto.setTitle("title");
    postDto.setContent("content");
    postDto.setDescription("description");
    postDto.setCategoryId(1L);

    MvcResult result =
        mvc.perform(
                post("/api/v1/posts")
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(postDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("title"))
            .andExpect(jsonPath("$.content").value("content"))
            .andExpect(jsonPath("$.description").value("description"))
            .andReturn();

    return getFromResult(result, PostDto.class);
  }
}
