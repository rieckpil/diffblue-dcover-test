package de.rieckpil.courses.book.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class BadReviewQualityExceptionDiffblueTest {
  /**
  * Method under test: {@link BadReviewQualityException#BadReviewQualityException(String)}
  */
  @Test
  void testConstructor() {
    // Arrange and Act
    BadReviewQualityException actualBadReviewQualityException = new BadReviewQualityException("An error occurred");

    // Assert
    assertNull(actualBadReviewQualityException.getCause());
    assertEquals(0, actualBadReviewQualityException.getSuppressed().length);
    assertEquals("An error occurred", actualBadReviewQualityException.getMessage());
    assertEquals("An error occurred", actualBadReviewQualityException.getLocalizedMessage());
  }
}

