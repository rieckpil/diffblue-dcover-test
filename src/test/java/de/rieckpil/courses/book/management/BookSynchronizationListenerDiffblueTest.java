package de.rieckpil.courses.book.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BookSynchronizationListener.class})
@ExtendWith(SpringExtension.class)
class BookSynchronizationListenerDiffblueTest {
  @MockBean
  private BookRepository bookRepository;

  @Autowired
  private BookSynchronizationListener bookSynchronizationListener;

  @MockBean
  private OpenLibraryApiClient openLibraryApiClient;
  /**
  * Method under test: {@link BookSynchronizationListener#consumeBookUpdates(BookSynchronization)}
  */
  @Test
  void testConsumeBookUpdates() {
    // Arrange
    BookSynchronization bookSynchronization = new BookSynchronization("Isbn");

    // Act
    bookSynchronizationListener.consumeBookUpdates(bookSynchronization);

    // Assert that nothing has changed
    assertEquals("Isbn", bookSynchronization.getIsbn());
  }

  /**
   * Method under test: {@link BookSynchronizationListener#consumeBookUpdates(BookSynchronization)}
   */
  @Test
  void testConsumeBookUpdates2() {
    // Arrange
    BookSynchronization bookSynchronization = mock(BookSynchronization.class);
    when(bookSynchronization.getIsbn()).thenReturn("Isbn");

    // Act
    bookSynchronizationListener.consumeBookUpdates(bookSynchronization);

    // Assert that nothing has changed
    verify(bookSynchronization).getIsbn();
  }
}

