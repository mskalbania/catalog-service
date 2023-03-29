package edu.polar.catalog.domain.exception;

import lombok.RequiredArgsConstructor;

public abstract class BookException extends RuntimeException {

    @RequiredArgsConstructor
    public static class BookNotFoundException extends BookException {

        private final String bookIsbn;

        @Override
        public String getMessage() {
            return String.format("Book with isbn %s not found", bookIsbn);
        }
    }

    @RequiredArgsConstructor
    public static class BookAlreadyExistsException extends BookException {

        private final String bookIsbn;

        @Override
        public String getMessage() {
            return String.format("Book with isbn %s already exists", bookIsbn);
        }
    }
}
