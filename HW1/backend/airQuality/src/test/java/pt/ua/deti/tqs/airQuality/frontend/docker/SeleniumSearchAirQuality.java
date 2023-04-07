package pt.ua.deti.tqs.airQuality.frontend.docker;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.DockerComposeContainer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@ExtendWith(SeleniumJupiter.class)
public class SeleniumSearchAirQuality {
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
    @DisplayName("Test Search Air Quality")
    void testSearchAirQuality() {
        driver.get("http://frontend:3000/");
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
}
