package de.rieckpil.courses.book.management;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** rieckpil
 *
 * The tested method has an if-statement, so I would
 * expect to have at least two tests to cover both branches.
 * Diffblue only generated one.
 *
 * JUnit & Mockito would have also been sufficient, no need for an ApplicationContext
 *
 */
@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceDiffblueTest {
  @MockBean
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  /**
  * Method under test: {@link UserService#getOrCreateUser(String, String)}
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
    when(userRepository.findByNameAndEmail((String) any(), (String) any())).thenReturn(user);
    when(userRepository.save((User) any())).thenReturn(user1);

    // Act and Assert
    assertSame(user, userService.getOrCreateUser("Name", "jane.doe@example.org"));
    verify(userRepository).findByNameAndEmail((String) any(), (String) any());
  }
}

