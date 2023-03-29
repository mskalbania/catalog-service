package edu.polar.catalog.web.handler;

import edu.polar.catalog.web.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Validations {

    private final Validator validator;

    public <T> Mono<ServerResponse> withValidation(
            ServerRequest serverRequest,
            Class<T> bodyType,
            Function<T, Mono<ServerResponse>> handler
    ) {
        return serverRequest.bodyToMono(bodyType)
                .flatMap(body -> {
                    Errors errors = new BeanPropertyBindingResult(body, bodyType.getName());
                    this.validator.validate(body, errors);
                    if (errors.getAllErrors().isEmpty()) {
                        return handler.apply(body);
                    } else {
                        return onValidationErrors(errors, serverRequest);
                    }
                });
    }

    private Mono<ServerResponse> onValidationErrors(Errors errors, ServerRequest request) {
        return ErrorResponse.badRequest(
                errors.getFieldErrors()
                        .stream()
                        .map(error -> error.getField() + " " + error.getDefaultMessage())
                        .collect(Collectors.toList())
        );
    }
}
