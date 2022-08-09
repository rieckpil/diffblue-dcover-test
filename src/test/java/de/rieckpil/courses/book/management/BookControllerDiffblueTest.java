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

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
class BookControllerDiffblueTest {
  @Autowired
  private BookController bookController;

  @MockBean
  private BookManagementService bookManagementService;
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
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

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

