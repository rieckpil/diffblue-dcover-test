package de.rieckpil.courses.book.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.rieckpil.courses.book.management.Book;
import de.rieckpil.courses.book.management.BookRepository;
import de.rieckpil.courses.book.management.User;
import de.rieckpil.courses.book.management.UserService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** rieckpil
 * The testCreateBookReview, testCreateBookReview2, testCreateBookReview3
 * together with the longer test methods due to the required setup,
 * make it hard to understand what use case is actually tested.
 *
 * Furthermore, the variable names also use the class name and then
 * an incremented value: book, book1, book2.
 *
 * This is a maintenance and understanding nightmare (code is read more than written).
 *
 * I try to give them meaningful names like newBook, existingBook, userWithLackingPermissions.
 *
 * I know that's hard to come up with an AI but still wanted to give this feedback.
 *
 * Also, I see that passed parameters are mocked. This can result quite fast in a
 * "mocking-hell" making the test brittle as they don't allow refactorings.
 *
 */
@ContextConfiguration(classes = {ReviewService.class})
@ExtendWith(SpringExtension.class)
class ReviewServiceDiffblueTest {
  @MockBean
  private BookRepository bookRepository;

  @MockBean
  private ReviewRepository reviewRepository;

  @Autowired
  private ReviewService reviewService;

  @MockBean
  private ReviewVerifier reviewVerifier;

  @MockBean
  private UserService userService;
  /**
  * Method under test: {@link ReviewService#createBookReview(String, BookReviewRequest, String, String)}
  */
  @Test
  void testCreateBookReview() {
    // Arrange
    when(reviewVerifier.doesMeetQualityStandards((String) any())).thenReturn(true);

    User user = new User();
    user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user.setEmail("jane.doe@example.org");
    user.setId(123L);
    user.setName("Name");
    when(userService.getOrCreateUser((String) any(), (String) any())).thenReturn(user);

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
    when(bookRepository.findByIsbn((String) any())).thenReturn(book);

    Book book1 = new Book();
    book1.setAuthor("JaneDoe");
    book1.setDescription("The characteristics of someone or something");
    book1.setGenre("Genre");
    book1.setId(123L);
    book1.setIsbn("Isbn");
    book1.setPages(1L);
    book1.setPublisher("Publisher");
    book1.setThumbnailUrl("https://example.org/example");
    book1.setTitle("Dr");

    User user1 = new User();
    user1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user1.setEmail("jane.doe@example.org");
    user1.setId(123L);
    user1.setName("Name");

    Review review = new Review();
    review.setBook(book1);
    review.setContent("Not all who wander are lost");
    review.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    review.setId(123L);
    review.setRating(1);
    review.setTitle("Dr");
    review.setUser(user1);
    when(reviewRepository.save((Review) any())).thenReturn(review);

    // Act and Assert
    assertEquals(123L,
        reviewService
            .createBookReview("Isbn", new BookReviewRequest("Dr", "Not all who wander are lost", 1), "janedoe",
                "jane.doe@example.org")
            .longValue());
    verify(reviewVerifier).doesMeetQualityStandards((String) any());
    verify(userService).getOrCreateUser((String) any(), (String) any());
    verify(bookRepository).findByIsbn((String) any());
    verify(reviewRepository).save((Review) any());
  }

