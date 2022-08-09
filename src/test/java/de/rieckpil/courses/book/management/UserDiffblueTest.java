package de.rieckpil.courses.book.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class UserDiffblueTest {
  /**
  * Methods under test: 
  * 
  * <ul>
  *   <li>default or parameterless constructor of {@link User}
  *   <li>{@link User#setCreatedAt(LocalDateTime)}
  *   <li>{@link User#setEmail(String)}
  *   <li>{@link User#setId(Long)}
  *   <li>{@link User#setName(String)}
  *   <li>{@link User#getCreatedAt()}
  *   <li>{@link User#getEmail()}
  *   <li>{@link User#getId()}
  *   <li>{@link User#getName()}
  * </ul>
  */
  @Test
  void testConstructor() {
    // Arrange and Act
    User actualUser = new User();
    LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
    actualUser.setCreatedAt(ofResult);
    actualUser.setEmail("jane.doe@example.org");
    actualUser.setId(123L);
    actualUser.setName("Name");

    // Assert
    assertSame(ofResult, actualUser.getCreatedAt());
    assertEquals("jane.doe@example.org", actualUser.getEmail());
    assertEquals(123L, actualUser.getId().longValue());
    assertEquals("Name", actualUser.getName());
  }
}

