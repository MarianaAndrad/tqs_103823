package pt.ua.deti.tqs.airQuality.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ApiStatisticPage {

    private WebDriver driver;

    @FindBy(linkText = "Air Quality")
    private WebElement homePageLink;

    @FindBy(css = ".stats:nth-child(3) > .stat:nth-child(1) .swap-on")
    private WebElement element;

    public ApiStatisticPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public HomePage clickHomePageLink() {
        homePageLink.click();
        return new HomePage(driver);
    }

    public Boolean isElementPresent() {
        return element.isDisplayed();
    }

    public Boolean isHomePageLinkDisplayed() {
        return homePageLink.isDisplayed();
    }


    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
