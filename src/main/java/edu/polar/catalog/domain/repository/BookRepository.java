package edu.polar.catalog.domain.repository;

import edu.polar.catalog.domain.model.Book;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookRepository {

    Mono<List<Book>> findAll();

    Mono<Book> findByIsbn(String isbn);

    Mono<Book> save(Book book);

    Mono<Book> delete(String isbn);
}
