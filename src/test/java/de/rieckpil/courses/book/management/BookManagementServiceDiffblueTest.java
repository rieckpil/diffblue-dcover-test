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

