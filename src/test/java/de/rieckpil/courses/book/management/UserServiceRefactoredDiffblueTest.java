package de.rieckpil.courses.book.management;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class UserServiceRefactoredDiffblueTest {
  /**
  * Method under test: {@link UserServiceRefactored#getOrCreateUser(String, String)}
  */
  @Test
  void testGetOrCreateUser() {
    // Arrange
    User user = new User();
    user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user.setEmail("jane.doe@example.org");
    user.setId(123L);
    user.setName("Name");

    User user1 = new User();
    user1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user1.setEmail("jane.doe@example.org");
    user1.setId(123L);
    user1.setName("Name");
    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.findByNameAndEmail((String) any(), (String) any())).thenReturn(user);
    when(userRepository.save((User) any())).thenReturn(user1);
    LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
    ZoneOffset ofTotalSecondsResult = ZoneOffset.ofTotalSeconds(1);

    // Act and Assert
    assertSame(user,
        (new UserServiceRefactored(userRepository,
            Clock.fixed(atStartOfDayResult.atZone(ofTotalSecondsResult).toInstant(), ofTotalSecondsResult)))
                .getOrCreateUser("Name", "jane.doe@example.org"));
    verify(userRepository).findByNameAndEmail((String) any(), (String) any());
  }
}

