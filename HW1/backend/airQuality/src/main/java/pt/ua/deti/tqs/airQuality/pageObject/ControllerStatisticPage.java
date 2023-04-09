package pt.ua.deti.tqs.airQuality.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ControllerStatisticPage {
    private final WebDriver driver;

    @FindBy(linkText = "Air Quality")
    private WebElement homePageLink;

    @FindBy(css=".mb-6")
    private WebElement element;


    public ControllerStatisticPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Boolean isHomePageLinkDisplayed() {
        return homePageLink.isDisplayed();
    }

    public HomePage clickHomePageLink() {
        homePageLink.click();
        return new HomePage(driver);
    }


    public void assertControllerStatistics() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> element.isDisplayed());
    }
}
