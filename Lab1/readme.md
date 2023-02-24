# Lab01 - Unit Testing (with JUnit 5)

# Conteúdos

## Unit Testing

*Unit Testing* é uma técnica de teste de software em que uma unidade individual, ou seja, o menor pedaço de código que pode ser logicamente isolado em um sistema, são testados individualmente, geralmente por meio da criação e execução de testes automatizados. Trata-se de uma prática recomendada em desenvolvimento ágil de software, pois permite garantir a qualidade do software, melhorar a manutenibilidade, reduzir os custos de correção de erros e melhorar a produtividade dos desenvolvedores.

Na maioria das linguagens de programação, uma unidade é uma função, uma sub-rotina, um método ou propriedade. Geralmente, porém, menor é melhor. **Testes menores oferecem uma visão muito mais granular do desempenho do seu código.** O seu objetivo é garantir que cada unidade do código seja testada exaustivamente antes de ser integrada a outras unidades ou componentes, a modo de se detectar e corrigir quaisquer erros ou defeitos de software o mais cedo possível.


## JUnit 

JUnit é uma framework de teste unitários em Java, fornece uma série de recursos que permite que os desenvolvedores definam, criem e executam testes de unidade de forma fácil e eficiente.

### **Anotações**

*Anotação* | *Descrição*
--- | ---
@Test | Identifica um método como um método de teste.
@DisplayName | Define o nome de exibição para um método de teste.
@BeforeAll | Executa um método antes de todos os testes.
@BeforeEach | Executa um método antes de cada teste.
@AfterAll | Executa um método depois de todos os testes.
@AfterEach | Executa um método depois de cada teste.
@Disabled | Desabilita um método de teste.
@Nested | Anota uma classe interna como uma classe de teste aninhada.
@Tag | Anota um método de teste com uma tag.
@ExtendWith | Anota uma classe com uma extensão.
@ParameterizedTest | Anota um método como um método de teste parametrizado.


### **Asserts**

Asserts são usados para verificar se o resultado de um teste é o esperado. Se o resultado for diferente do esperado, o teste falha. O JUnit 5 fornece uma série de métodos assert para verificar se o resultado de um teste é o esperado.

name | Description | Exemplo
--- | --- | ---
assertEquals | Verifica se dois objetos são iguais | assertEquals(1, 1)
assertNotEquals | Verifica se dois objetos são diferentes | assertNotEquals(1, 2)
assertTrue | Verifica se um valor booleano é verdadeiro | assertTrue(true)
assertFalse | Verifica se um valor booleano é falso | assertFalse(false)
assertNull | Verifica se um objeto é nulo | assertNull(null)
assertNotNull | Verifica se um objeto não é nulo | assertNotNull(new Object())
assertSame | Verifica se dois objetos são o mesmo | assertSame(1, 1)
assertNotSame | Verifica se dois objetos não são o mesmo | assertNotSame(1, 2)
assertArrayEquals | Verifica se dois arrays são iguais | assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3})
assertThrows | Verifica se um bloco de código lança uma exceção | assertThrows(IllegalArgumentException.class, () -> { throw new IllegalArgumentException("a message"); })
assertTimeout | Verifica se um bloco de código termina dentro de um tempo limite | assertTimeout(Duration.ofMillis(100), () -> { Thread.sleep(10); })

#### **Asserts com mensagens**

É possível adicionar uma mensagem personalizada para cada assert. Essa mensagem será exibida caso o teste falhe.

```java
@Test
void testWithMessage() {
    assertEquals(2, calculator.add(1, 1), "1 + 1 should equal 2");
}
```

### **Propriedades de um bom teste**

* **Automático**: Pode ser executado por uma ferramenta de automação
* **Completo** : Atende os objetivos de cobertura desejados (completo e cuidadoso)
* **Repetitivo**: Capaz de ser executado repetidamente e continuar a produzir os
mesmos resultados independentemente do ambiente
* **Independente**: Não depende nem interfere com outros testes


# Stack contract

A *stack contract* é um conjunto de regras que descreve o comportamento de uma pilha. A stack contract é composta por 5 metodos:

* **push**: Adiciona um elemento ao topo da pilha
* **pop**: Remove o elemento do topo da pilha
* **peek**: Retorna o elemento do topo da pilha
* **size**: Retorna o número de elementos na pilha
* **isEmpty**: Retorna se a pilha está vazia

## Estrutura do projeto 

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── stack
│   │   │       ├── BoundedStack.java
│   │   │       ├── IStack.java
│   │   │       ├── DraftStack.java
│   │   │       └── TqsStack.java
│   │   └── resources
│   └── test
│       ├── java
│       |     ├── BoundedStackTest.java
│       │     └── TqsStackTest.java
└── pom.xml
```

### POM.XML

```xml
Depois da criação de um projeto *Maven*, no ficheiro *POM.xml*, é necessário colocar a dependências da estrutura de testes JUnit 5, e os plugins responsáveis por executar os testes unitários e de integração. 

<dependencies>
    <!-- Junit -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.9.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M7</version>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-failsafe-plugin</artifactId>
            <version>3.0.0-M7</version>
        </plugin>
    </plugins>
</build>
```

##  Execução

Para executar os testes unitários, basta executar o comando:

```bash
mvn test #executa todos os testes
mvn test -Dtest=NomeDaClasseTeste #executa apenas os testes da classe NomeDaClasseTeste
mvn compile 




