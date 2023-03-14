import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumJupiter.class)
public class buyTripC {
    @Test
    void buyTripTest(FirefoxDriver driver) {
        // 1 | open | / |
        driver.get("https://blazedemo.com/");
        // 2 | assertTitle | BlazeDemo |
        assertThat(driver.getTitle(), is("BlazeDemo"));
        // 3 | assertElementPresent | css=.btn-primary | value="Find Flights"
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".btn-primary"));
            assert(elements.size() > 0);
        }
        // 4 | assertText | css=h1 | Welcome to the Simple Travel Agency!
        assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Welcome to the Simple Travel Agency!"));
        // 5 | setWindowSize | 896x1016 |
        driver.manage().window().setSize(new Dimension(896, 1016));
        // 6 | click | name=fromPort |
        driver.findElement(By.name("fromPort")).click();
        // 7 | select | name=fromPort | label=Boston
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = 'Boston']")).click();
        }
        // 8 | click | css=.form-inline:nth-child(1) > option:nth-child(3) |
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(3)")).click();
        // 9 | click | name=toPort |
        driver.findElement(By.name("toPort")).click();
        // 10 | select | name=toPort | label=Rome
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'Rome']")).click();
        }
        // 11 | click | css=.form-inline:nth-child(4) > option:nth-child(2) |
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(2)")).click();
        // 12 | click | css=.btn-primary |
        driver.findElement(By.cssSelector(".btn-primary")).click();
        // 13 | assertElementPresent | css=tr:nth-child(1) .btn |
        {
            List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(1) .btn"));
            assert(elements.size() > 0);
        }
        // 14 | assertElementPresent | css=tr:nth-child(2) .btn |
        {
            List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(2) .btn"));
            assert(elements.size() > 0);
        }
        // 15 | assertElementPresent | css=tr:nth-child(3) .btn |
        {
            List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(3) .btn"));
            assert(elements.size() > 0);
        }
        // 16 | assertElementPresent | css=tr:nth-child(4) .btn |
        {
            List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(4) .btn"));
            assert(elements.size() > 0);
        }
        // 17 | assertElementPresent | css=tr:nth-child(5) .btn |
        {
            List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(5) .btn"));
            assert(elements.size() > 0);
        }
        // 18 | click | css=tr:nth-child(1) .btn |
        driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
        // 19 | click | id=inputName |
        driver.findElement(By.id("inputName")).click();
        // 20 | type | id=inputName | Darcy
        driver.findElement(By.id("inputName")).sendKeys("Darcy");
        // 21 | click | id=address |
        driver.findElement(By.id("address")).click();
        // 22 | type | id=address | 226 causeway ST STE 9
        driver.findElement(By.id("address")).sendKeys("226 causeway ST STE 9");
        // 23 | click | id=city |
        driver.findElement(By.id("city")).click();
        // 24 | type | id=city | Boston
        driver.findElement(By.id("city")).sendKeys("Boston");
        // 25 | click | id=state |
        driver.findElement(By.id("state")).click();
        // 26 | type | id=state | Boston
        driver.findElement(By.id("state")).sendKeys("Boston");
        // 27 | click | id=zipCode |
        driver.findElement(By.id("zipCode")).click();
        // 28 | type | id=zipCode | 02114-2158
        driver.findElement(By.id("zipCode")).sendKeys("02114-2158");
        // 29 | click | id=cardType |
        driver.findElement(By.id("cardType")).click();
        // 30 | select | id=cardType | label=American Express
        {
            WebElement dropdown = driver.findElement(By.id("cardType"));
            dropdown.findElement(By.xpath("//option[. = 'American Express']")).click();
        }
        // 31 | click | css=option:nth-child(2) |
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        // 32 | click | id=creditCardNumber |
        driver.findElement(By.id("creditCardNumber")).click();
        // 33 | type | id=creditCardNumber | 1 800 297 2977
        driver.findElement(By.id("creditCardNumber")).sendKeys("1 800 297 2977");
        // 34 | click | id=creditCardMonth |
        driver.findElement(By.id("creditCardMonth")).click();
        // 35 | type | id=creditCardMonth | 11
        driver.findElement(By.id("creditCardMonth")).sendKeys("11");
        // 36 | click | id=creditCardYear |
        driver.findElement(By.id("creditCardYear")).click();
        // 37 | type | id=creditCardYear | 2023
        driver.findElement(By.id("creditCardYear")).sendKeys("2023");
        // 38 | click | id=nameOnCard |
        driver.findElement(By.id("nameOnCard")).click();
        // 39 | type | id=nameOnCard | Darcy
        driver.findElement(By.id("nameOnCard")).sendKeys("Darcy");
        // 40 | assertValue | id=nameOnCard | Darcy
        {
            String value = driver.findElement(By.id("nameOnCard")).getAttribute("value");
            assertThat(value, is("Darcy"));
        }
        // 41 | click | id=rememberMe |
        driver.findElement(By.id("rememberMe")).click();
        // 42 | verifyChecked | id=rememberMe |
        assertTrue(driver.findElement(By.id("rememberMe")).isSelected());
        // 43 | click | css=.btn-primary |
        driver.findElement(By.cssSelector(".btn-primary")).click();
        // 44 | assertText | css=h1 | Thank you for your purchase today!
        assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Thank you for your purchase today!"));
        // 47 | verifyElementNotPresent | css=.btn-primary |
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".btn-primary"));
            assert(elements.size() == 0);
        }
        // 48 | assertTitle | BlazeDemo Confirmation |
        assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));

    }



}
