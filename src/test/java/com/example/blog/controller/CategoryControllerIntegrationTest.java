package com.example.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.blog.payload.CategoryDto;
import com.example.blog.util.SpringBootComponentTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
public class CategoryControllerIntegrationTest extends SpringBootComponentTest {
  @Autowired private MockMvc mvc;
  @Autowired private ObjectMapper mapper;

  @Test
  @WithMockUser(roles = "ADMIN")
  public void when_addCategory_then_returnCategory() throws Exception {
    CategoryDto categoryDto = createCategory();
    CategoryDto expectedCategoryDto = new CategoryDto();
    expectedCategoryDto.setId(categoryDto.getId());
    expectedCategoryDto.setName("name");
    expectedCategoryDto.setDescription("description");

    // then
    assertEquals(expectedCategoryDto, categoryDto);
  }

  private CategoryDto createCategory() throws Exception {
    CategoryDto categoryDto = new CategoryDto();
    categoryDto.setId(1L);
    categoryDto.setName("name");
    categoryDto.setDescription("description");

    MvcResult result =
        mvc.perform(
                post(API_V1 + "/categories")
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(categoryDto)))
            .andExpect(status().isCreated())
            .andReturn();

    return getFromResult(result, CategoryDto.class);
  }
}
