package de.rieckpil.courses.book.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/** rieckpil
 * Code coverage addicts may be happy that Diffblue creates tests
 * for their getter/setter but I personally don't add test unless there's
 * some business logic in them (which shouldn't be there in general).
 */
class BookSynchronizationDiffblueTest {
  /**
  * Methods under test:
  *
  * <ul>
  *   <li>{@link BookSynchronization#BookSynchronization(String)}
  *   <li>{@link BookSynchronization#setIsbn(String)}
  *   <li>{@link BookSynchronization#toString()}
  *   <li>{@link BookSynchronization#getIsbn()}
  * </ul>
  */
  @Test
  void testConstructor() {
    // Arrange and Act
    BookSynchronization actualBookSynchronization = new BookSynchronization("Isbn");
    actualBookSynchronization.setIsbn("Isbn");
    String actualToStringResult = actualBookSynchronization.toString();

    // Assert that nothing has changed
    assertEquals("Isbn", actualBookSynchronization.getIsbn());
    assertEquals("BookUpdate{isbn='Isbn'}", actualToStringResult);
  }
}

