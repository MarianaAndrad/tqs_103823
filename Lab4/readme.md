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
# Page Object pattern
#  Browser variations

