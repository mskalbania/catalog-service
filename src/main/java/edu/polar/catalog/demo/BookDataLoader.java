package edu.polar.catalog.demo;

import edu.polar.catalog.domain.model.Book;
import edu.polar.catalog.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConditionalOnProperty(name = "application.test-data-enabled", havingValue = "true")
@RequiredArgsConstructor
public class BookDataLoader {

    private final BookRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void addBookData() throws Exception {
        repository.save(Book.builder().isbn("isbn1").author("auth1").title("title1").price(BigDecimal.ONE).build())
            .toFuture().get();
        repository.save(Book.builder().isbn("isbn2").author("auth2").title("title2").price(BigDecimal.ONE).build())
            .toFuture().get();
    }
}
