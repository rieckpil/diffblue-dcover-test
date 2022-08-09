package de.rieckpil.courses.book.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BookReviewRequestDiffblueTest {
  /**
  * Methods under test: 
  * 
  * <ul>
  *   <li>{@link BookReviewRequest#BookReviewRequest(String, String, Integer)}
  *   <li>{@link BookReviewRequest#setRating(Integer)}
  *   <li>{@link BookReviewRequest#setReviewContent(String)}
  *   <li>{@link BookReviewRequest#setReviewTitle(String)}
  *   <li>{@link BookReviewRequest#getRating()}
  *   <li>{@link BookReviewRequest#getReviewContent()}
  *   <li>{@link BookReviewRequest#getReviewTitle()}
  * </ul>
  */
  @Test
  void testConstructor() {
    // Arrange and Act
    BookReviewRequest actualBookReviewRequest = new BookReviewRequest("Dr", "Not all who wander are lost", 1);
    actualBookReviewRequest.setRating(1);
    actualBookReviewRequest.setReviewContent("Not all who wander are lost");
    actualBookReviewRequest.setReviewTitle("Dr");

    // Assert
    assertEquals(1, actualBookReviewRequest.getRating().intValue());
    assertEquals("Not all who wander are lost", actualBookReviewRequest.getReviewContent());
    assertEquals("Dr", actualBookReviewRequest.getReviewTitle());
  }
}

