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

/** rieckpil
 * Similar to the other controllers, I would favor @WebMvcTest
 * here.
 *
 * Reason see comment of BookControllerDiffblueTest
 *
 */
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
    /** rieckpil
     * Not sure why we're testing the 405 here -> method not allowed.
     * We should verify a successful outcome of 201 and e.g. the presence of the Location header
     *
     * I assume the AI couldn't figure out how to pass the JwtAuthenticationToken
     * that this controller expects.
     *
     * This could have been solved with @WebMvcTest & Spring Security Test by associating
     * an actual logged-in user
     */
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
      /** rieckpil
       * I would personally prefer to inline the request in the already fluent style of MockMvc.
       *
       * Right now I also have to move my eyes up to understand what request is tested.
       *
       * I see no big benefit of splitting the object declaration and its use here.
       */
      .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
      /** rieckpil
       * It seems that Diffblue _always_ goes the easy testing route
       * and stubs for an empty response.
       *
       * However, this can hide future bugs where we e.g. rename the field of the returned object
       * and break our API contract.
       * I usually use the controller tests to also include a light version of a contract test
       * by returning an object or list of objects and asserting the fields
       * with MockMvc's jsonPath integration.
       */
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
    /** rieckpil
     * Small nitpick: anyString() and anyLong() are also valid
     * and won't produce the "This is redundant info" within IntelliJ IDEA
     *
     */
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

  /** rieckpil
   * The HTTP delete endpoint is protected with additional authorization.
   * See the implementation and @PreAuthorize.
   *
   * Using @WebMvcTest here with the Spring Security integration would have
   * been better to test both scenarios: Get a 403 when the user misses the roles.
   * Get a 200 when an ADMIN calls this endpoint.
   *
   * See the upcoming blog article I've written for the Diffblue blog for more info
   * on the MockMvc & Spring Security integration.
   */

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

  /** rieckpil
   * Not sure why we see here again a second test with the additional Encoding
   */

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

