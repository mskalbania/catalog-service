package edu.polar.catalog.web.handler;

import edu.polar.catalog.domain.exception.BookException.BookAlreadyExistsException;
import edu.polar.catalog.domain.exception.BookException.BookNotFoundException;
import edu.polar.catalog.domain.model.Book;
import edu.polar.catalog.domain.service.BookService;
import edu.polar.catalog.web.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class BookHandler {

    private final BookService bookService;
    private final Validations validator;

    public Mono<ServerResponse> getBooks(ServerRequest serverRequest) {
        return ServerResponse.ok()
            .body(bookService.getBooks(), new ParameterizedTypeReference<>() {
            });
    }

    public Mono<ServerResponse> getBookByIsbn(ServerRequest serverRequest) {
        return bookService.getBook(serverRequest.pathVariable("isbn"))
            .flatMap(book -> ServerResponse.ok().bodyValue(book))
            .onErrorResume(BookNotFoundException.class, __ -> ErrorResponse.notFound());
    }

    public Mono<ServerResponse> addBook(ServerRequest serverRequest) {
        return validator.withValidation(
            serverRequest,
            Book.class,
            book -> bookService.addBook(book)
                .flatMap(__ -> ServerResponse.status(HttpStatus.CREATED).build())
                .onErrorResume(
                    BookAlreadyExistsException.class,
                    __ -> ErrorResponse.unprocessableEntity("book with given isbn already exists")
                )
        );
    }

    public Mono<ServerResponse> deleteBook(ServerRequest serverRequest) {
        return bookService.deleteBook(serverRequest.pathVariable("isbn"))
            .flatMap(__ -> ServerResponse.noContent().build())
            .onErrorResume(BookNotFoundException.class, __ -> ErrorResponse.notFound());
    }
}
