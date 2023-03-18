package io.cucumber.book;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> store = new ArrayList<>();

    public void addBook(final Book book) {
        store.add(book);
    }

    public List<Book> findBooks(final LocalDateTime from, final LocalDateTime to) {
        return store.stream().filter(book -> {
            return from.isBefore(book.getPublished()) && to.isAfter(book.getPublished());
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

<<<<<<< HEAD
    public List<Book> booksByAuthor(final String author) {
        return store.stream().filter(book -> {
            return book.getAuthor().equals(author);
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

    public List<Book> booksByTitle(String title) {
        return store.stream().filter(book -> {
            return book.getTitle().equals(title);
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }
=======
>>>>>>> parent of 6892117 (write new feature)
}
