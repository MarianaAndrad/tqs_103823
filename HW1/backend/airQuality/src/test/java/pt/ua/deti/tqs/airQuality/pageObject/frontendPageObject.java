package pt.ua.deti.tqs.airQuality.pageObject;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.DockerComposeContainer;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumJupiter.class)
public class frontendPageObject {
    private final static String DOCKER_COMPOSE_LOCATION = "../../docker-compose.test.yml";

    private static final DockerComposeContainer environment = new DockerComposeContainer(new java.io.File(DOCKER_COMPOSE_LOCATION))
            .withBuild(true);

    private RemoteWebDriver driver;

    @BeforeAll
    static void beforeAll() {
        environment.start();
    }

    @BeforeEach
    void setup() throws MalformedURLException {
        URL url = new URL("http://localhost:4444/wd/hub");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        driver = new RemoteWebDriver(url, capabilities);
    }

    @Test
    @DisplayName("Test Search Air Quality")
    void testSearchAirQuality() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        assertTrue(homePage.isAirQualityButtonDisplayed());
        assertTrue(homePage.isAirQualityLinkDisplayed());

        AirQualityPage airQualityPage = homePage.clickAirQualityButton();
        airQualityPage.search("Ovar", "Portugal");
        assertTrue(airQualityPage.getAirQualityIndex());
        assertTrue(airQualityPage.getSubTitle());
    }

    @Test
    @DisplayName("Test Search Weather")
    void testSearchWeather() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        assertTrue(homePage.isWeatherButtonDisplayed());
        assertTrue(homePage.isAirQualityLinkDisplayed());

        WeatherPage weatherPage = homePage.clickWeatherButton();
        weatherPage.search("Portugal", "Aveiro","Agueda");
        String country = weatherPage.getCountry();
        String city = weatherPage.getCity();
        String state = weatherPage.getState();

        assertEquals("Portugal", country);
        assertEquals("Agueda", city);
        assertEquals("Aveiro", state);

        assertTrue(weatherPage.isPreviusPageButtonDisplayed());
    }

    @Test
    @DisplayName("Test Statistics")
    void testStatistics() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        assertTrue(homePage.isApiStatisticButtonDisplayed());
        assertTrue(homePage.isControllerStatisticButtonDisplayed());
        assertTrue(homePage.isCacheStatisticButtonDisplayed());

        ApiStatisticPage apiStatisticPage = homePage.clickApiStatisticButton();
        assertTrue(apiStatisticPage.isElementPresent());
        assertTrue(apiStatisticPage.isHomePageLinkDisplayed());
        homePage = apiStatisticPage.clickHomePageLink();

        ControllerStatisticPage controllerStatisticPage = homePage.clickControllerStatisticButton();
        assertTrue(controllerStatisticPage.isHomePageLinkDisplayed());
        homePage = controllerStatisticPage.clickHomePageLink();

        CacheStatisticPage cacheStatisticPage = homePage.clickCacheStatisticButton();
        assertTrue(cacheStatisticPage.isHomePageLinkDisplayed());
        assertTrue(cacheStatisticPage.isElementPresent());
        homePage = cacheStatisticPage.clickHomePageLink();

    }

}
