package pt.ua.deti.tqs.airQuality.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
    private WebElement buttonsearch;

    @FindBy(xpath = "/html/body/div/div/div[2]/div/div/button")
    private WebElement conditionOption;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[1]/div[2]/div[2]/div[2]")
    private WebElement cityLocation;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div[1]/div[2]/div[2]/div[3]")
    private WebElement countryAndStateLocation;

    @FindBy(css = ".btn-outline")
    private WebElement previusPageButton;

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

    public void selectCountry(String country) {
        countryInput.click();
        Select countryDropdown = new Select(countryInput);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> countryDropdown.getOptions().size() > 1);
        countryDropdown.selectByVisibleText(country);
    }

    public void selectState(String state) {
        stateInput.click();
        Select stateDropdown = new Select(stateInput);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> stateDropdown.getOptions().size() > 1);
        stateDropdown.selectByVisibleText(state);
    }

    public void selectCity(String city) {
        cityInput.click();
        Select cityDropdown = new Select(cityInput);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> cityDropdown.getOptions().size() > 1);
        cityDropdown.selectByVisibleText(city);
    }

    public void search() {

        conditionOption.click();
    }

    public void assertData(String city, String state, String country) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(webDriver -> previusPageButton.isDisplayed());
        wait.until(webDriver -> cityLocation.getText().contains(city));
        wait.until(webDriver -> countryAndStateLocation.getText().contains(state));
        wait.until(webDriver -> countryAndStateLocation.getText().contains(country));
    }

    public void clickSearchButton() {
        buttonsearch.click();
    }
}
