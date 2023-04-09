package pt.ua.deti.tqs.airQuality.pageObject;

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

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[2]")
    private WebElement cityLocation;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[3]")
    private WebElement countryLocation;

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


    public void insertCity(String city) {
        cityInput.click();
        cityInput.clear();
        cityInput.sendKeys(city);
    }

    public void insertCountry(String country) {
        countryInput.click();
        countryInput.clear();
        countryInput.sendKeys(country);
    }

    public void search() {
        searchButton.click();
        searchButton.click();
    }

    public void assertData(String city, String country) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(webDriver -> airQualityIndex.isDisplayed());
        wait.until(webDriver -> cityLocation.getText().equals(city));
        wait.until(webDriver -> countryLocation.getText().contains(country));
    }
}
