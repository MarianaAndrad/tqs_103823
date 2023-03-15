import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BlazeDemoTest {

    private WebDriver driver;
    private HomePage homePage;
    private FlightsPage flightsPage;
    private PurchasePage purchasePage;
    private ConfirmationPage confirmationPage;


    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        homePage = new HomePage(driver);
        flightsPage = new FlightsPage(driver);
        purchasePage = new PurchasePage(driver);
        confirmationPage = new ConfirmationPage(driver);
    }


    @Test
    @DisplayName("Teste de sucesso na pesquisa de voos")
    public void testSuccessSearchFlights() {
        // Navegar para a página inicial
        Assertions.assertEquals("BlazeDemo", homePage.getTitle());
        Assertions.assertEquals("Welcome to the Simple Travel Agency!", homePage.getSubTitle());
        // Preencher o formulário de pesquisa
        homePage.selectFromPort("Boston");
        homePage.selectToPort("London");

        // Clicar no botão de pesquisa
        homePage.clickFindFlightsButton();

        // Verificar se a página de pesquisa de voos foi carregada
        Assertions.assertEquals("BlazeDemo - reserve", flightsPage.getTitle());
        Assertions.assertEquals("Flights from Boston to London:", flightsPage.getSubTitle());

        // Verificar a lista de voos não esta vazia
        Assertions.assertFalse(flightsPage.numberOfFlights() == 0);

        // Escolher o terceiro voo
        flightsPage.chooseFirstFlight(3);

        // Verificar se a página de compra foi carregada
        Assertions.assertEquals("BlazeDemo Purchase", purchasePage.getTitle());
        Assertions.assertEquals("Your flight from TLV to SFO has been reserved.", purchasePage.getSubTitle());

        Assertions.assertTrue(purchasePage.getInfoValues().contains("Airline: United"));
        Assertions.assertTrue(purchasePage.getInfoValues().contains("Flight Number: UA954"));
        Assertions.assertTrue(purchasePage.getInfoValues().contains("Price: 400"));
        Assertions.assertTrue(purchasePage.getInfoValues().contains("Arbitrary Fees and Taxes: 514.76"));
        Assertions.assertTrue(purchasePage.getInfoValues().contains("Total Cost: 914.76"));

        // Preencher o formulário de compra
        purchasePage.fillOutForm("John Doe", "123 Main St", "Boston", "MA", "02134", "Visa", "11", "2019", "John Doe");

        // Clicar no botão de remember me
        purchasePage.clickRememberMe();

        // Clicar no botão de compra de voo
        purchasePage.purchaseFlight();

        // Verificar se a página de confirmação foi carregada
        Assertions.assertEquals("BlazeDemo Confirmation", confirmationPage.getTitle());
        Assertions.assertEquals("Thank you for your purchase today!", confirmationPage.getSubTitle());
        Assertions.assertEquals("PendingCapture", confirmationPage.getStatus());
        Assertions.assertEquals("555 USD", confirmationPage.getAmount());
        Assertions.assertEquals("xxxxxxxxxxxx1111", confirmationPage.getCardNumber());
        Assertions.assertEquals("11 /2018", confirmationPage.getExpiration());
        Assertions.assertEquals("888888", confirmationPage.getAuthCode());
        // Assertions.assertEquals(formattedDate, confirmationPage.getDate()); // TODO: Fix this


    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
