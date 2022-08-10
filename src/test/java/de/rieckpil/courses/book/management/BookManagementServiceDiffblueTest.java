package de.rieckpil.courses.book.management;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** rieckpil
 * No need to involve Spring for this unit test.
 * Using just JUnit and Mockito would be enough. There's a small
 * overhead in launching a ApplicationContext for this test.
 *
 * The bigger issue may be that we end up in so many ApplicationContexts for our tests
 * that the caching my evict other bigger contexts. See more info for the caching here
 * https://rieckpil.de/improve-build-times-with-context-caching-from-spring-test/
 */
@ContextConfiguration(classes = {BookManagementService.class})
@ExtendWith(SpringExtension.class)
class BookManagementServiceDiffblueTest {
  @Autowired
  private BookManagementService bookManagementService;

  @MockBean
  private BookRepository bookRepository;
  /**
  * Method under test: {@link BookManagementService#getAllBooks()}
  */
  @Test
  void testGetAllBooks() {
    // Arrange
    ArrayList<Book> bookList = new ArrayList<>();
    when(bookRepository.findAll()).thenReturn(bookList);

    // Act
    List<Book> actualAllBooks = bookManagementService.getAllBooks();

    // Assert
    assertSame(bookList, actualAllBooks);
    assertTrue(actualAllBooks.isEmpty());
    verify(bookRepository).findAll();
  }
}