  /**
   * Method under test: {@link ReviewService#createBookReview(String, BookReviewRequest, String, String)}
   */
  @Test
  void testCreateBookReview2() {
    // Arrange
    when(reviewVerifier.doesMeetQualityStandards((String) any())).thenReturn(true);

    User user = new User();
    user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user.setEmail("jane.doe@example.org");
    user.setId(123L);
    user.setName("Name");
    when(userService.getOrCreateUser((String) any(), (String) any())).thenReturn(user);

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
    when(bookRepository.findByIsbn((String) any())).thenReturn(book);
    when(reviewRepository.save((Review) any())).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> reviewService.createBookReview("Isbn",
        new BookReviewRequest("Dr", "Not all who wander are lost", 1), "janedoe", "jane.doe@example.org"));
    verify(reviewVerifier).doesMeetQualityStandards((String) any());
    verify(userService).getOrCreateUser((String) any(), (String) any());
    verify(bookRepository).findByIsbn((String) any());
    verify(reviewRepository).save((Review) any());
  }

  /**
   * Method under test: {@link ReviewService#createBookReview(String, BookReviewRequest, String, String)}
   */
  @Test
  void testCreateBookReview3() {
    // Arrange
    when(reviewVerifier.doesMeetQualityStandards((String) any())).thenReturn(false);

    User user = new User();
    user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user.setEmail("jane.doe@example.org");
    user.setId(123L);
    user.setName("Name");
    when(userService.getOrCreateUser((String) any(), (String) any())).thenReturn(user);

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
    when(bookRepository.findByIsbn((String) any())).thenReturn(book);

    Book book1 = new Book();
    book1.setAuthor("JaneDoe");
    book1.setDescription("The characteristics of someone or something");
    book1.setGenre("Genre");
    book1.setId(123L);
    book1.setIsbn("Isbn");
    book1.setPages(1L);
    book1.setPublisher("Publisher");
    book1.setThumbnailUrl("https://example.org/example");
    book1.setTitle("Dr");

    User user1 = new User();
    user1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user1.setEmail("jane.doe@example.org");
    user1.setId(123L);
    user1.setName("Name");

    Review review = new Review();
    review.setBook(book1);
    review.setContent("Not all who wander are lost");
    review.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    review.setId(123L);
    review.setRating(1);
    review.setTitle("Dr");
    review.setUser(user1);
    when(reviewRepository.save((Review) any())).thenReturn(review);

    // Act and Assert
    assertThrows(BadReviewQualityException.class, () -> reviewService.createBookReview("Isbn",
        new BookReviewRequest("Dr", "Not all who wander are lost", 1), "janedoe", "jane.doe@example.org"));
    verify(reviewVerifier).doesMeetQualityStandards((String) any());
    verify(bookRepository).findByIsbn((String) any());
  }

  /**
   * Method under test: {@link ReviewService#getReviewStatistics()}
   */
  @Test
  void testGetReviewStatistics() {
    // Arrange
    when(reviewRepository.getReviewStatistics()).thenReturn(new ArrayList<>());

    // Act and Assert
    assertEquals(0, reviewService.getReviewStatistics().size());
    verify(reviewRepository).getReviewStatistics();
  }

  /**
   * Method under test: {@link ReviewService#getReviewStatistics()}
   */
  @Test
  void testGetReviewStatistics2() {
    // Arrange
    when(reviewRepository.getReviewStatistics()).thenThrow(new IllegalArgumentException("foo"));

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> reviewService.getReviewStatistics());
    verify(reviewRepository).getReviewStatistics();
  }

  /**
   * Method under test: {@link ReviewService#getReviewStatistics()}
   */
  @Test
  void testGetReviewStatistics3() {
    // Arrange
    ReviewStatistic reviewStatistic = mock(ReviewStatistic.class);
    when(reviewStatistic.getId()).thenReturn(123L);
    when(reviewStatistic.getRatings()).thenReturn(1L);
    when(reviewStatistic.getIsbn()).thenReturn("Isbn");
    when(reviewStatistic.getAvg()).thenReturn(BigDecimal.valueOf(42L));

    ArrayList<ReviewStatistic> reviewStatisticList = new ArrayList<>();
    reviewStatisticList.add(reviewStatistic);
    when(reviewRepository.getReviewStatistics()).thenReturn(reviewStatisticList);

    // Act
    ArrayNode actualReviewStatistics = reviewService.getReviewStatistics();

    // Assert
    assertEquals("[ {\n  \"bookId\" : 123,\n  \"isbn\" : \"Isbn\",\n  \"avg\" : 42,\n  \"ratings\" : 1\n} ]",
        actualReviewStatistics.toPrettyString());
    assertEquals(1, actualReviewStatistics.size());
    verify(reviewRepository).getReviewStatistics();
    verify(reviewStatistic).getId();
    verify(reviewStatistic).getRatings();
    verify(reviewStatistic).getIsbn();
    verify(reviewStatistic).getAvg();
  }

  /**
   * Method under test: {@link ReviewService#getReviewStatistics()}
   */
  @Test
  void testGetReviewStatistics4() {
    // Arrange
    ReviewStatistic reviewStatistic = mock(ReviewStatistic.class);
    when(reviewStatistic.getId()).thenReturn(123L);
    when(reviewStatistic.getRatings()).thenReturn(1L);
    when(reviewStatistic.getIsbn()).thenReturn("Isbn");
    when(reviewStatistic.getAvg()).thenReturn(BigDecimal.valueOf(0L));

    ArrayList<ReviewStatistic> reviewStatisticList = new ArrayList<>();
    reviewStatisticList.add(reviewStatistic);
    when(reviewRepository.getReviewStatistics()).thenReturn(reviewStatisticList);

    // Act
    ArrayNode actualReviewStatistics = reviewService.getReviewStatistics();

    // Assert
    assertEquals("[ {\n  \"bookId\" : 123,\n  \"isbn\" : \"Isbn\",\n  \"avg\" : 0,\n  \"ratings\" : 1\n} ]",
        actualReviewStatistics.toPrettyString());
    assertEquals(1, actualReviewStatistics.size());
    verify(reviewRepository).getReviewStatistics();
    verify(reviewStatistic).getId();
    verify(reviewStatistic).getRatings();
    verify(reviewStatistic).getIsbn();
    verify(reviewStatistic).getAvg();
  }

  /**
   * Method under test: {@link ReviewService#getReviewStatistics()}
   */
  @Test
  void testGetReviewStatistics5() {
    // Arrange
    ReviewStatistic reviewStatistic = mock(ReviewStatistic.class);
    when(reviewStatistic.getId()).thenReturn(123L);
    when(reviewStatistic.getRatings()).thenReturn(1L);
    when(reviewStatistic.getIsbn()).thenReturn("Isbn");
    when(reviewStatistic.getAvg()).thenReturn(null);

    ArrayList<ReviewStatistic> reviewStatisticList = new ArrayList<>();
    reviewStatisticList.add(reviewStatistic);
    when(reviewRepository.getReviewStatistics()).thenReturn(reviewStatisticList);

    // Act
    ArrayNode actualReviewStatistics = reviewService.getReviewStatistics();

    // Assert
    assertEquals("[ {\n  \"bookId\" : 123,\n  \"isbn\" : \"Isbn\",\n  \"avg\" : null,\n  \"ratings\" : 1\n} ]",
        actualReviewStatistics.toPrettyString());
    assertEquals(1, actualReviewStatistics.size());
    verify(reviewRepository).getReviewStatistics();
    verify(reviewStatistic).getId();
    verify(reviewStatistic).getRatings();
    verify(reviewStatistic).getIsbn();
    verify(reviewStatistic).getAvg();
  }

  /**
   * Method under test: {@link ReviewService#getAllReviews(Integer, String)}
   */
  @Test
  void testGetAllReviews() {
    // Arrange
    when(reviewRepository.findAllByOrderByCreatedAtDesc((Pageable) any())).thenReturn(new ArrayList<>());

    // Act and Assert
    assertEquals(0, reviewService.getAllReviews(3, "Order By").size());
    verify(reviewRepository).findAllByOrderByCreatedAtDesc((Pageable) any());
  }

  /**
   * Method under test: {@link ReviewService#getAllReviews(Integer, String)}
   */
  @Test
  void testGetAllReviews2() {
    // Arrange
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("rating");
    book.setId(123L);
    book.setIsbn("rating");
    book.setPages(1L);
    book.setPublisher("rating");
    book.setThumbnailUrl("https://example.org/example");
    book.setTitle("Dr");

    User user = new User();
    user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user.setEmail("jane.doe@example.org");
    user.setId(123L);
    user.setName("rating");

    Review review = new Review();
    review.setBook(book);
    review.setContent("Not all who wander are lost");
    review.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    review.setId(123L);
    review.setRating(1);
    review.setTitle("Dr");
    review.setUser(user);

    ArrayList<Review> reviewList = new ArrayList<>();
    reviewList.add(review);
    when(reviewRepository.findAllByOrderByCreatedAtDesc((Pageable) any())).thenReturn(reviewList);

    // Act
    ArrayNode actualAllReviews = reviewService.getAllReviews(3, "Order By");

    // Assert
    assertEquals(
        "[ {\n" + "  \"reviewId\" : 123,\n" + "  \"reviewContent\" : \"Not all who wander are lost\",\n"
            + "  \"reviewTitle\" : \"Dr\",\n" + "  \"rating\" : 1,\n" + "  \"bookIsbn\" : \"rating\",\n"
            + "  \"bookTitle\" : \"Dr\",\n" + "  \"bookThumbnailUrl\" : \"https://example.org/example\",\n"
            + "  \"submittedBy\" : \"rating\",\n" + "  \"submittedAt\" : -62135596348000\n" + "} ]",
        actualAllReviews.toPrettyString());
    assertEquals(1, actualAllReviews.size());
    verify(reviewRepository).findAllByOrderByCreatedAtDesc((Pageable) any());
  }

  /**
   * Method under test: {@link ReviewService#getAllReviews(Integer, String)}
   */
  @Test
  void testGetAllReviews3() {
    // Arrange
    Book book = new Book();
    book.setAuthor("JaneDoe");
    book.setDescription("The characteristics of someone or something");
    book.setGenre("rating");
    book.setId(123L);
    book.setIsbn("rating");
    book.setPages(1L);
    book.setPublisher("rating");
    book.setThumbnailUrl("https://example.org/example");
    book.setTitle("Dr");

    User user = new User();
    user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user.setEmail("jane.doe@example.org");
    user.setId(123L);
    user.setName("rating");

    Review review = new Review();
    review.setBook(book);
    review.setContent("Not all who wander are lost");
    review.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    review.setId(123L);
    review.setRating(1);
    review.setTitle("Dr");
    review.setUser(user);

    Book book1 = new Book();
    book1.setAuthor("JaneDoe");
    book1.setDescription("The characteristics of someone or something");
    book1.setGenre("rating");
    book1.setId(123L);
    book1.setIsbn("rating");
    book1.setPages(1L);
    book1.setPublisher("rating");
    book1.setThumbnailUrl("https://example.org/example");
    book1.setTitle("Dr");

    User user1 = new User();
    user1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user1.setEmail("jane.doe@example.org");
    user1.setId(123L);
    user1.setName("rating");

    Review review1 = new Review();
    review1.setBook(book1);
    review1.setContent("Not all who wander are lost");
    review1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    review1.setId(123L);
    review1.setRating(1);
    review1.setTitle("Dr");
    review1.setUser(user1);

    ArrayList<Review> reviewList = new ArrayList<>();
    reviewList.add(review1);
    reviewList.add(review);
    when(reviewRepository.findAllByOrderByCreatedAtDesc((Pageable) any())).thenReturn(reviewList);

    // Act
    ArrayNode actualAllReviews = reviewService.getAllReviews(3, "Order By");

    // Assert
    assertEquals(
        "[ {\n" + "  \"reviewId\" : 123,\n" + "  \"reviewContent\" : \"Not all who wander are lost\",\n"
            + "  \"reviewTitle\" : \"Dr\",\n" + "  \"rating\" : 1,\n" + "  \"bookIsbn\" : \"rating\",\n"
            + "  \"bookTitle\" : \"Dr\",\n" + "  \"bookThumbnailUrl\" : \"https://example.org/example\",\n"
            + "  \"submittedBy\" : \"rating\",\n" + "  \"submittedAt\" : -62135596348000\n" + "}, {\n"
            + "  \"reviewId\" : 123,\n" + "  \"reviewContent\" : \"Not all who wander are lost\",\n"
            + "  \"reviewTitle\" : \"Dr\",\n" + "  \"rating\" : 1,\n" + "  \"bookIsbn\" : \"rating\",\n"
            + "  \"bookTitle\" : \"Dr\",\n" + "  \"bookThumbnailUrl\" : \"https://example.org/example\",\n"
            + "  \"submittedBy\" : \"rating\",\n" + "  \"submittedAt\" : -62135596348000\n" + "} ]",
        actualAllReviews.toPrettyString());
    assertEquals(2, actualAllReviews.size());
    verify(reviewRepository).findAllByOrderByCreatedAtDesc((Pageable) any());
  }

  /**
   * Method under test: {@link ReviewService#getAllReviews(Integer, String)}
   */
  @Test
  void testGetAllReviews4() {
    // Arrange
    when(reviewRepository.findTop5ByOrderByRatingDescCreatedAtDesc()).thenReturn(new ArrayList<>());
    when(reviewRepository.findAllByOrderByCreatedAtDesc((Pageable) any())).thenReturn(new ArrayList<>());

    // Act and Assert
    assertEquals(0, reviewService.getAllReviews(3, "rating").size());
    verify(reviewRepository).findTop5ByOrderByRatingDescCreatedAtDesc();
  }

  /**
   * Method under test: {@link ReviewService#getAllReviews(Integer, String)}
   */
  @Test
  void testGetAllReviews5() {
    // Arrange
    when(reviewRepository.findTop5ByOrderByRatingDescCreatedAtDesc()).thenThrow(new IllegalArgumentException("rating"));
    when(reviewRepository.findAllByOrderByCreatedAtDesc((Pageable) any())).thenReturn(new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> reviewService.getAllReviews(3, "rating"));
    verify(reviewRepository).findTop5ByOrderByRatingDescCreatedAtDesc();
  }

  /**
   * Method under test: {@link ReviewService#deleteReview(String, Long)}
   */
  @Test
  void testDeleteReview() {
    // Arrange
    doNothing().when(reviewRepository).deleteByIdAndBookIsbn((Long) any(), (String) any());

    // Act
    reviewService.deleteReview("Isbn", 123L);

    // Assert that nothing has changed
    verify(reviewRepository).deleteByIdAndBookIsbn((Long) any(), (String) any());
  }

  /**
   * Method under test: {@link ReviewService#deleteReview(String, Long)}
   */
  @Test
  void testDeleteReview2() {
    // Arrange
    doThrow(new IllegalArgumentException("foo")).when(reviewRepository)
        .deleteByIdAndBookIsbn((Long) any(), (String) any());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> reviewService.deleteReview("Isbn", 123L));
    verify(reviewRepository).deleteByIdAndBookIsbn((Long) any(), (String) any());
  }

  /**
   * Method under test: {@link ReviewService#getReviewById(String, Long)}
   */
  @Test
  void testGetReviewById() {
    // Arrange
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

    User user = new User();
    user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    user.setEmail("jane.doe@example.org");
    user.setId(123L);
    user.setName("Name");

    Review review = new Review();
    review.setBook(book);
    review.setContent("Not all who wander are lost");
    review.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
    review.setId(123L);
    review.setRating(1);
    review.setTitle("Dr");
    review.setUser(user);
    Optional<Review> ofResult = Optional.of(review);
    when(reviewRepository.findByIdAndBookIsbn((Long) any(), (String) any())).thenReturn(ofResult);

    // Act
    ObjectNode actualReviewById = reviewService.getReviewById("Isbn", 123L);

    // Assert
    assertEquals(
        "{\n" + "  \"reviewId\" : 123,\n" + "  \"reviewContent\" : \"Not all who wander are lost\",\n"
            + "  \"reviewTitle\" : \"Dr\",\n" + "  \"rating\" : 1,\n" + "  \"bookIsbn\" : \"Isbn\",\n"
            + "  \"bookTitle\" : \"Dr\",\n" + "  \"bookThumbnailUrl\" : \"https://example.org/example\",\n"
            + "  \"submittedBy\" : \"Name\",\n" + "  \"submittedAt\" : -62135596348000\n" + "}",
        actualReviewById.toPrettyString());
    assertEquals(9, actualReviewById.size());
    verify(reviewRepository).findByIdAndBookIsbn((Long) any(), (String) any());
  }
}

