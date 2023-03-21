Feature: Buy Trip

  Scenario: Successful purchase1
    Given I navigate to 'https://blazedemo.com'
    Then I should be shown the 'BlazeDemo' page
    When I select a departure city 'Boston'
    And I select a destination city 'London'
    And I click on 'Find Flights' button

    Then I should be shown the 'BlazeDemo - reserve' page
    When I choose the flight 3 and click on Choose This Flight button

    Then I should be shown the 'BlazeDemo Purchase' page
    When I enter the 'inputName' as 'John Doe'
    And I enter the 'address' as '123 Main St'
    And I enter the 'city' as 'Boston'
    And I enter the 'state' as 'MA'
    And I enter the 'zipCode' as '02134'
    And I select the 'cardType' as 'Visa'
    And I enter the 'creditCardNumber' as '1234567890123456'
    And I enter the 'creditCardMonth' as '12'
    And I enter the 'creditCardYear' as '2020'
    And I enter the 'nameOnCard' as 'John Doe'
    And I filled in a checkbox Remember me
    And I click on 'Purchase Flight' button

    Then I should be shown the 'BlazeDemo Confirmation' page