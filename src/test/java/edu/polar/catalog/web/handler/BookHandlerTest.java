package edu.polar.catalog.web.handler;

import edu.polar.catalog.domain.model.Book;
import edu.polar.catalog.domain.service.BookService;
import edu.polar.catalog.web.Routes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;

class BookHandlerTest {

    private WebTestClient webTestClient;
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = Mockito.mock(BookService.class);
        var bookHandler = new BookHandler(bookService, Mockito.mock(Validations.class));
        webTestClient = WebTestClient.bindToRouterFunction(new Routes().router(bookHandler)).build();
    }

    @Test
    public void shouldCorrectlyHandleGetBook() {
        //given book exist
        given(bookService.getBook(ANY_ISBN))
                .willReturn(Mono.just(new Book(ANY_ISBN, ANY_TITLE, ANY_AUTHOR, ANY_PRICE)));

        //when handler called
        var exchange = webTestClient.get()
                .uri("/api/v1/books/" + ANY_ISBN)
                .exchange();

        //then correct body returned
        exchange.expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.isbn").isEqualTo(ANY_ISBN);
    }

    //and more...

    private static final String ANY_ISBN = "9783161484100";
    private static final String ANY_TITLE = "title";
    private static final String ANY_AUTHOR = "author";
    private static final BigDecimal ANY_PRICE = BigDecimal.valueOf(11.11);
}