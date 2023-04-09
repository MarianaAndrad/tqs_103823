package pt.ua.deti.tqs.airQuality.frontend.docker;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.containers.DockerComposeContainer;
import pt.ua.deti.tqs.airQuality.pageObject.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SeleniumJupiter.class)
//@Disabled
public class AllTests {
    private final static String DOCKER_COMPOSE_LOCATION = "../../docker-compose.test.yml";

    private static final DockerComposeContainer environment = new DockerComposeContainer(new java.io.File(DOCKER_COMPOSE_LOCATION))
            .withBuild(true);

    private static RemoteWebDriver driver;

    @BeforeAll
    static void beforeAll() throws MalformedURLException {
        environment.start();
        URL url = new URL("http://localhost:4444/wd/hub");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        capabilities.setCapability("enableVNC", true);
        driver = new RemoteWebDriver(url, capabilities);
    }

    @AfterAll
    static void afterAll() {
        environment.stop();
    }


    @Test
    @DisplayName("Test Search Air Quality")
    void testSearchAirQuality() {
        driver.get("http://frontend:3000/");
        driver.manage().window();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".card:nth-child(1) > .card-body > .btn"));
            assert(elements.size() > 0);
        }
        {
            List<WebElement> elements = driver.findElements(By.linkText("Air Quality"));
            assert(elements.size() > 0);
        }
        driver.findElement(By.cssSelector(".card:nth-child(1) > .card-body > .btn")).click();
        driver.findElement(By.name("city")).click();
        driver.findElement(By.name("city")).clear();
        driver.findElement(By.name("city")).sendKeys("Ovar");
        driver.findElement(By.name("country")).click();
        driver.findElement(By.name("country")).clear();
        driver.findElement(By.name("country")).sendKeys("Portugal");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        {
         List<WebElement> elements = driver.findElements(By.cssSelector(".text-2xl"));
         assert(elements.size() > 0);
        }
        {
         List<WebElement> elements = driver.findElements(By.cssSelector(".stats:nth-child(1) > .stat-figure .swap-on"));
         assert(elements.size() > 0);
        }
    }

    @Test
    @DisplayName("Test Search Weather")
    void testSearchWeather() {
        driver.get("http://frontend:3000/");
        driver.manage().window();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".card:nth-child(2) .btn"));
            assert(elements.size() > 0);
        }
        {
            List<WebElement> elements = driver.findElements(By.linkText("Air Quality"));
            assert(elements.size() > 0);
        }
        driver.findElement(By.cssSelector(".card:nth-child(2) .btn")).click();
        {
            WebElement dropdown = driver.findElement(By.name("country"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(webDriver -> dropdown.findElement(By.xpath("//option[. = 'Portugal']")).isDisplayed());
            dropdown.findElement(By.xpath("//option[. = 'Portugal']")).click();
        }
        {
            // driver.findElement(By.name("state")).click();
            WebElement dropdown = driver.findElement(By.name("state"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(webDriver -> dropdown.findElement(By.xpath("//option[. = 'Aveiro']")).isDisplayed());
            dropdown.findElement(By.xpath("//option[. = 'Aveiro']")).click();
        }
        {
            // driver.findElement(By.name("city")).click();
            WebElement dropdown = driver.findElement(By.name("city"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(webDriver -> dropdown.findElement(By.xpath("//option[. = 'Agueda']")).isDisplayed());
            dropdown.findElement(By.xpath("//option[. = 'Agueda']")).click();
        }

        {
            String value = driver.findElement(By.name("country")).getAttribute("value");
            assertThat(value, is("Portugal"));
        }

        {
            String value = driver.findElement(By.name("state")).getAttribute("value");
            assertThat(value, is("Aveiro"));
        }

        {
            String value = driver.findElement(By.name("city")).getAttribute("value");
            assertThat(value, is("Agueda"));
        }
    }

    @Test
    @DisplayName("Test Statistics")
    void testStatistic() {
        driver.get("http://frontend:3000/");
        driver.manage().window();
        {
            List<WebElement> elements = driver.findElements(By.linkText("Air Quality"));
            assert(elements.size() > 0);
        }
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".btn-primary:nth-child(1)"));
            assert(elements.size() > 0);
        }
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".btn:nth-child(2)"));
            assert(elements.size() > 0);
        }
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".flex > .btn:nth-child(3)"));
            assert(elements.size() > 0);
        }
        driver.executeScript("document.querySelector('a[href=\"/statistics/api\"').click()");

        driver.findElement(By.linkText("Air Quality")).click();
        {
            WebElement element = driver.findElement(By.linkText("Air Quality"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }

        driver.executeScript("document.querySelector('a[href=\"/statistics/cache\"').click()");
        driver.findElement(By.linkText("Air Quality")).click();
        {
            WebElement element = driver.findElement(By.linkText("Air Quality"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.linkText("Air Quality"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }

        driver.executeScript("document.querySelector('a[href=\"/statistics/controller\"').click()");
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".mb-6"));
            assert(elements.size() > 0);
        }
    }

    @Test
    @DisplayName("Test Search Air Quality Page Object")
    void testSearchAirQualityPageObject() {
        driver.get("http://frontend:3000/");
        driver.manage().window();
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
    @DisplayName("Test Search Weather Page Object")
    void testSearchWeatherPageObject() {
        driver.get("http://frontend:3000/");
        driver.manage().window();
        HomePage homePage = new HomePage(driver);
        homePage.open();
        assertTrue(homePage.isWeatherButtonDisplayed());
        assertTrue(homePage.isAirQualityLinkDisplayed());


        WeatherPage weatherPage = homePage.clickWeatherButton();
        weatherPage.selectCountry("Portugal");
        weatherPage.selectState("Aveiro");
        weatherPage.selectCity("Agueda");
        weatherPage.clickSearchButton();
        String country = weatherPage.getCountry();
        String city = weatherPage.getCity();
        String state = weatherPage.getState();

        assertEquals("Portugal", country);
        assertEquals("Agueda", city);
        assertEquals("Aveiro", state);

        assertTrue(weatherPage.isPreviusPageButtonDisplayed());
    }

    @Test
    @DisplayName("Test Statistics Page Object")
    void testStatisticsPageObject() {
        driver.get("http://frontend:3000/");
        driver.manage().window();
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
