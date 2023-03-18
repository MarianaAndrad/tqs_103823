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

    public List<Book> booksByAuthor(final String author) {
        return store.stream().filter(book -> {
            return book.getAuthor().equals(author);
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

    public List<Book> booksByTitle(String title) {
        return store.stream().filter(book -> {
            return book.getTitle().contains(title);
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

    public List<Book> booksByAuthorAndTitle(String author, String title) {
        return store.stream().filter(book -> {
            return book.getAuthor().equals(author) && book.getTitle().contains(title);
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

    public List<Book> booksByCategory(String category) {
        return store.stream().filter(book -> {
            return book.getCategory().equals(category);
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

    public List<Book> booksByTitleAndCategory(String title, String category) {
        return store.stream().filter(book -> {
            return book.getTitle().contains(title) && book.getCategory().equals(category);
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

    public List<Book> booksByAuthorAndCategory(String author, String category) {
        return store.stream().filter(book -> {
            return book.getAuthor().equals(author) && book.getCategory().equals(category);
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

    public List<Book> booksByAuthorAndTitleAndCategory(String author, String title, String category) {
        return store.stream().filter(book -> {
            return book.getAuthor().equals(author) && book.getTitle().contains(title) && book.getCategory().equals(category);
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

    public List<Book> booksByAuthorAndDateAndCategory(String author, LocalDateTime published, String category) {
        return store.stream().filter(book -> {
            return book.getAuthor().equals(author) && book.getPublished().isBefore(published) && book.getCategory().equals(category);
        }).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }
}
