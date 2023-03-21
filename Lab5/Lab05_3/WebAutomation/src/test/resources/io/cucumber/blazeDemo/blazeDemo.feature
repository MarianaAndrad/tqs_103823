Feature: Buy Trip

  Scenario: Successful purchase
    Given I navigate to 'https://blazedemo.com'
    Then I should be shown the 'BlazeDemo' page
    When I select a departure city 'Boston'
    And I select a destination city 'London'
    And I click on 'Find Flights'

    Then I should be shown the '---' page
    When I select the 'United Airlines' flight
    And I click on 'Choose This Flight'

    Then I should be shown the '---' page
    When I enter the 'Name' as 'John Doe'
    And I enter the 'Address' as '123 Main St'
    And I enter the 'City' as 'Boston'
    And I enter the 'State' as 'MA'
    And I enter the 'Zip Code' as '02134'
    And I select the 'Card Type' as 'Visa'
    And I enter the 'Credit Card Number' as '1234567890123456'
    And I enter the 'Credit Card Month' as '12'
    And I enter the 'Credit Card Year' as '2020'
    And I enter the 'Name on Card' as 'John Doe'
    And I click on 'Remember Me'
    And I click on 'Purchase Flight'

    Then I should be shown the 'BlazeDemo Confirmation' page