package edu.polar.catalog.domain.service;

import edu.polar.catalog.domain.exception.BookException.BookAlreadyExistsException;
import edu.polar.catalog.domain.exception.BookException.BookNotFoundException;
import edu.polar.catalog.domain.model.Book;
import edu.polar.catalog.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Mono<List<Book>> getBooks() {
        return bookRepository.findAll();
    }

    public Mono<Book> getBook(String isbn) {
        return bookRepository.findByIsbn(isbn)
            .switchIfEmpty(Mono.error(new BookNotFoundException(isbn)));
    }

    public Mono<Book> addBook(Book book) {
        return bookRepository.findByIsbn(book.isbn())
            .flatMap(__ -> Mono.<Book>error(new BookAlreadyExistsException(book.isbn())))
            .switchIfEmpty(bookRepository.save(book));
    }

    public Mono<Book> deleteBook(String isbn) {
        return bookRepository.delete(isbn)
            .switchIfEmpty(Mono.error(new BookNotFoundException(isbn)));
    }
}
