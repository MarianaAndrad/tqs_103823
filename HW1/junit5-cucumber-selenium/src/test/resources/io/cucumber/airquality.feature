Feature: Test Website Functionality

  Scenario Outline: Search for air quality information in a location
    Given I am on the homepage
    When I click on the Search button in card Air Quality Search
    Then I should see the page "Air Quality Search"
    And I search for "<location>" in "<country>"
    Then I should see the air quality index
    And I should see the subtitle

    Examples:
      | location | country  |
      | Ovar     | Portugal |

  Scenario Outline: Search for weather information in a location
    Given I am on the homepage
    When I click on the Weather button
    Then I should see the page "Weather Search"
    And I search for "<city>" in "<state>", "<country>"
    Then I should see the country as "<country>"
    And I should see the city as "<city>"
    And I should see the state as "<state>"
    And I should see the previous page button

    Examples:
      | city    | state  | country |
      | Agueda  | Aveiro | Portugal |


  Scenario: Check API, Controller and Cache statistics
    Given I am on the homepage
    Then I should see the "API" statistics button in card Statistics Data
    And I should see the "Controller" statistics button in card Statistics Data
    And I should see the "Cache" statistics button in card Statistics Data
    When I click on the "API" statistics button in card Statistics Data
    Then I should see the API statistics page
    And I should see a link to go back to the homepage in "Api" statistics page
    When I click on the link to go back to the homepage in "Api" statistics page
    Then I should be redirected to the homepage
    When I click on the "Controller" statistics button in card Statistics Data
    Then I should see the Controller statistics page
    And I should see a link to go back to the homepage in "Controller" statistics page
    When I click on the link to go back to the homepage in "Controller" statistics page
    Then I should be redirected to the homepage
    When I click on the "Cache" statistics button in card Statistics Data
    Then I should see the Cache statistics page
    And I should see a link to go back to the homepage in "Cache" statistics page
    When I click on the link to go back to the homepage in "Cache" statistics page
    Then I should be redirected to the homepage

