package pt.ua.deti.tqs.airQuality.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CacheStatisticPage {
    private WebDriver driver;

    @FindBy(css=".mb-6")
    private WebElement element;

    @FindBy(linkText = "Air Quality")
    private WebElement homePageLink;

    public CacheStatisticPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

public HomePage clickHomePageLink() {
        homePageLink.click();
        return new HomePage(driver);
    }

    public Boolean isHomePageLinkDisplayed() {
        return homePageLink.isDisplayed();
    }

    public Boolean isElementPresent() {
        return element.isDisplayed();
    }

    public void assertCacheStatistics() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> isElementPresent());
    }
}
