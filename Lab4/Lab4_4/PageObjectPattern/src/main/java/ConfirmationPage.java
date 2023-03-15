import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage {

    private WebDriver driver;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    By subTitle = By.cssSelector("h1");

    @FindBy(css = "table tbody tr:nth-child(2) td:nth-child(2)")
    private WebElement status;

    @FindBy(css = "table tbody tr:nth-child(3) td:nth-child(2)")
    private WebElement amount;

    @FindBy(css = "table tbody tr:nth-child(4) td:nth-child(2)")
    private WebElement cardNumber;

    @FindBy(css = "table tbody tr:nth-child(5) td:nth-child(2)")
    private WebElement expiration;

    @FindBy(css = "table tbody tr:nth-child(6) td:nth-child(2)")
    private WebElement authCode;

    @FindBy(css = "table tbody tr:nth-child(7) td:nth-child(2)")
    private WebElement date;


    public String getStatus() {
        return status.getText();
    }

    public String getAmount() {
        return amount.getText();
    }

    public String getCardNumber() {
        return cardNumber.getText();
    }

    public String getExpiration() {
        return expiration.getText();
    }

    public String getAuthCode() {
        return authCode.getText();
    }

    public String getDate() {
        return date.getText();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getSubTitle() {
        return driver.findElement(subTitle).getText();
    }

}
