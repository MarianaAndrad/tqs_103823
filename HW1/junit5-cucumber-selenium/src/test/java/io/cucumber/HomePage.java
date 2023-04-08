package io.cucumber;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "Air Quality")
    private WebElement airQualityLink;

    @FindBy(css = ".card:nth-child(1) > .card-body > .btn")
    private WebElement airQualityButton;

    @FindBy(css = ".card:nth-child(2) .btn")
    private WebElement weatherButton;

    @FindBy(css =".btn-primary:nth-child(1)")
    private WebElement apiStatisticButton;

    @FindBy(css =".btn:nth-child(2)")
    private WebElement controllerStatisticButton;

    @FindBy(css =".flex > .btn:nth-child(3)")
    private WebElement cacheStatisticButton;


    public void open() {
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(1850, 1053));
    }

    public AirQualityPage clickAirQualityButton() {
        airQualityButton.click();
        return new AirQualityPage(driver);
    }

    public WeatherPage clickWeatherButton() {
        weatherButton.click();
        return new WeatherPage(driver);
    }

    public ApiStatisticPage clickApiStatisticButton() {
        apiStatisticButton.click();
        return new ApiStatisticPage(driver);
    }

    public ControllerStatisticPage clickControllerStatisticButton() {
        controllerStatisticButton.click();
        return new ControllerStatisticPage(driver);
    }

    public CacheStatisticPage clickCacheStatisticButton() {
        cacheStatisticButton.click();
        return new CacheStatisticPage(driver);
    }

    public boolean isAirQualityLinkDisplayed() {
        return airQualityLink.isDisplayed();
    }

    public Boolean isAirQualityButtonDisplayed() {
        return airQualityButton.isDisplayed();
    }

    public Boolean isWeatherButtonDisplayed() {
        return weatherButton.isDisplayed();
    }

    public Boolean isApiStatisticButtonDisplayed() {
        return apiStatisticButton.isDisplayed();
    }

    public Boolean isControllerStatisticButtonDisplayed() {
        return controllerStatisticButton.isDisplayed();
    }

    public Boolean isCacheStatisticButtonDisplayed() {
        return cacheStatisticButton.isDisplayed();
    }


    public String getUrl() {
        return driver.getCurrentUrl();
    }
}


