# Lab02 - Mocking dependencias (for unit testing)

# Indice
* [Conteúdo extra](#Conteudo-extra)
* [Stocks Portfolio](#stocks-portfolio)
* [Geocoding](#Geocoding)
* [Integration](#Integration)


# Conteúdo extra

## Stub
Stub é um objeto que simula o comportamento de uma dependência externa, como um serviço de rede, e retorna valores pré-definidos, permitindo que o código em teste seja executado sem depender da dependência externa real. Os stubs são geralmente usados em testes unitários para isolar o código em teste de suas dependências externas e testá-lo em um ambiente controlado.

## MOck
Mock é um objeto que simula o comportamento de uma dependência externa, mas também permite que o teste verifique como o código em teste interage com essa dependência externa.Ao contrário dos stubs, os mocks permitem que os testes verifiquem se as interações com a dependência externa ocorreram como esperado. Os mocks são geralmente usados em testes de integração ou testes de unidade mais complexos, onde a interação com as dependências externas precisa ser verificada.


## SuT
SuT significa "Subject under Test" e se refere ao objeto ou componente que está sendo testado em um determinado cenário de teste. Em outras palavras, é o objeto que é testado em um caso de teste específico. O SuT é geralmente a classe ou método que está sendo testado e é o ponto focal do teste. Ao escrever um caso de teste, é importante entender qual é o SuT para garantir que o teste esteja se concentrando no objeto correto e que o comportamento do objeto seja testado de forma adequada.

## Guideline
1. Prepare a mock to substitute the remote service (@Mock annotation)
2. Create an instance of the subject under test (SuT) and use the mock to set 
the (remote) service instance.
3. Load the mock with the proper expectations (when...thenReturn)
4. Execute the test (use the service in the SuT)
5. Verify the result (assert) and the use of the mock (verify)



## Mockito Annotations
Annotation | Purpose
--- | ---
@Mock | create mocked instances (without having to call Mockito.mock() “manually”) <br> **when()** to specify how a mock should behave
@Spy | Creates a spy object
@Captor | Get the arguments used in a previous expectation.
@MockBean | Creates a mock bean
@SpyBean | Creates a spy bean
@ExtendWith(MockitoExtension.class) | Enables Mockito support
@InjectMocks| Use the created mock and inject it as a field in test subject


[Best practices](https://github.com/mockito/mockito/wiki/How-to-write-good-tests)



# Stocks portfolio
O StocksPortfolio contém uma coleção de Ações, um valor atual da carteira dependente do estado atual do mercado de ações. A imagem seguinte corresponde a classes implementadas para o exercicio.

![](Lab2_1/StockPorfolioClasses.png)

## POM.XML
Para implementar os testes unitários, foi necessário adicionar as seguintes dependências ao ficheiro pom.xml:

* JUnit 5
* Mockito
* Hamcrest

```xml
    <properties>
        <slf4j.version>2.0.6</slf4j.version>
        <logback.version>1.4.5</logback.version>

        <junit-jupiter.version>5.9.2</junit-jupiter.version>
        <mockito-junit-jupiter.version>5.1.1</mockito-junit-jupiter.version>

        <maven-surefire-plugin.version>3.0.0-M9</maven-surefire-plugin.version>

        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>${logback.version}</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit-jupiter.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-junit-jupiter</artifactId>
            <version>${mockito-junit-jupiter.version}</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest-all</artifactId>
            <version>1.3</version>
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

## Test *getTotalValue()*

Este método testa a funcionalidade do método **getTotalValue()** da classe **StocksPortfolio**.

Neste teste, usamos Mockito para criar um Mock da interface IStockMarket, que é usada pelo StocksPortfolio para obter o valor atual de cada ação. Configuramos o mock para retornar um valor de ação fixo para cada ação.

Em seguida, chamamos o método **getTotalValue()** e verificamos se o valor total retornado corresponde ao valor total esperado.

# Geocoding
Um aplicativo que precisa executar geocodificação reserva para localizar um código postal para um determinado ponto de coordenadas de GPS. Este serviço utiliza APIs públicas, exemplo [MapQuest API](https://developer.mapquest.com/documentation/geocoding-api/reverse/get/) para obter o código postal. O serviço é executado em um servidor dedicado e é acessível através de uma API REST.

## Diagrama UML das classes for the geocoding

![](Lab2_2/Geocoding_class.png)

## **whenResolveDetiGps_returnJacintoMagalhaeAddress**

Este método testa a funcionalidade do método **findAddressForLocation()** quando recebe coordenadas válidas. 

Neste teste, usamos Mockito para criar um Mock da interface ISimpleHttpClient, que é usada pelo AddressResolver para fazer a chamada HTTP para o serviço de geocodificação. Configuramos o mock para retornar uma resposta Json que corresponde a uma solicitação de geocodificação bem-sucedida, com um endereço válido retornado.

Em seguida, chamamos o método **findAddressForLocation()** com coordenadas válidas e verificamos se a resposta retornada corresponde ao endereço esperado.

### **Questão alinea b**
O SuT(subject under test) é a classe *AddressResolver* e o serviço a ser mockado é *ISimpleHttpClient*, responsável por fazer a chamada HTTP para o serviço de geocodificação remota.

## **whenBadCoordidates_thenReturnNoValidAddress**

Este método testa a funcionalidade do método **findAddressForLocation()** quando recebe coordenadas inválidas.

Neste teste, configuramos o mock para retornar uma resposta JSON que indica que uma solicitação inválida foi feita com coordenadas incorretas. Em seguida, chamamos o método **findAddressForLocation()** com coordenadas inválidas e verificamos se a resposta retornada corresponde a um endereço inválido esperado.

## **whenCoordinatesNull_thenReturnExpection**

Este método extra testa a funcionalidade do método **findAddressForLocation()** quando recebe coordenadas nulas.

Neste caso, em vez de usar o Mockito para simular Neste teste, não é necessário configurar um mock porque o método findAddressForLocation() verifica se as coordenadas são nulas antes de fazer a chamada HTTP. Em vez disso, simplesmente chamamos o método findAddressForLocation() com coordenadas nulas e verificamos se uma exceção é lançada.


# Integration
Para implementar um teste de integração, usei a class **AddressResolverIT** dada.

Nesta nova classe de teste, usei uma implementação  real do HttpClient para se comunicar com o serviço de geocodificação remota.

Reutilizei os testes do exercício anterior, para esta nova classe de teste, mas removei qualquer suporte para mocking, para que podese usar a implementação real do HttpClient.

Na forma pedida e para garantir que a execução do teste não falhe devido a problemas de conectividade, usei o plugin maven **failsafe plugin**, tal como pedido.

Por fim, executei os testes pelo terminal com os seguintes comandos: `mvn test` e o `mvn install failsafe:integration-test`.

## **Diferenças entre `mvn test` e o `mvn install failsafe:integration-test`**

O comando `mvn test` executa os testes unitários, enquanto o comando `mvn install failsafe:integration-test` executa os testes de integração do projeto usando o plugin Failsafe do Maven.

O plugin Failsafe é usado para executar testes de integração. O plugin Failsafe é executado após o plugin Surefire, que é usado para executar testes unitários.

O plugin Failsafe é executado quando o comando `mvn install failsafe:integration-test` é executado. O plugin Failsafe executa os testes de integração em um novo ciclo de vida do Maven, chamado de ciclo de vida de integração.


## **Testes de unidade e de integração**

### **Testes de unidade**

Os testes de unidade testam as classes e métodos individualmente, isolando as dependências externas usando mocks ou stubs, e garantem que cada unidade do código funcione corretamente em relação à sua especificação.

### **Testes de integração**

Os testes de integração, por outro lado, testam a interação entre diferentes unidades do sistema, bem como a integração com componentes externos, como bancos de dados, sistemas de arquivos, serviços da web, etc. Eles garantem que o sistema funcione como um todo e que as diferentes partes se comuniquem corretamente.

### **Diferenças entre testes de unidade e de integração**

Portanto, enquanto os testes de unidade podem detectar problemas em uma única unidade do código, os testes de integração são importantes para garantir que o sistema como um todo atenda aos requisitos e funcione corretamente em um ambiente mais realista.

 
# Referencias

* [Mockito](https://site.mockito.org/))<br>
* [Mockito When/Then](https://www.baeldung.com/mockito-behavior)<br>
* [Mockito example](https://github.com/bonigarcia/mastering-junit5/tree/master/junit5-mockito))<br>
* [Testing with Hamcrest](https://www.baeldung.com/java-junit-hamcrest-guide)<br>
* [Hamcrest example](https://github.com/bonigarcia/mastering-junit5/blob/master/junit5-assertions/src/test/java/io/github/bonigarcia/HamcrestTest.java)

