# Questions:

**Author:** *Mariana Andrade*<br>
**NMec:** 103823

1. Identificar alguns exemplos que usam encadeamento de métodos expressivos AssertJ.

**Resposta**: <br>

AssertJ é uma biblioteca que fornece métodos de asserts fluentes e expressivos em testes em Java. 
É importante ainda destacar que o encadeamento de métodos expressivos do AssertJ permite que o código dos teste seja mais legível e claro, o que facilita a identificação dos possíveis problemas. 
Alguns exemplos de métodos encadeados expressicos do AssertJ são:

1. Verificação do tamanho de uma lista e se uma lista contém um determinado valor:
```java
List<Employee> allEmployees = employeeRepository.findAll();
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
```

2. Verificação se um valor é nulo ou se o valor não é nulo:
```java
// no caso de o valor ser nulo
Employee fromDb = employeeService.getEmployeeByName("wrong_name");
assertThat(fromDb).isNull();

// no caso de o valor não ser nulo
Employee fromDb = employeeRepository.findById(emp.getId()).orElse(null);
assertThat(fromDb).isNotNull();
```

3. Verificação se um valor é igual a outro:
```java
Employee fromDb = employeeService.getEmployeeById(111L);
assertThat(fromDb.getName()).isEqualTo("john");
```

4. Verificação se um valor é verdadeiro ou falso:
```java
// no caso de o valor ser verdadeiro
boolean doesEmployeeExist = employeeService.exists("john");
assertThat(doesEmployeeExist).isEqualTo(true);
// no caso de o valor ser falso
boolean doesEmployeeExist = employeeService.exists("some_name");
assertThat(doesEmployeeExist).isEqualTo(false);

// outra forma não utilizada no projeto dado
// no caso de o valor ser verdadeiro
boolean doesEmployeeExist = employeeService.exists("john");
assertThat(doesEmployeeExist).isTrue();
// no caso de o valor ser falso
boolean doesEmployeeExist = employeeService.exists("some_name");
assertThat(doesEmployeeExist).isFalse();
```

Alguns outros exemplos não utilizados no projeto dado:

1. Verificação se um valor é < ou > ou <= ou <=  que outro:
```java
// no caso de o valor ser maior
assertThat(2).isGreaterThan(1);
// no caso de o valor ser menor
assertThat(1).isLessThan(2);
// no caso de o valor ser maior ou igual
assertThat(2).isGreaterThanOrEqualTo(1);
assertThat(1).isGreaterThanOrEqualTo(1);
// no caso de o valor ser menor ou igual
assertThat(1).isLessThanOrEqualTo(2);
assertThat(1).isLessThanOrEqualTo(1);
```

2. Verificação se um valor é uma instância de uma classe:
```java
assertThat(employee).isInstanceOf(Employee.class);
```

3. Verifcação se uma lista não contém um elemento específico:
```java
String myString = "hello world";
assertThat(myString).doesNotContain("goodbye");
```

4. Verificação se uma lista está ordenada corretamente:
```java
List<Integer> myList = Arrays.asList(1, 2, 3);
assertThat(myList).isSorted().isNotEqualTo(Arrays.asList(3, 2, 1));
```

5. Verificação se uma lista está vazia:
```java
List<Integer> myList = Arrays.asList(1, 2, 3);
assertThat(myList).isNotEmpty();
```

Para mais informações sobre AssertJ, visite a [documentação](https://assertj.github.io/doc/) oficial.

<br>

2. Identify an example in which you mock the behavior of the repository (and avoid involving a 
database).

**Resposta**:<br>
A `B_EmployeeService_UnitTest` é um exemplo onde utilizamos a simulação do comportante do repositório em testes de métodos de serviços.Ao invés de utilizar o repositório real para acessar o banco de dados, utilizamos um objeto simulado (mock) para simular o comportamento do repositório. Isso nos permite evitar o uso do banco de dados e tornar os testes mais independentes e rápidos.

<br>

3. Qual é a diferença entre @Mock e @MockBean?

**Resposta**: <br>
As anotações *@Mock* e *@MockBean* são ambas usadas em testes com objetos simulados, mas servem a propósitos diferentes. 

A anotação *@Mock* faz parte da biblioteca **Mockito** e é usada para criar objetos fictícios em testes de unidade. Ele cria uma instância simulada de uma classe ou interface que pode ser usada para isolar a classe ou método que está sendo testado de suas dependências. Objetos simulados criados com @Mock não estão cientes do contexto Spring e não são gerenciados por ele.

Por outro lado, a anotação *@MockBean* faz parte do Spring Framework usada em testes de integração que permite substituir um *bean* por um *mock*.Ele cria um objeto fictício que reconhece o contexto do Spring e pode ser usado para substituir um bean no contexto do aplicativo. Isso significa que o objeto simulado pode ser usado para simular o comportamento de um bean real, e qualquer componente no contexto do Spring que dependa do bean receberá o objeto simulado.

Em suma, @Mock é usado para criar objetos simulados em testes de unidade, enquanto @MockBean é usado para criar objetos simulados que podem ser injetados no contexto Spring em testes de integração.

<br>

4. What is the role of the file “application-integrationtest.properties”? In which conditions will it be 
used?

**Resposta**: <br> 
O ficheiro *application-integrationtest.properties* é um ficheiro de propriedades usado para configurar o aplicativo para teste de integração. Ele tem propriedades de configuração específicas para o ambiente de teste de integração, por exemplo, `spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo`, o url do banco de dados de teste e outras propriedades necessárias para definir o ambiente de execução dos testes de integração.

Este ficheiro é usada quando executamos os testes de integração. Por defeito, ao executar testes, o Spring Boot carrega o arquivo "application.properties" do classpath, mas se encontrar um arquivo chamado "application-integrationtest.properties", ele o carregará, fornecendo as propriedades especificadas no arquivo para a aplicação. Isso permite que os desenvolvedores configurem o aplicativo para usar um banco de dados diferente ou outra configuração específica do ambiente ao executar testes de integração, sem afetar a configuração de produção.

<br> 

5. the sample project demonstrates three test strategies to assess an API (C, D and E) developed 
with SpringBoot. Which are the main/key differences?

**Resposta**: <br>
O projeto dado mostra 3 estratégias de testes diferentes para avaliar uma API. 

A estratégia C é a mais simples e consiste em testar o controller da API. Neste caso, usa a anotação *@WebMvcTest* para testar apenas a camda web da aplicação, sem carregar todo o contexto da aplicação.Isso permite um teste mais rápido e direcionado da camada da web, mas requer que as outras camadas do aplicativo sejam testadas separadamente.

A estratégia D é um pouco mais complexa. Usa a anotação *@SpringBootTest* para carregar todo o contexto do aplicativo e testar todo o aplicativo de ponta a ponta. Isso permite um teste mais abrangente do aplicativo como um todo, mas pode ser mais lento e complexo de configurar.

A estratégia E é a mais complexa e semelhante a estratégia D, no entanto esta usa um *TestRestTemplate* para simular solicitações HTTP para a API, permitindo testar o aplicativo de ponta a ponta, mas sem a sobrecarga de executar um servidor HTTP real.

As principais diferenças entre estas estratégias são o *scope* e a complexidade do teste. A estratégia C é focada em testar apenas a camada web, enquanto as estratégias D e E testam toda a aplicação. As estratégias D e E são mais abrangentes, mas requerem mais configuração e podem ser mais lentas do que a estratégia C. A escolha de qual estratégia usar depende das necessidades específicas de teste e das restrições do projeto.