# Lab3 Multi-layer apllication testing (with Spring Boot)

# Employee manager example


## Questions:

1. Identify a couple of examples that use AssertJ expressive methods chaining.

**Resposta**:


2. Identify an example in which you mock the behavior of the repository (and avoid involving a 
database).

**Resposta**:

3. What is the difference between standard @Mock and @MockBean?

**Resposta**:

1. What is the role of the file “application-integrationtest.properties”? In which conditions will it be 
used?

**Resposta**:

5. the sample project demonstrates three test strategies to assess an API (C, D and E) developed 
with SpringBoot. Which are the main/key differences?

**Resposta**:


# Cars Service



# Integration test

> Nesta etapa, o objetivo era adaptar o teste de integração para usar um banco de dados real. Para tal, foi necessário executar uma instância do banco de dados MYSQL e garantir a conexão estivesse a funcionar corretamente. Depois, foi adicionada a depência do MySQL e criado e alterado um arquivo de propriedades para usar o banco de dados MySQL.
<br>

Passos para a realização deste exercício foram:

1. Executar uma instância mysql e certificar-se de que pode se conectar usando um contêiner Docker. 
O comando a seguir foi o utilizado para executar a instância do MySQL:

```bash
docker run --name cars_service -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cars_service -e MYSQL_USER=springuser -e MYSQL_PASSWORD=password -p 3307:3306 -d mysql/mysql-server:5.7
```

Este comando inicia uma instância do MySQL com um nome de contêiner "cars_service" e expõe a porta 3307 no host. O banco de dados é criado com o nome "cars_service" e um usuário "springuser" é criado com a senha "password".



2. Alterar o POM, incluindo a dependência do MySQL.

```xml
<dependency>
        <groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
        <version>8.0.27</version>
</dependency>
```

3. Alterar o arquivo de propriedades- **application-integrationtest.properties** - para usar o banco de dados MySQL. 
No arquivo de propriedades foram colocadas as seguintes linhas de código para especificar as configurações de conexão do banco de dados MySQL:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3307/cars_service
spring.datasource.username=springuser
spring.datasource.password=password
```

4. Na classe de teste, alterar as anotações para indicar que se trata de um teste de integração e para ativar o perfil de configuração de teste de integração.

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureTestDatabase // this annotation is not needed because we are using the same database
@TestPropertySource(locations = "/application-integrationtest.properties")
public class CarRestControllerTemplateIT {
        // ...
}
```

Com essas alterações, o teste de integração agora usa um banco de dados MySQL real em vez de um banco de dados em memória.


# Referências

[Difference between @Mock and @MockBean in Spring Boot? Example Tutorial](https://javarevisited.blogspot.com/2022/08/difference-between-mock-and-mockbean-in.html?m=1)
