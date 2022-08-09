package de.rieckpil.courses.book.review;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReviewVerifier.class})
@ExtendWith(SpringExtension.class)
class ReviewVerifierDiffblueTest {
  @Autowired
  private ReviewVerifier reviewVerifier;
  /**
  * Method under test: {@link ReviewVerifier#doesMeetQualityStandards(String)}
  */
  @Test
  void testDoesMeetQualityStandards() {
    // Arrange, Act and Assert
    assertFalse(reviewVerifier.doesMeetQualityStandards("Review"));
    assertFalse(reviewVerifier.doesMeetQualityStandards("Lorem ipsum"));
    assertFalse(reviewVerifier.doesMeetQualityStandards(" "));
    assertFalse(reviewVerifier.doesMeetQualityStandards("I"));
    assertFalse(reviewVerifier.doesMeetQualityStandards(" I"));
  }
}

