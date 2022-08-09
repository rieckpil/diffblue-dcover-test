package de.rieckpil.courses.book.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class BookDiffblueTest {
  /**
  * Methods under test:
  *
  * <ul>
  *   <li>default or parameterless constructor of {@link Book}
  *   <li>{@link Book#setAuthor(String)}
  *   <li>{@link Book#setDescription(String)}
  *   <li>{@link Book#setGenre(String)}
  *   <li>{@link Book#setId(Long)}
  *   <li>{@link Book#setIsbn(String)}
  *   <li>{@link Book#setPages(Long)}
  *   <li>{@link Book#setPublisher(String)}
  *   <li>{@link Book#setThumbnailUrl(String)}
  *   <li>{@link Book#setTitle(String)}
  *   <li>{@link Book#toString()}
  *   <li>{@link Book#getAuthor()}
  *   <li>{@link Book#getDescription()}
  *   <li>{@link Book#getGenre()}
  *   <li>{@link Book#getId()}
  *   <li>{@link Book#getIsbn()}
  *   <li>{@link Book#getPages()}
  *   <li>{@link Book#getPublisher()}
  *   <li>{@link Book#getThumbnailUrl()}
  *   <li>{@link Book#getTitle()}
  * </ul>
  */
  @Test
  void testConstructor() {
    // Arrange and Act
    Book actualBook = new Book();
    actualBook.setAuthor("JaneDoe");
    actualBook.setDescription("The characteristics of someone or something");
    actualBook.setGenre("Genre");
    actualBook.setId(123L);
    actualBook.setIsbn("Isbn");
    actualBook.setPages(1L);
    actualBook.setPublisher("Publisher");
    actualBook.setThumbnailUrl("https://example.org/example");
    actualBook.setTitle("Dr");
    String actualToStringResult = actualBook.toString();

    // Assert
    assertEquals("JaneDoe", actualBook.getAuthor());
    assertEquals("The characteristics of someone or something", actualBook.getDescription());
    assertEquals("Genre", actualBook.getGenre());
    assertEquals(123L, actualBook.getId().longValue());
    assertEquals("Isbn", actualBook.getIsbn());
    assertEquals(1L, actualBook.getPages().longValue());
    assertEquals("Publisher", actualBook.getPublisher());
    assertEquals("https://example.org/example", actualBook.getThumbnailUrl());
    assertEquals("Dr", actualBook.getTitle());
    assertEquals("Book{id=123, title='Dr', isbn='Isbn', author='JaneDoe', genre='Genre', thumbnailUrl='https://example"
        + ".org/example', description='The characteristics of someone or something', publisher='Publisher',"
        + " pages=1}", actualToStringResult);
  }

  /**
   * Method under test: {@link Book#equals(Object)}
   */
  @Test
  void testEquals() {
    // Arrange
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(123L);
    book.setIsbn("Isbn");
    book.setPages(1L);
    book.setPublisher("Publisher");
    book.setThumbnailUrl("https://example.org/example");
    book.setTitle("Dr");

    // Act and Assert
    assertNotEquals(book, null);
  }

  /**
   * Method under test: {@link Book#equals(Object)}
   */
  @Test
  void testEquals2() {
    // Arrange
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(123L);
    book.setIsbn("Isbn");
    book.setPages(1L);
    book.setPublisher("Publisher");
    book.setThumbnailUrl("https://example.org/example");
    book.setTitle("Dr");

    // Act and Assert
    assertNotEquals(book, "Different type to Book");
  }

  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link Book#equals(Object)}
   *   <li>{@link Book#hashCode()}
   * </ul>
   */
  @Test
  void testEquals3() {
    // Arrange
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(123L);
    book.setIsbn("Isbn");
    book.setPages(1L);
    book.setPublisher("Publisher");
    book.setThumbnailUrl("https://example.org/example");
    book.setTitle("Dr");

    // Act and Assert
    assertEquals(book, book);
    int expectedHashCodeResult = book.hashCode();
    assertEquals(expectedHashCodeResult, book.hashCode());
  }

  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link Book#equals(Object)}
   *   <li>{@link Book#hashCode()}
   * </ul>
   */
  @Test
  void testEquals4() {
    // Arrange
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(123L);
    book.setIsbn("Isbn");
    book.setPages(1L);
    book.setPublisher("Publisher");
    book.setThumbnailUrl("https://example.org/example");
    book.setTitle("Dr");

    Book book1 = new Book();
    book1.setAuthor("JaneDoe");
    book1.setDescription("The characteristics of someone or something");
    book1.setGenre("Genre");
    book1.setId(123L);
    book1.setIsbn("Isbn");
    book1.setPages(1L);
    book1.setPublisher("Publisher");
    book1.setThumbnailUrl("https://example.org/example");
    book1.setTitle("Dr");

    // Act and Assert
    assertEquals(book, book1);
    int expectedHashCodeResult = book.hashCode();
    assertEquals(expectedHashCodeResult, book1.hashCode());
  }

  /**
   * Method under test: {@link Book#equals(Object)}
   */
  @Test
  void testEquals5() {
    // Arrange
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("Genre");
    book.setId(123L);
    book.setIsbn("Book{id=");
    book.setPages(1L);
    book.setPublisher("Publisher");
    book.setThumbnailUrl("https://example.org/example");
    book.setTitle("Dr");

    Book book1 = new Book();
    book1.setAuthor("JaneDoe");
    book1.setDescription("The characteristics of someone or something");
    book1.setGenre("Genre");
    book1.setId(123L);
    book1.setIsbn("Isbn");
    book1.setPages(1L);
    book1.setPublisher("Publisher");
    book1.setThumbnailUrl("https://example.org/example");
    book1.setTitle("Dr");

    // Act and Assert
    assertNotEquals(book, book1);
  }
}

