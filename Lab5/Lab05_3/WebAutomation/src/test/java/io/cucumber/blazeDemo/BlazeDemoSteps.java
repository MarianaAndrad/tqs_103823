package io.cucumber.blazeDemo;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BlazeDemoSteps {

    private WebDriver driver;

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
        driver.manage().window().setSize(new Dimension(896, 1016));
    }

    @Then("I should be shown the {string} page")
    public void i_should_be_shown_the_page(String title) {
        assertThat(driver.getTitle()).isEqualTo(title);
    }

    @When("I select a departure city {string}")
    public void i_select_a_departure_city(String departureCity) {
        driver.findElement(By.name("fromPort")).click();
        driver.findElement(By.xpath("//option[. = '" + departureCity + "']")).click();
    }

    @When("I select a destination city {string}")
    public void i_select_a_destination_city(String destinationCity) {
        driver.findElement(By.name("toPort")).click();
        driver.findElement(By.xpath("//option[. = '" + destinationCity + "']")).click();
    }

    @When("I click on {string} button")
    public void i_click_on_button(String button) {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @When("I choose the flight {int} and click on Choose This Flight button")
    public void iChooseFlight(int flightNumber) {
        driver.findElement(By.cssSelector("tr:nth-child("+flightNumber+") .btn")).click();
    }

    @When("I enter the {string} as {string}")
    public void iEnterTheAs(String field, String value) {
        driver.findElement(By.id(field)).click();
        driver.findElement(By.id(field)).sendKeys(value);
    }

    @And("I select the {string} as {string}")
    public void iSelectTheAs(String field, String value) {
        driver.findElement(By.id(field)).click();
        driver.findElement(By.xpath("//option[. = '" + value + "']")).click();
    }

    @And("I filled in a checkbox Remember me")
    public void iFilledInACheckboxRememberMe() {
        driver.findElement(By.id("rememberMe")).click();
    }




}
