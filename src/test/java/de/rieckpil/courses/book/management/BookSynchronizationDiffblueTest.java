package de.rieckpil.courses.book.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

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

