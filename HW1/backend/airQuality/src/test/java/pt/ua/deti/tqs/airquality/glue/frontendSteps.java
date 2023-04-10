package pt.ua.deti.tqs.airquality.glue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.DockerComposeContainer;
import pt.ua.deti.tqs.airquality.pageobject.AirQualityPage;
import pt.ua.deti.tqs.airquality.pageobject.HomePage;
import pt.ua.deti.tqs.airquality.pageobject.WeatherPage;

import java.net.MalformedURLException;
import java.net.URL;

public class frontendSteps {
    private static final DockerComposeContainer environment = new DockerComposeContainer(new java.io.File("../../docker-compose.test.yml"))
            .withBuild(true);
    private RemoteWebDriver driver;
    private HomePage homePage;
    private AirQualityPage airQualityPage;
    private WeatherPage weatherPage;
    @Given("I am on the homepage")
    public void iAmOnTheHomepage() throws MalformedURLException {
        environment.stop();
        environment.start();
        URL url = new URL("http://localhost:4444/wd/hub");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        capabilities.setCapability("enableVNC", true);
        driver = new RemoteWebDriver(url, capabilities);

        driver.get("http://frontend:3000");

        homePage = new HomePage(driver);
        airQualityPage = new AirQualityPage(driver);
        weatherPage = new WeatherPage(driver);
    }

    @When("I click on the Search Air Quality button")
    public void iClickOnTheButton() {
        homePage.clickAirQualityButton();
    }

    @And("I enter {string} in the city field")
    public void iEnterInTheCity(String city) {
        airQualityPage.insertCity(city);
    }

    @And("I enter {string} in the country field")
    public void iEnterInTheCountry(String country) {
        airQualityPage.insertCountry(country);
    }

    @And("I click the search button")
    public void iTwoClickTheButton() {
        airQualityPage.search();
    }

    @Then("I should see {string} and {string} in the air quality data")
    public void iShouldSeeTheAirQualityData(String city, String country) {
        airQualityPage.assertData(city, country);
    }

    @When("I click on the search weather button")
    public void iClickOnTheSearchWeatherButton() {
        homePage.clickWeatherButton();
    }

    @And("I select {string} in the country field")
    public void iSelectInTheWeatherCountryField(String country) {
        weatherPage.selectCountry(country);
    }

    @And("I select {string} in the state field")
    public void iSelectInTheStateField(String state) {
        weatherPage.selectState(state);
    }

    @And("I select {string} in the city field")
    public void iSelectInTheCityField(String city) {
        weatherPage.selectCity(city);
    }

    @And("I search for the weather")
    public void iSearchForTheWeather() {
        weatherPage.search();
    }

    @Then("I should see {string}, {string} and {string} in the weather data")
    public void iShouldSeeTheWeatherData(String city,String state,String country) {
        weatherPage.assertData(city,state,country);
    }
}
