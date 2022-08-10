package de.rieckpil.courses.book.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/** rieckpil
 * Also here it's a matter of taste whether we need a unit test for
 * an exception.
 *
 * As this class uses @ResponseStatus to map to an HTTP response code, it would
 * be great if that had been covered in a controller test by throwing this
 * exception from the service class and expecting the HTTP response code.
 */
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

