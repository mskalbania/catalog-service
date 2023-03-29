package edu.polar.catalog;

import edu.polar.catalog.domain.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//Integration with full context
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CatalogServiceIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldCorrectlyHandleAddBookRequest() {
        var bookToAdd = new Book("9783161484100", "title", "author", BigDecimal.valueOf(22.20));

        webTestClient.post()
                .uri("/api/v1/books")
                .body(Mono.just(bookToAdd), Book.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void shouldRejectRequestIfValidationFailed() {
        var bookToAdd = new Book("incorrect-isbn", "title", "author", BigDecimal.valueOf(22.20));

        webTestClient.post()
                .uri("/api/v1/books")
                .body(Mono.just(bookToAdd), Book.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.messages[0]").isEqualTo("isbn must match \"[0-9]{10}|[0-9]{13}\"");
    }

    //and more...
}
