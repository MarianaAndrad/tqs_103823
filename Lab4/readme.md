# Lab04 - Acceptance testing with web automation (Selenium)

# WebDriver starter
O Selenium WebDriver oferece uma interface de programação concisa (ou seja, API) para conduzir um navegador (web), como se um utilizador real estivesse operando o navegador.

> Observar a secção ["getting started"](https://www.selenium.dev/documentation/webdriver/getting_started/install_drivers/), usar o método 2 ou 3. 

## Configuração de Dependências e Criação de Projeto Spring Boot

Criar um projeto Srping Boot com as seguintes dependências:
- selenium-java
- selenium-jupiter
- junit-jupiter


```xml
<properties>
        <junit-jupiter.version>5.9.2</junit-jupiter.version>
        <maven-surefire-plugin.version>3.0.0-M9</maven-surefire-plugin.version>
        <selenium-java.version>4.8.1</selenium-java.version>
        <selenium-jupiter.version>4.3.3</selenium-jupiter.version>

        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>

        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit-jupiter.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>${selenium-java.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>selenium-jupiter</artifactId>
            <version>${selenium-jupiter.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>LATEST</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${maven-surefire-plugin.version}</version>
            </plugin>
        </plugins>
    </build>
```
> [dependencias for POM.xml](https://github.com/bonigarcia/mastering-junit5/tree/master/junit5-selenium)


## Implement the example 

>["hello world" section](https://learning.oreilly.com/library/view/hands-on-selenium-webdriver/9781098109998/ch02.html#idm45849753934704)


```java
public class HelloWorldChromeJupiterTest {


    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver();
    }

    @Test
    void test() {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        // Verify
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }
}
```

# Selenium IDE recorder
> Geralmente,usa-se o *Selenium IDE* para preparar/gravar os testes interativamente e explorar os *locators*- por exemplo, id para um determinado elemento da web.


## Instalar plug-in/ add-on no navegador
- [Firefox](https://addons.mozilla.org/en-GB/firefox/addon/selenium-ide/)
- [Chrome](https://chrome.google.com/webstore/detail/selenium-ide/mooikfkahbdckldjjndioackbalphokd)

## Gravar um teste interativamente
> Usando o aplicativo da web de uma agência de viagens fictícias, [https://blazedemo.com/](https://blazedemo.com/), grave um teste no qual seleciona e compra uma viagem. Adicione verificações relevantes (*asserts*) ao test. 

### Basic steps

1. Abrir o *Selenium Ide* (ícone da barra de menu do navegador)

2. Ao iniciar o IDE, selecionar a opção : 
   - `Record a new test in a new project`, no caso de ver a primeira vez a utilizar o IDE
   - `Open an existing project` ou `Create a new project`, dependo da sua necessidade.
    </br>

    ![](https://cdn.discordapp.com/attachments/887155995887960085/1085596810451632318/image.png)

3. Nomeie o projeto e configure a *URL base*¹, que é a página inicial do aplicativo de viagens.
4. Após as configurações, uma nova janela do navegador será aberta, carregará a URL base e iniciará a gravação do teste.
5. Interaja com a página e cada uma das ações serão registadas no IDE.
6. Para parar a gravação, mude para  a janela do IDE e clique no ícone de parar (vermelho) no canto superior direito da janela do navegador.

    ![](https://cdn.discordapp.com/attachments/887155995887960085/1085597639963316284/image.png)

7. Adicione os asserts necessários ao seu teste, verificando se as informações são corretas, como por exemplo, se o título da página contém a palavra "BlazeDemo", se a cidade de partida selecionada é "Boston" e se a cidade de destino selecionada é "New York".
   [ver mais]()

8. Execute o teste clicando no ícone de reprodução no canto superior esquerdo da janela do navegador.

    ![](https://cdn.discordapp.com/attachments/887155995887960085/1085597424896192532/image.png)

9.  Analise os resultados do teste, e se houver falhas, faça as correções necessárias e execute novamente.

> ¹URL base é o URL do aplicativo que será testado.Pode ser defenido uma vez e usado em todos os teste do projeto. 

## Exportar e Guardar o projeto
### Guardar em *.side*
1. Clique no botão "Save" (botão de disquete) na barra de ferramentas do Selenium IDE.
2. Escolha um nome de arquivo para o seu projeto de teste e salve-o como um arquivo "*.side".

### Exportar o projeto para uma classe de teste em Java
1. Clique com o botão direito do mouse em um teste, seleciona "Export"
2. Seleciona `Java JUnit` no meno que lhe apareceu.
3. Escolha um nome de arquivo para a sua classe de teste e salve-a como um arquivo "*.java".

# Page Object pattern
> *O modelo Page Object é um padrão de design de objetos no Selenium, em que as páginas da web são representadas como classes e os vários elementos [de interesse] na página são definidos como variáveis ​​na classe. Todas as possíveis interações do usuário [em uma página] podem ser implementadas como métodos na classe.*
> Implementar o padrão de design *Page Object* para um teste mais limpo e legível usando o mesmo problema de aplicação do [*Selenium IDE*](#selenium-ide-recorder)


1. Crie uma classe para cada página do aplicativo que deseja testar.

```java
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

    // ... continue

        public String getTitle() {
        return driver.getTitle();
    }
    public void selectFromPort(String fromPort) {
    //        WebElement dropdown = driver.findElement(By.name("fromPort"));
    //        dropdown.findElement(By.xpath("//option[. = '" + fromPort + "']")).click();

            this.fromPort.sendKeys(fromPort);
    }

    // ...continue
}
```

2. Crie uma classe de teste para testar o que deseja executar.

```java
public class BlazeDemoTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver();
        HomePage homePage = new HomePage(driver);
    }

    @Test
    void test() {
         // Navegar para a página inicial
        Assertions.assertEquals("BlazeDemo", homePage.getTitle());
        Assertions.assertEquals("Welcome to the Simple Travel Agency!", homePage.getSubTitle());
        // Preencher o formulário de pesquisa
        homePage.selectFromPort("Boston");
        homePage.selectToPort("London");

        // Clicar no botão de pesquisa
        homePage.clickFindFlightsButton();

        // continue ....
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }
}
```

3. Refator a implementação anterior para usar a extensão Selenium-Jupiter e o padrão Page Object.

```Java
@ExtendWith(SeleniumJupiter.class)
public class BlazeDemoTest {
    @Test
    public void testSuccessSearchFlights(FirefoxDriver driver) {
        HomePage homePage = new HomePage(driver);
        FlightsPage flightsPage = new FlightsPage(driver);
        PurchasePage purchasePage = new PurchasePage(driver);
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);

        // Navegar para a página inicial
        Assertions.assertEquals("BlazeDemo", homePage.getTitle());
        Assertions.assertEquals("Welcome to the Simple Travel Agency!", homePage.getSubTitle());
        // Preencher o formulário de pesquisa
        homePage.selectFromPort("Boston");
        homePage.selectToPort("London");

        // Clicar no botão de pesquisa
        homePage.clickFindFlightsButton();

        // continue ....
    }
}
```

#  Browser variations
>Considerando não utilizar um navegador que não está intalado no meu sistema. Recorre a uma iamgem docker, conectando o WebDriver a um navegador remoto.


```java
@ExtendWith(SeleniumJupiter.class)
public class DockerBlazeDemoTest {

    @Test
    @DisplayName("Teste de sucesso na pesquisa de voos")
    public void testSuccessSearchFlights(@DockerBrowser(type = CHROME) WebDriver driver) {
        HomePage homePage = new HomePage(driver);
        FlightsPage flightsPage = new FlightsPage(driver);
        PurchasePage purchasePage = new PurchasePage(driver);
        // ...
    }
}
```

>*Nota:* Deve ter o docker instalado.  

# Referências
- [Getting Started in Selenium IDE](https://www.selenium.dev/selenium-ide/docs/en/introduction/getting-started)
- [Code Export Selenium IDE](https://www.selenium.dev/selenium-ide/docs/en/introduction/code-export)
- [Comandos Selenium IDE](https://www.selenium.dev/selenium-ide/docs/en/api/commands)
- [Chapter 7. The Page Object Model (POM)](https://learning.oreilly.com/library/view/hands-on-selenium-webdriver/9781098109998/ch07.html#idm45849710155104)
- [Automation in Selenium: Page Object Model and Page Factory](https://www.toptal.com/selenium/test-automation-in-selenium-using-page-object-model-and-page-factory)
- [Docker Browers](https://bonigarcia.dev/selenium-jupiter/#docker-browsers)
- [Selenium-Jupiter](https://bonigarcia.dev/selenium-jupiter/)