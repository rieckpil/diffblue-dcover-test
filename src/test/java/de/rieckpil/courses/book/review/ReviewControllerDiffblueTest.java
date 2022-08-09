package de.rieckpil.courses.book.review;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

@ContextConfiguration(classes = {ReviewController.class})
@ExtendWith(SpringExtension.class)
class ReviewControllerDiffblueTest {
  @Autowired
  private ReviewController reviewController;

  @MockBean
  private ReviewService reviewService;
  /**
  * Method under test: {@link ReviewController#createBookReview(String, BookReviewRequest, JwtAuthenticationToken, UriComponentsBuilder)}
  */
  @Test
  void testCreateBookReview() throws Exception {
    // Arrange
    MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
        .post("/api/books/{isbn}/reviews", "", "Uri Vars")
        .contentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    MockHttpServletRequestBuilder requestBuilder = contentTypeResult
        .content(objectMapper.writeValueAsString(new BookReviewRequest("Dr", "Not all who wander are lost", 1)));

    // Act
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(reviewController)
        .build()
        .perform(requestBuilder);

    // Assert
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
  }

  /**
   * Method under test: {@link ReviewController#getAllReviews(Integer, String)}
   */
  @Test
  void testGetAllReviews() throws Exception {
    // Arrange
    when(reviewService.getAllReviews((Integer) any(), (String) any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get("/api/books/reviews")
        .param("orderBy", "foo");
    MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(reviewController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link ReviewController#getReviewStatistics()}
   */
  @Test
  void testGetReviewStatistics() throws Exception {
    // Arrange
    when(reviewService.getReviewStatistics()).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books/reviews/statistics");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(reviewController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link ReviewController#getReviewById(String, Long)}
   */
  @Test
  void testGetReviewById() throws Exception {
    // Arrange
    when(reviewService.getReviewById((String) any(), (Long) any()))
        .thenReturn(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books/{isbn}/reviews/{reviewId}",
        "Isbn", 123L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(reviewController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("{}"));
  }

  /**
   * Method under test: {@link ReviewController#deleteBookReview(String, Long)}
   */
  @Test
  void testDeleteBookReview() throws Exception {
    // Arrange
    doNothing().when(reviewService).deleteReview((String) any(), (Long) any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/books/{isbn}/reviews/{reviewId}",
        "Isbn", 123L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(reviewController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * Method under test: {@link ReviewController#deleteBookReview(String, Long)}
   */
  @Test
  void testDeleteBookReview2() throws Exception {
    // Arrange
    doNothing().when(reviewService).deleteReview((String) any(), (Long) any());
    MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/books/{isbn}/reviews/{reviewId}",
        "Isbn", 123L);
    deleteResult.characterEncoding("Encoding");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(reviewController)
        .build()
        .perform(deleteResult)
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}

