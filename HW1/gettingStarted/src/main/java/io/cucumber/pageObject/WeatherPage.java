package pt.ua.deti.tqs.airQuality.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Collection;

public class WeatherPage {
    private WebDriver driver;

    public WeatherPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "country")
    private WebElement countryInput;

    @FindBy(name = "state")
    private WebElement stateInput;

    @FindBy(name = "city")
    private WebElement cityInput;

    @FindBy(css = ".mb-4 > option:nth-child(2)")
    private WebElement conditionOption;


    @FindBy(css = ".btn-outline")
    private WebElement previusPageButton;

    public void search(String country, String state, String city) {
        countryInput.click();
        Select countryDropdown = new Select(countryInput);
        countryDropdown.selectByVisibleText(country);

        stateInput.click();
        Select stateDropdown = new Select(stateInput);
        stateDropdown.selectByVisibleText(state);

        cityInput.click();
        Select cityDropdown = new Select(cityInput);
        cityDropdown.selectByVisibleText(city);

        conditionOption.click();
    }

    public String getCountry() {
        return countryInput.getAttribute("value");
    }

    public String getState() {
        return stateInput.getAttribute("value");
    }

    public String getCity() {
        return cityInput.getAttribute("value");
    }

    public Boolean isPreviusPageButtonDisplayed() {
        return previusPageButton.isDisplayed();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
