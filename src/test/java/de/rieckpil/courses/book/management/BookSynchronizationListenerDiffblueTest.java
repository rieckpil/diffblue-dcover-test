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

/** rieckpil
 * This test verifies an Amazon SQS listener (message queue from AWS). There's
 * some value to unit test this but there would be much more benefit in a sliced test
 * with an actual queue (-> LocalStack & Testcontainers) to also cover the serialization.
 *
 * I can see that that's hard to autogenerate. Nevertheless, the listener method has still some
 * meaningful business logic of early retruns when the payload doesn't match the specifications.
 * Diffblue could have generated tests that verify that we're not saving the incoming payload to
 * the database by e.g. verifying the interaction with our repository mock.
 *
 */
@ContextConfiguration(classes = {BookSynchronizationListener.class})
@ExtendWith(SpringExtension.class)
class BookSynchronizationListenerDiffblueTest {
  @MockBean
  private BookRepository bookRepository;

  @Autowired
  private BookSynchronizationListener bookSynchronizationListener;

  @MockBean
  private OpenLibraryApiClient openLibraryApiClient;

  /** rieckpil
   * As far as I read this test, it verifies that the mutable BookSynchronization
   * object that gets passed as a method argument isn't changed during the invocation
   * of the class under test.
   *
   * Not sure if that's worth a test. But at least it now gave me the inpsiration
   * to work with an immutable object field here.
   */

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

  /** rieckpil
   * This seems like a quite useless test.
   * We're mocking the method argument and then verify that
   * the getter is called.
   *
   * That couples our test quite to the implementation and doesn't verify
   * any business relevant operation
   */

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

