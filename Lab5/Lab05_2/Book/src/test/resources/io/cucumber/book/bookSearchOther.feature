Feature: Book Search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Background: The library has a collection of books
  Background: The library has a collection of books
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-04-14
    And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-08-23
    And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2021-01-01
    And another book with the title "The Hitchhiker's Guide to the Galaxy", written by 'Douglas Adams', published in 1979-10-12
    And another book with the title 'The Catcher in the Rye', written by 'J.D. Salinger', published in 1951-07-16
    And another book with the title 'The Great Gatsby', written by 'F. Scott Fitzgerald', published in 1925-04-10
    And another book with the title 'Pride and Prejudice', written by 'Jane Austen', published in 1813-01-28
    And another book with the title 'The Hobbit', written by 'J.R.R. Tolkien', published in 1937-09-21


  Scenario: Search books by publication year
    When the customer searches for books published between 2013-01-01 and 2014-12-31
    Then 2 books should have been found
    And Book 1 should have the title 'Some other book'
    And Book 2 should have the title 'One good book'

  Scenario: Search books by author
    When the customer searches for books by author 'Anonymous'
    Then 1 book should have been found
    And Book 1 should have the title 'One good book'

  Scenario: Search books by title
    When the customer searches for books by title "The Hitchhiker's Guide to the Galaxy"
    Then 1 book should have been found
    And Book 1 should have the author 'Douglas Adams'



