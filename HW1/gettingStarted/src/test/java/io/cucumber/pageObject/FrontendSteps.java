package io.cucumber.pageObject;
//
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import io.github.bonigarcia.wdm.WebDriverManager;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
public class FrontendSteps {
//    private WebDriver driver;
//
//    private HomePage homePage;
//    private AirQualityPage airQualityPage;
//    private WeatherPage weatherPage;
//    private ApiStatisticPage apiStatisticPage;
//    private ControllerStatisticPage controllerStatisticPage;
//    private CacheStatisticPage cacheStatisticPage;
//
//    private String baseUrl = "http://localhost:3000/";
//
//    @Given("I am on the homepage")
//    public  void iAmOnTheHomepage() {
//        driver = WebDriverManager.firefoxdriver().create();
//        homePage = new HomePage(driver);
//        homePage.open();
//    }
//
//    @When("I click on the Search button in card Air Quality Search")
//    public void iClickOnTheSearchButtonInCardAirQualitySearch() {
//        assertTrue(homePage.isAirQualityButtonDisplayed());
//        assertTrue(homePage.isAirQualityLinkDisplayed());
//        airQualityPage = homePage.clickAirQualityButton();
//    }
//
//    @And("I search for {string} in {string}")
//    public void iSearchFor(String location, String country) {
//        airQualityPage.search(location, country);
//    }
//
//    @Then("Then I should see the page {string}")
//    public void thenIShouldSeeThePage(String title) {
//        if (title.equals("Air Quality Search")) {
//            assertTrue(airQualityPage.getUrl().contains("airquality"));
//        }
//    }
//
//    @Then("I should see the air quality index")
//    public void iShouldSeeTheAirQualityIndex() {
//        assertTrue(airQualityPage.getAirQualityIndex());
//    }
//
}
