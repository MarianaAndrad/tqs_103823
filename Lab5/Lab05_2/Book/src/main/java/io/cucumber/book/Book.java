package io.cucumber.book;

import java.time.LocalDateTime;
import java.util.Date;

public class Book {

    private final String title;
    private final String author;
    private final LocalDateTime published;

    private final String Category;

    public Book(String title, String author, LocalDateTime published) {
        this.title = title;
        this.author = author;
        this.published = published;
        this.Category = "None";
    }
    public Book(String title, String author, LocalDateTime published, String Category) {
        this.title = title;
        this.author = author;
        this.published = published;
        this.Category = Category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getPublished() {
        return published;
    }



}
