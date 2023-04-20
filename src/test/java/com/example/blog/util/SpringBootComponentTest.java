package com.example.blog.util;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_CLASS)
// @Sql("/truncate_tables.sql")
public abstract class SpringBootComponentTest {
  public static final String API_V1 = "/api/v1";
  @Autowired protected ObjectMapper objectMapper;

  protected <T> T getFromResult(MvcResult result, Class<T> clazz) {
    try {
      return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    } catch (JsonProcessingException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  //  protected <T> List<T> getFromListResult(MvcResult result, Class<T> clazz) {
  //    try {
  //      return objectMapper
  //          .readerForListOf(clazz)
  //          .readValue(result.getResponse().getContentAsString());
  //    } catch (JsonProcessingException | UnsupportedEncodingException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }
}
