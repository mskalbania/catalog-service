package edu.polar.catalog.web.dto;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(int code, LocalDateTime timestamp, List<String> messages) {

    public static Mono<ServerResponse> badRequest(List<String> messages) {
        return ServerResponse.badRequest()
            .bodyValue(new ErrorResponse(400, LocalDateTime.now(), messages));
    }

    public static Mono<ServerResponse> unprocessableEntity(String message) {
        return ServerResponse.unprocessableEntity()
            .bodyValue(new ErrorResponse(422, LocalDateTime.now(), List.of(message)));
    }

    public static Mono<ServerResponse> notFound() {
        return ServerResponse.notFound().build();
    }
}
