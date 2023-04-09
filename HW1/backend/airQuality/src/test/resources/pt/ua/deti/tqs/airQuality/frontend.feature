Feature: Search for Air Quality and Weather statistics
  As a user
  I want to search for air quality and weather statistics
  So that I can view the current conditions

  Background:
    Given I am on the homepage

  Scenario: Search for Air Quality
    When I click on the Search Air Quality button
    And I enter "Ovar" in the city field
    And I enter "Portugal" in the country field
    And I click the search button
    Then I should see the air quality data

  Scenario: Search for Weather
    When I click on the search weather button
    And I select "Portugal" in the country field
    And I select "Aveiro" in the state field
    And I select "Agueda" in the city field
    And I search for the weather
    Then I should see the weather data

#  Scenario: View statistics
#    When I click on the "API" button
#    Then I should be redirected to the API statistics page
#    And I should see the relevant API statistics
#
#    When I click on the "Air Quality" button
#    Then I should be redirected to homepage.
#
#    When I click on the "Cache" button
#    Then I should be redirected to the Cache statistics page
#    And I should see the relevant Cache statistics
#
#    When I click on the "Air Quality" button
#    Then I should be redirected to homepage.
#
#    When I click on the "Controller" button
#    Then I should be redirected to the Controller statistics page
#    And I should see the relevant Controller statistics
#
#    When I am finished viewing the statistics
#    Then I should click on the "Air Quality"    button to return to the home page.