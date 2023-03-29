package edu.polar.catalog.adapter.database;

import edu.polar.catalog.domain.model.Book;
import edu.polar.catalog.domain.repository.BookRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryBookRepository implements BookRepository {

    public final ConcurrentHashMap<String, Book> isbnToBook = new ConcurrentHashMap<>();

    @Override
    public Mono<List<Book>> findAll() {
        return Mono.fromCallable(() -> isbnToBook.values().stream().toList());
    }

    @Override
    public Mono<Book> findByIsbn(String isbn) {
        return Mono.justOrEmpty(isbnToBook.get(isbn));
    }

    @Override
    public Mono<Book> save(Book book) {
        return Mono.fromCallable(() -> isbnToBook.put(book.isbn(), book))
                .switchIfEmpty(Mono.just(book));
    }

    @Override
    public Mono<Book> delete(String isbn) {
        return Mono.fromCallable(() -> isbnToBook.remove(isbn));
    }
}
