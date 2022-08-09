package de.rieckpil.courses.book.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class ReviewNotFoundExceptionDiffblueTest {
  /**
  * Method under test: default or parameterless constructor of {@link ReviewNotFoundException}
  */
  @Test
  void testConstructor() {
    // Arrange and Act
    ReviewNotFoundException actualReviewNotFoundException = new ReviewNotFoundException();

    // Assert
    assertNull(actualReviewNotFoundException.getCause());
    assertEquals(0, actualReviewNotFoundException.getSuppressed().length);
    assertNull(actualReviewNotFoundException.getMessage());
    assertNull(actualReviewNotFoundException.getLocalizedMessage());
  }
}

