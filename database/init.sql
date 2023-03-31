CREATE TABLE book
(
    isbn   text PRIMARY KEY,
    title  text          NOT NULL,
    author text          NOT NULL,
    price  decimal(6, 2) NOT NULL
)