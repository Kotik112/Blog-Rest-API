/*
TODO: Exception handling tests
 */

package com.example.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.blog.entity.Category;
import com.example.blog.payload.PostDto;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.util.SpringBootComponentTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
public class PostControllerIntegrationTest extends SpringBootComponentTest {
  @Autowired private MockMvc mvc;
  @Autowired private CategoryRepository categoryRepository;
  @Autowired private ObjectMapper mapper;

  //  @BeforeEach
  //  public void init() {
  //    System.out.println("KOTIK");
  //    Category category = new Category();
  //    category.setId(1L);
  //    category.setName("IT");
  //    category.setName("category");
  //    categoryRepository.save(category);
  //
  //    Category category2 = new Category();
  //    category2.setId(2L);
  //    category2.setName("Java");
  //    category2.setName("category");
  //    categoryRepository.save(category2);
  //  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void test_createPost() throws Exception {
    PostDto postDto = createPost();

    PostDto expectedPostDto = new PostDto();
    expectedPostDto.setId(postDto.getId());
    expectedPostDto.setTitle("title");
    expectedPostDto.setContent("content");
    expectedPostDto.setDescription("description");
    expectedPostDto.setCategoryId(1L);

    // then
    assertEquals(expectedPostDto, postDto);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void test_getPost() throws Exception {
    PostDto postDto = createPost();

    PostDto expectedPostDto = new PostDto();
    expectedPostDto.setId(postDto.getId());
    expectedPostDto.setTitle("title");
    expectedPostDto.setContent("content");
    expectedPostDto.setDescription("description");
    expectedPostDto.setCategoryId(1L);

    // then
    assertEquals(expectedPostDto, postDto);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void test_updatePost() throws Exception {
    PostDto postDto = createPost();

    PostDto newPostDto = new PostDto();
    newPostDto.setId(postDto.getId());
    newPostDto.setTitle("new title");
    newPostDto.setContent(" new content");
    newPostDto.setDescription("new description");
    newPostDto.setCategoryId(1L);

    PostDto expectedPostDto = new PostDto();
    expectedPostDto.setId(postDto.getId());
    expectedPostDto.setTitle("new title");
    expectedPostDto.setContent(" new content");
    expectedPostDto.setDescription("new description");
    expectedPostDto.setCategoryId(1L);
    expectedPostDto.setComments(Set.of());

    var result =
        mvc.perform(
                put(API_V1 + "/posts/{id}", postDto.getId())
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(newPostDto)))
            .andExpect(status().isOk())
            .andReturn();

    PostDto returnedPostDto = getFromResult(result, PostDto.class);

    // then
    assertEquals(expectedPostDto, returnedPostDto);
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void test_deletePost() throws Exception {
    PostDto postDto = createPost();

    var result =
        mvc.perform(delete(API_V1 + "/posts/{id}", postDto.getId()))
            .andExpect(status().isOk())
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(result.getResponse().getContentAsString(), "Post deleted successfully");
  }

  private PostDto createPost() throws Exception {
    // this should be in @BeforeEach, not working
    Category category = new Category();
    category.setId(1L);
    category.setName("IT");
    category.setName("category");
    categoryRepository.save(category);

    PostDto postDto = new PostDto();
    postDto.setId(1L);
    postDto.setTitle("title");
    postDto.setContent("content");
    postDto.setDescription("description");
    postDto.setCategoryId(1L);

    MvcResult result =
        mvc.perform(
                post(API_V1 + "/posts")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(postDto)))
            .andExpect(status().isCreated())
            .andReturn();

    return getFromResult(result, PostDto.class);
  }
}
