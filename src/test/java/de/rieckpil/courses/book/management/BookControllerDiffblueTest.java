package de.rieckpil.courses.book.management;

import static org.mockito.Mockito.when;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/** rieckpil
 * While this minimal test setup works, I always favor the sliced
 * test annotation @WebMvcTest as this will also include the Spring Security config,
 * any @ControllerAdvices, filters, converters, etc. that let me test the controller
 * endpoints more closer to how they are invoked during runtime.
 *
 * It's good that Diffblue still uses MockMvc here and doesn't fallback do plain
 * unit tests with just JUnit & Mockito, as there's little value.
 */
@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
class BookControllerDiffblueTest {
  @Autowired
  private BookController bookController;

  @MockBean
  private BookManagementService bookManagementService;

  /** rieckpil
   * I see this comment as redundant and would not generate it. I personally prefer meaningful
   * test method names that let me derive the tested business functionality,
   * e.g. shouldRejectPayloadWhenManadatoryUserIdIsMissing
   *
   * I know that's hard to produce with an AI.
   */

  /**
  * Method under test: {@link BookController#getAvailableBooks()}
  */
  @Test
  void testGetAvailableBooks() throws Exception {
    // Arrange
    when(bookManagementService.getAllBooks()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(bookController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
      /** rieckpil
       * Minor nitpick: You could favor static imports to shorten the test from
       * MockMvcResultMatchers.content() to content()
       */
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * rieckpil
   * repeating the first test method by just appending a '2' is not optimal.
   * Three weeks after this test, nobody may understand what this test actually verifies.
   * See my comment above for meaningful test methods.
   *
   * I'm not sure how this test differs from the first. It just sets the encoding.
   * This may be a redundant test
   */

  /**
   * Method under test: {@link BookController#getAvailableBooks()}
   */
  @Test
  void testGetAvailableBooks2() throws Exception {
    // Arrange
    when(bookManagementService.getAllBooks()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/books");
    getResult.characterEncoding("Encoding");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(bookController)
        .build()
        .perform(getResult)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }
}

