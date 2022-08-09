package de.rieckpil.courses.book.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import de.rieckpil.courses.book.management.Book;
import de.rieckpil.courses.book.management.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReviewDiffblueTest {
  /**
  * Methods under test: 
  * 
  * <ul>
  *   <li>default or parameterless constructor of {@link Review}
  *   <li>{@link Review#setBook(Book)}
  *   <li>{@link Review#setContent(String)}
  *   <li>{@link Review#setCreatedAt(LocalDateTime)}
  *   <li>{@link Review#setId(Long)}
  *   <li>{@link Review#setRating(Integer)}
  *   <li>{@link Review#setTitle(String)}
  *   <li>{@link Review#setUser(User)}
  *   <li>{@link Review#toString()}
  *   <li>{@link Review#getBook()}
  *   <li>{@link Review#getContent()}
  *   <li>{@link Review#getCreatedAt()}
  *   <li>{@link Review#getId()}
  *   <li>{@link Review#getRating()}
  *   <li>{@link Review#getTitle()}
  *   <li>{@link Review#getUser()}
  * </ul>
  */
  @Test
  void testConstructor() {
    // Arrange and Act
    Review actualReview = new Review();
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
    actualReview.setBook(book);
    actualReview.setContent("Not all who wander are lost");
    LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
    actualReview.setCreatedAt(ofResult);
    actualReview.setId(123L);
    actualReview.setRating(1);
    actualReview.setTitle("Dr");
    User user = new User();
    user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user.setEmail("jane.doe@example.org");
    user.setId(123L);
    user.setName("Name");
    actualReview.setUser(user);
    actualReview.toString();

    // Assert
    assertSame(book, actualReview.getBook());
    assertEquals("Not all who wander are lost", actualReview.getContent());
    assertSame(ofResult, actualReview.getCreatedAt());
    assertEquals(123L, actualReview.getId().longValue());
    assertEquals(1, actualReview.getRating().intValue());
    assertEquals("Dr", actualReview.getTitle());
    assertSame(user, actualReview.getUser());
  }
}

