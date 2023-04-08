package io.cucumber.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AirQualityPage {
    private WebDriver driver;

    public AirQualityPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(name = "city")
    private WebElement cityInput;

    @FindBy(name = "country")
    private WebElement countryInput;

    @FindBy(css = ".btn-primary")
    private WebElement searchButton;

    @FindBy(css = ".stats:nth-child(1) > .stat-figure .swap-on")
    private WebElement airQualityIndex;

    @FindBy(css = ".text-2xl")
    private WebElement subTitle;

    public void search(String city, String country) {
        cityInput.click();
        cityInput.clear();
        cityInput.sendKeys(city);
        countryInput.click();
        countryInput.clear();
        countryInput.sendKeys(country);
        searchButton.click();
        searchButton.click();
    }

    public Boolean getAirQualityIndex() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> airQualityIndex.isDisplayed());
        return airQualityIndex.isDisplayed();
    }

    public Boolean getSubTitle() {
        return subTitle.isDisplayed();
    }


    public String getUrl() {
        return driver.getCurrentUrl();
    }


}
