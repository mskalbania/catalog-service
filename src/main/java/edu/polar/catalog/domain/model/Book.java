package edu.polar.catalog.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Book(
    @NotBlank @Pattern(regexp = "[0-9]{10}|[0-9]{13}") String isbn,
    @NotBlank String title,
    @NotBlank String author,
    @Positive BigDecimal price
) {
}
