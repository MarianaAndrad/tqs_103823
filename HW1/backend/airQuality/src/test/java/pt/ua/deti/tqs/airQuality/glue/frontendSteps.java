package pt.ua.deti.tqs.airQuality.glue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.DockerComposeContainer;
import pt.ua.deti.tqs.airQuality.pageObject.*;

import java.net.MalformedURLException;
import java.net.URL;

public class frontendSteps {
    private static final DockerComposeContainer environment = new DockerComposeContainer(new java.io.File("../../docker-compose.test.yml"))
            .withBuild(true);

    private RemoteWebDriver driver;
    private HomePage homePage;
    private AirQualityPage airQualityPage;
    private WeatherPage weatherPage;
    private ControllerStatisticPage controllerStatisticPage;
    private ApiStatisticPage apiStatisticPage;
    private CacheStatisticPage cacheStatisticPage;

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
        controllerStatisticPage = new ControllerStatisticPage(driver);
        apiStatisticPage = new ApiStatisticPage(driver);
        cacheStatisticPage = new CacheStatisticPage(driver);
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

    @When("I go to the api statistics page")
    public void iGoToTheApiStatisticsPage() {
        homePage.clickApiStatisticButton();
    }

    @Then("I see relevant statistics about api calls")
    public void iSeeRelevantStatisticsAboutApiCalls() {
        apiStatisticPage.assertApiStatistics();
    }

    @When("I go to the cache statistics page")
    public void iGoToTheCacheStatisticsPage() {
        homePage.clickCacheStatisticButton();
    }

    @Then("I see relevant statistics about cache calls")
    public void iSeeRelevantStatisticsAboutCacheCalls() {
        cacheStatisticPage.assertCacheStatistics();
    }

    @When("I go to the controller statistics page")
    public void iGoToTheControllerStatisticsPage() {
        homePage.clickControllerStatisticButton();
    }

    @Then("I see relevant statistics about controller calls")
    public void iSeeRelevantStatisticsAboutControllerCalls() {
        controllerStatisticPage.assertControllerStatistics();
    }

    @When("I go to the homepage")
    public void iGoToTheHomepage() {
        homePage.clickHomeButton();
    }
}
