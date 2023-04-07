package pt.ua.deti.tqs.airQuality.frontend;
// Generated by Selenium IDE

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.List;


@ExtendWith(SeleniumJupiter.class)
@Disabled
public class TestStastisticTest {
  @Test
  void testStastistic(FirefoxDriver driver) {
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
}
