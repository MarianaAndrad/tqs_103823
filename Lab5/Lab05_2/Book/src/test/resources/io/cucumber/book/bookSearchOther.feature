Feature: Book Search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Background: The library has a collection of books
  Background: The library has a collection of books
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-04-14
    And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-08-23
    And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2021-01-01
    And another book with the title "The Hitchhiker's Guide to the Galaxy", written by 'Douglas Adams', published in 1979-10-12
    And another book with the title 'The Catcher in the Rye', written by 'J.D. Salinger', published in 1951-07-16 and categorized as 'Fiction'
    And another book with the title 'The Great Gatsby', written by 'F. Scott Fitzgerald', published in 1925-04-10 and categorized as 'Fiction'
    And another book with the title 'Pride and Prejudice', written by 'Jane Austen', published in 1813-01-28 and categorized as 'Fiction'
    And another book with the title 'The Hobbit', written by 'J.R.R. Tolkien', published in 1937-09-21 and categorized as 'Fantasy'
    And another book with the title 'The Lord of the Rings', written by 'J.R.R. Tolkien', published in 1954-07-29 and categorized as 'Fantasy'
    And another book with the title 'The Alchemist', written by 'Paulo Coelho', published in 1988-01-01 and categorized as 'Fiction'
    And another book with the title "Harry Potter and the Philosopher's Stone", written by 'J.K. Rowling', published in 1997-06-26 and categorized as 'Fantasy'
    And another book with the title 'The Girl with the Dragon Tattoo', written by 'Stieg Larsson', published in 2005-08-16 and categorized as 'Mystery'

  Scenario: Search books by publication year
    When the customer searches for books published between 2013-01-01 and 2014-12-31
    Then 2 books should have been found
    And Book 1 should have the title 'Some other book'
    And Book 2 should have the title 'One good book'

  Scenario: Search books by author
    When the customer searches for books by author 'J.R.R. Tolkien'
    Then 2 books should have been found
    And Book 1 should have the title 'The Lord of the Rings'
    And Book 2 should have the title 'The Hobbit'

  Scenario: Search books by title
    When the customer searches for books by title "The Hitchhiker's Guide to the Galaxy"
    Then 1 books should have been found
    And Book 1 should have the author 'Douglas Adams'

  Scenario: Search books by author and title
    When the customer searches for books by author 'J.R.R. Tolkien' and by title 'The'
    Then 2 books should have been found
    And Book 1 should have the title 'The Lord of the Rings'
    And Book 2 should have the title 'The Hobbit'

  Scenario: Search books by category1
    Given a book with the title 'The Hunger Games', written by 'Suzanne Collins', published in 2008-09-14 and categorized as 'Young Adult Fiction'
    And another book with the title 'The Fault in Our Stars', written by 'John Green', published in 2012-01-10 and categorized as 'Young Adult Fiction'
    When the customer searches for books in the 'Young Adult Fiction' category
    Then 2 books should have been found
    And Book 1 should have the title 'The Fault in Our Stars'
    And Book 2 should have the title 'The Hunger Games'

  Scenario: Search books by category2
    When the customer searches for books in the 'Fiction' category
    Then 4 books should have been found
    And Book 1 should have the title 'The Alchemist'
    And Book 2 should have the title 'The Catcher in the Rye'
    And Book 3 should have the title 'The Great Gatsby'
    And Book 4 should have the title 'Pride and Prejudice'

    Scenario: Search books by title and category
        When the customer searches for books by title 'The' and in the 'Fiction' category
        Then 3 books should have been found
        And Book 1 should have the title 'The Alchemist'
        And Book 2 should have the title 'The Catcher in the Rye'
        And Book 3 should have the title 'The Great Gatsby'

    Scenario: Search books by author and category
        When the customer searches for books by author 'J.R.R. Tolkien' and in the 'Fantasy' category
        Then 2 books should have been found
        And Book 1 should have the title 'The Lord of the Rings'
        And Book 2 should have the title 'The Hobbit'

    Scenario: Search books by multiple filters
      When the customer searches for books written by 'J.R.R. Tolkien' and published before 1940-01-01 and categorized as 'Fantasy'
      Then 1 books should have been found
      And Book 1 should have the title 'The Hobbit'












