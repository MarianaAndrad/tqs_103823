package pt.ua.deti.tqs.airQuality;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.containers.DockerComposeContainer;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@ExtendWith(SeleniumJupiter.class)
public class FrontendIT {
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
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(1850, 1053));
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
        driver.findElement(By.name("city")).sendKeys("Ovar");
        driver.findElement(By.name("country")).click();
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
    @DisplayName("Test Statistics")
    void testStatistic() {
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(1850, 1053));
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
        driver.findElement(By.cssSelector(".btn-primary:nth-child(1)")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".stats:nth-child(3) > .stat:nth-child(1) .swap-on"));
            assert(elements.size() > 0);
        }


        driver.executeScript("document.querySelector('a[href=\"/\"]').click()");
        driver.executeScript("document.querySelector('a[href=\"/statistics/cache\"]').click()");
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
        driver.findElement(By.cssSelector(".flex > .btn:nth-child(3)")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".mb-6"));
            assert(elements.size() > 0);
        }
    }

    @Test
    @DisplayName("Test Search Weather")
    void testSearchWeather() {
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(1850, 1053));
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".card:nth-child(2) .btn"));
            assert(elements.size() > 0);
        }
        {
            List<WebElement> elements = driver.findElements(By.linkText("Air Quality"));
            assert(elements.size() > 0);
        }
        driver.findElement(By.cssSelector(".card:nth-child(2) .btn")).click();
        driver.findElement(By.name("country")).click();
        {

            WebDriverWait wait;
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(webDriver -> webDriver.findElement(By.xpath("//option[. = 'Portugal']")).getText().equals("Portugal"));
            WebElement dropdown = driver.findElement(By.name("country"));
            dropdown.findElement(By.xpath("//option[. = 'Portugal']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(110)")).click();
        driver.findElement(By.name("state")).click();
        {
            WebElement dropdown = driver.findElement(By.name("state"));
            dropdown.findElement(By.xpath("//option[. = 'Aveiro']")).click();
        }
        driver.findElement(By.cssSelector(".select:nth-child(3) > option:nth-child(2)")).click();
        driver.findElement(By.name("city")).click();
        {
            WebElement dropdown = driver.findElement(By.name("city"));
            dropdown.findElement(By.xpath("//option[. = 'Agueda']")).click();
        }
        driver.findElement(By.cssSelector(".mb-4 > option:nth-child(2)")).click();
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
        driver.findElement(By.cssSelector(".btn-outline")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".btn-outline"));
            assert(elements.size() > 0);
        }
    }
}