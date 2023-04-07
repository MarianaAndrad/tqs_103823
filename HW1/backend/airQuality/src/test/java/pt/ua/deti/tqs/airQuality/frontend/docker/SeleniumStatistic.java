package pt.ua.deti.tqs.airQuality.frontend.docker;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.DockerComposeContainer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@ExtendWith(SeleniumJupiter.class)
public class SeleniumStatistic {
    private final static String DOCKER_COMPOSE_LOCATION = "../../docker-compose.test.yml";

    private static final DockerComposeContainer environment = new DockerComposeContainer(new java.io.File(DOCKER_COMPOSE_LOCATION))
            .withBuild(true);

    private RemoteWebDriver driver;

    @BeforeAll
    static void beforeAll() {
        environment.start();
    }

    @AfterAll
    static void afterAll() {
        environment.stop();
    }

    @BeforeEach
    void setup() throws MalformedURLException {
        URL url = new URL("http://localhost:4444/wd/hub");
                DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        driver = new RemoteWebDriver(url, capabilities);
    }

    @Test
    @DisplayName("Test Statistics")
    void testStatistic() {
        driver.get("http://frontend:3000/");
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
}
