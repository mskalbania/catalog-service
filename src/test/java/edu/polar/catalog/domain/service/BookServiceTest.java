package edu.polar.catalog.domain.service;

import edu.polar.catalog.domain.exception.BookException.BookNotFoundException;
import edu.polar.catalog.domain.model.Book;
import edu.polar.catalog.domain.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class BookServiceTest {

    private final BookRepository bookRepositoryMock = Mockito.mock(BookRepository.class);
    private final BookService bookService = new BookService(bookRepositoryMock);

    @Test
    public void shouldReturnErrorWhenBookNotFound() {
        //given repository doesn't contain book
        Mockito.when(bookRepositoryMock.findByIsbn(ANY_ISBN)).thenReturn(Mono.empty());

        //when service called
        Mono<Book> bookMono = bookService.getBook(ANY_ISBN);

        //then error returned
        StepVerifier.create(bookMono)
                .expectErrorSatisfies(throwable -> {
                    Assertions.assertInstanceOf(BookNotFoundException.class, throwable);
                    Assertions.assertEquals("Book with isbn " + ANY_ISBN + " not found", throwable.getMessage());
                }).verify();
    }

    //An so on../

    private static final String ANY_ISBN = "9783161484100";
}