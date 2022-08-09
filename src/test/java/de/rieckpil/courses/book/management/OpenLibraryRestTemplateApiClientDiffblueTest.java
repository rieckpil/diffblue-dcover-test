package de.rieckpil.courses.book.management;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;

class OpenLibraryRestTemplateApiClientDiffblueTest {
  @MockBean
  private OpenLibraryRestTemplateApiClient openLibraryRestTemplateApiClient;

  @MockBean
  private RestTemplateBuilder restTemplateBuilder;
  /**
  * Method under test: {@link OpenLibraryRestTemplateApiClient#fetchMetadataForBook(String)}
  */
  @Test
  void testFetchMetadataForBook() {
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
    when(openLibraryRestTemplateApiClient.fetchMetadataForBook((String) any())).thenReturn(book);

    // Act and Assert
    assertSame(book, openLibraryRestTemplateApiClient.fetchMetadataForBook("Isbn"));
    verify(openLibraryRestTemplateApiClient).fetchMetadataForBook((String) any());
  }
}

