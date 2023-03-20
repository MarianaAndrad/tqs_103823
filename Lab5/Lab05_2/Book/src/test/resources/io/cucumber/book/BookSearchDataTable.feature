Feature: Book Search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Scenario: Correct non-zero number of books found by category
    Given I have the following books in the store
      | Book Title | Author | Category | published |
      | The Great Gatsby | F. Scott Fitzgerald | Classic | 1925-04-10 |
      | 1984 | George Orwell | Classic | 1949-06-08 |
      | The Catcher in the Rye | J.D. Salinger | Classic | 1951-07-16 |
      | The Hunger Games | Suzanne Collins | Young Adult | 2008-09-14 |
    When the customer searches for books published between 1950-01-01 and 2010-12-31
    Then I find 2 books

  Scenario: Search books by author with no match
    Given I have the following books in the store
      | Book Title | Author | Category | published |
      | The Devil in the White City | Erik Larson | Crime | 2003-01-01|
      | The Lion, the Witch and the Wardrobe | C.S. Lewis | Fantasy |1950-01-01|
      | In the Garden of Beasts | Erik Larson | History | 2011-03-02 |
    When the customer searches for books by author 'Erik Larson'
    Then I find 2 books

  Scenario: Search books by title with no match
    Given I have the following books in the store
      | Book Title | Author | Category | published |
      | The Devil in the White City | Erik Larson | Crime | 2003-01-01|
      | The Lion, the Witch and the Wardrobe | C.S. Lewis | Fantasy |1950-01-01|
      | In the Garden of Beasts | Erik Larson | History | 2011-03-02 |
    When the customer searches for books by title 'In the Garden of Beasts'
    Then I find 1 books

  Scenario: Search books by category with no match
    Given I have the following books in the store
      | Book Title | Author | Category | published |
      | The Great Gatsby | F. Scott Fitzgerald | Classic | 1925-04-10 |
      | 1984 | George Orwell | Classic | 1949-06-08 |
      | The Catcher in the Rye | J.D. Salinger | Classic |  1951-07-16 |
      | The Hunger Games | Suzanne Collins | Young Adult | 2008-09-14 |
    When the customer searches for books in the 'Classic' category
    Then I find 3 books

  Scenario: Search books by author and title
    Given I have the following books in the store
      | Book Title | Author | Category | published |
      | The Da Vinci Code | Dan Brown | Thriller | 2003-04-05 |
      | The Hitchhiker's Guide | Douglas Adams | Science Fiction | 1979-10-11 |
      | The Godfather | Mario Puzo | Crime Fiction | 1969-03-05 |
      | The Silence of the Lambs | Thomas Harris | Thriller | 1988-02-04|
    When the customer searches for books by author 'Dan Brown' and by title 'The Da'
    Then I find 1 books

  Scenario: Search books by multiple filters
    Given I have the following books in the store
      | Book Title | Author | Category | published |
      | The Great Gatsby | F. Scott Fitzgerald | Classic | 1925-04-10 |
      | 1984 | George Orwell | Classic | 1949-06-08 |
      | The Catcher in the Rye | J.D. Salinger | Classic | 1951-07-16 |
      | The Hunger Games | Suzanne Collins | Young Adult | 2008-09-14 |
    When the customer searches for books written by 'J.D. Salinger' and published before 1960-01-01 and categorized as 'Classic'
    Then I find 1 books