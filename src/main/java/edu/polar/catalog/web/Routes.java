package edu.polar.catalog.web;

import edu.polar.catalog.web.handler.BookHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> router(BookHandler bookHandler) {
        return route().nest(RequestPredicates.path("/api/v1"), builder ->
                builder
                        .GET("/books", bookHandler::getBooks)
                        .POST("/books", bookHandler::addBook)
                        .GET("/books/{isbn}", bookHandler::getBookByIsbn)
                        .DELETE("/books/{isbn}", bookHandler::deleteBook)
        ).build();
    }
}
