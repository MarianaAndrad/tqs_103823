import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BuyTrip {

    private WebDriver driver;

    public BuyTrip(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(name = "fromPort")
    private WebElement fromPort;

    @FindBy(name = "toPort")
    private WebElement toPort;

    @FindBy(css = ".btn-primary")
    private WebElement findButton;

    @FindBy(css = "tr:nth-child(3) .btn")
    private WebElement flightButton;

    @FindBy(css = ".btn-primary")
    private WebElement conclusionButton;



    /*
    By fromPort = By.name("fromPort");
    By toPort = By.name("toPort");
    By findButton = By.cssSelector(".btn-primary");
    By flightButton = By.cssSelector("tr:nth-child(3) .btn");
    By conclusionButton = By.cssSelector(".btn-primary");
     */

}
