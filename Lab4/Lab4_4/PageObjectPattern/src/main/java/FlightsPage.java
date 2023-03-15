import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class FlightsPage {
    private WebDriver driver;

    By subtitle = By.cssSelector("h3");

    public FlightsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public String getTitle() {
        return driver.getTitle();
    }
    public String getSubTitle() {
        return driver.findElement(subtitle).getText();
    }

    public int numberOfFlights() {
        List<WebElement> flights = driver.findElements(By.cssSelector("input[type='submit']"));
        return flights.size();
    }

    public void chooseFirstFlight(int flightNumber) {
        List<WebElement> flights = driver.findElements(By.cssSelector("input[type='submit']"));
        flights.get(flightNumber).click();
    }



}
