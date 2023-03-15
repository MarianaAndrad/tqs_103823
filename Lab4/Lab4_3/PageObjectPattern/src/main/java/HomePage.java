import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;

        //Inicializa os elementos da página
        PageFactory.initElements(driver, this);
        driver.get("https://blazedemo.com/");
    }
    By subTitle = By.cssSelector("h1");

    @FindBy(name = "fromPort")
    @CacheLookup
    private WebElement fromPort;

    @FindBy(name = "toPort")
    @CacheLookup
    private WebElement toPort;

    @FindBy(css = "input[type='submit']")
    private WebElement findFlightsButton;

    public String getSubTitle() {
        return driver.findElement(subTitle).getText();
    }
    public String getTitle() {
        return driver.getTitle();
    }
    public void selectFromPort(String fromPort) {
//        WebElement dropdown = driver.findElement(By.name("fromPort"));
//        dropdown.findElement(By.xpath("//option[. = '" + fromPort + "']")).click();

        this.fromPort.sendKeys(fromPort);
    }

    public void selectToPort(String toPort) {
//        WebElement dropdown = driver.findElement(By.name("toPort"));
//        dropdown.findElement(By.xpath("//option[. = '" + toPort + "']")).click();

        this.toPort.sendKeys(toPort);
    }

    public void clickFindFlightsButton() {
//        driver.findElement(By.cssSelector(".btn-primary")).click();
        findFlightsButton.click();
    }

}
