package de.rieckpil.courses.book.management;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InitialBookCreator.class, String.class})
@ExtendWith(SpringExtension.class)
class InitialBookCreatorDiffblueTest {
  @MockBean
  private BookRepository bookRepository;

  @Autowired
  private InitialBookCreator initialBookCreator;

  @MockBean
  private QueueMessagingTemplate queueMessagingTemplate;
  /**
  * Method under test: {@link InitialBookCreator#initialize(ApplicationReadyEvent)}
  */
  @Test
  void testInitialize() {
    // Arrange
    when(bookRepository.count()).thenReturn(3L);
    SpringApplication application = new SpringApplication(Object.class);

    // Act
    initialBookCreator.initialize(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext()));

    // Assert that nothing has changed
    verify(bookRepository).count();
  }

  /**
   * Method under test: {@link InitialBookCreator#initialize(ApplicationReadyEvent)}
   */
  @Test
  void testInitialize2() throws MessagingException {
    // Arrange
    when(bookRepository.count()).thenReturn(0L);
    doNothing().when(queueMessagingTemplate)
        .convertAndSend((String) any(), (Object) any(), (Map<String, Object>) any());
    SpringApplication application = new SpringApplication(Object.class);

    // Act
    initialBookCreator.initialize(new ApplicationReadyEvent(application, new String[]{"Args"},
        new AnnotationConfigReactiveWebApplicationContext()));

    // Assert
    verify(bookRepository).count();
    verify(queueMessagingTemplate, atLeast(1)).convertAndSend((String) any(), (Object) any(),
        (Map<String, Object>) any());
  }
}

