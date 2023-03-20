package io.cucumber.book;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StepDefinitions {
    private final Library library = new Library();
    private List<Book> result = new ArrayList<>();


    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDateTime iso8601Date(String year, String month, String day){
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0, 0);
    }

    @Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addNewBook(final String title, final String author, final LocalDateTime published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @Given("a book with the title {string}, written by {string}, published in {iso8601Date} and categorized as {string}")
    public void addNewBook(final String title, final String author, final LocalDateTime published, final String Category) {
        Book book = new Book(title, author, published, Category);
        library.addBook(book);
    }
    @Given("another book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addAnotherBook(final String title, final String author, final LocalDateTime published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @Given("another book with the title {string}, written by {string}, published in {iso8601Date} and categorized as {string}")
    public void addAnotherBook(final String title, final String author, final LocalDateTime published, final String Category) {
        Book book = new Book(title, author, published, Category);
        library.addBook(book);
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void setSearchParameters(final LocalDateTime from, final LocalDateTime to) {
        result = library.findBooks(from, to);
    }

    @Then("{int} books should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        Assertions.assertEquals(result.size(), booksFound);
    }

    @Then("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        Assertions.assertEquals(result.get(position - 1).getTitle(), title);
    }

    @When("the customer searches for books by author {string}")
    public void searchForBooksByAuthor(final String author) {
        result = library.booksByAuthor(author);
    }


    @When("the customer searches for books by title {string}")
    public void searchForBooksByTitle(final String title) {
        result = library.booksByTitle(title);
    }

    @When("the customer searches for books by author {string} and by title {string}")
    public void searchForBooksByAuthorAndTitle(final String author, final String title) {
        result = library.booksByAuthorAndTitle(author, title);
    }

    @When("the customer searches for books in the {string} category")
    public void searchForBooksByCategory(final String category) {
        result = library.booksByCategory(category);
    }

    @When("the customer searches for books by title {string} and in the {string} category")
    public void searchForBooksByTitleAndCategory(final String title, final String category) {
        result = library.booksByTitleAndCategory(title, category);
    }

    @When("the customer searches for books by author {string} and in the {string} category")
    public void searchForBooksByAuthorAndCategory(final String author, final String category) {
        result = library.booksByAuthorAndCategory(author, category);
    }

    @When("the customer searches for books by author {string}, by title {string} and in the {string} category")
    public void searchForBooksByAuthorAndTitleAndCategory(final String author, final String title, final String category) {
        result = library.booksByAuthorAndTitleAndCategory(author, title, category);
    }

    @When("the customer searches for books written by {string} and published before {iso8601Date} and categorized as {string}")
    public void searchForBooksByAuthorAndDateAndCategory(final String author, final LocalDateTime published, final String category) {
        result = library.booksByAuthorAndDateAndCategory(author, published, category);
    }

    @Then("Book {int} should have the author {string}")
    public void verifyBookAtPositionWrittenBy(final int position, final String author) {
        Assertions.assertEquals(result.get(position - 1).getAuthor(), author);
    }

    // Data table
    @Given("I have the following books in the store")
    public void addNewBook(DataTable books) {
        List<Map<String, String>> booksList = books.asMaps(String.class, String.class);
        for (Map<String, String> columns : booksList) {
            String[] date = columns.get("published").split("-");
            LocalDateTime published = iso8601Date(date[0], date[1], date[2]);
            library.addBook(new Book(columns.get("Book Title"), columns.get("Author"), published, columns.get("Category")));
        }
    }

    @Then("I find {int} books")
    public void verifyAmountOfBooksFound2(final int booksFound) {
        Assertions.assertEquals(result.size(), booksFound);
    }

}
