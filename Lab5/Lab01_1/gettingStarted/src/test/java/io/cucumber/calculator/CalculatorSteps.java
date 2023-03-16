package io.cucumber.calculator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorSteps {
    private Calculator calc;

    //@Given("^a calculator I just turned on$")
    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
    }

    // @When("^I add (\\d+) and (\\d+)$")
    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    //@When("^I substract (\\d+) to (\\d+)$")
    @When("I substract {int} to {int}")
    public void substract(int arg1, int arg2) {
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }

    //@Then("^the result is (\\d+)$")
    @Then("the result is {int}")
    public void the_result_is(double expected) {
        assertEquals(expected, calc.value());
    }

}
