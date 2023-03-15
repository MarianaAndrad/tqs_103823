import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class PurchasePage {
    private WebDriver driver;

    @FindBy(css = "h2")
    private WebElement subtitle;

    @FindBy(id = "inputName")
    private WebElement nameInput;

    @FindBy(id = "address")
    private WebElement addressInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "zipCode")
    private WebElement zipCodeInput;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardInput;

    @FindBy(id = "creditCardMonth")
    private WebElement creditCardMonthInput;

    @FindBy(id = "creditCardYear")
    private WebElement creditCardYearInput;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCardInput;

    @FindBy(css = "input[type='submit']")
    private WebElement purchaseButton;

    @FindBy(id = "rememberMe")
    private WebElement rememberMe;

    public PurchasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillOutForm(String name, String address, String city, String state, String zipCode, String creditCard, String creditCardMonth, String creditCardYear, String nameOnCard) {
        nameInput.sendKeys(name);
        addressInput.sendKeys(address);
        cityInput.sendKeys(city);
        stateInput.sendKeys(state);
        zipCodeInput.sendKeys(zipCode);
        creditCardInput.sendKeys(creditCard);
        creditCardMonthInput.sendKeys(creditCardMonth);
        creditCardYearInput.sendKeys(creditCardYear);
        nameOnCardInput.sendKeys(nameOnCard);
    }

    public void purchaseFlight() {
        purchaseButton.click();
    }

    public void clickRememberMe() {
        rememberMe.click();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getSubTitle() {
        return subtitle.getText();
    }

    public List<String> getInfoValues() {
        List<WebElement> infoValues = driver.findElements(By.cssSelector("p"));
        List<String> infoValuesText = new ArrayList<String>();
        for (WebElement infoValue : infoValues) {
            infoValuesText.add(infoValue.getText());
        }
        return infoValuesText;
    }
}
