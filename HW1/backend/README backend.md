# Serviço Air Quality And Weather

De forma a construir uma **multi-layer web application** recorreu-se à _framework Spring Boot_. 
A aplicação foi desenvolvida com o objetivo de fornecer uma API REST que permite a consulta de dados meteorológicos e de qualidade do ar. 

## Arquitetura
A aplicação foi desenvolvida com base na arquitetura de camadas, onde cada camada tem uma responsabilidade específica.


### Camada de negócio
A camada de negócio é responsável por processar as requisições e devolver as respostas.

### Camada de cache
A camada de cache é responsável por armazenar os dados obtidos da camada de dados, de forma a reduzir o número de chamadas à API externa.

### Camada de API externa
A camada de API externa é responsável por obter os dados necessários para processar as requisições.


## Como executar

### Pré-requisitos
Para executar a aplicação é necessário ter instalado o Java 11.

### Execução

Para executar a aplicação, basta seguir os seguintes passos:

1. _Clonar o repositório_


2. _Navegar até o diretório da aplicação_

```bash
cd HW1/backend/airQuality #Caminho da raiz do repositório
```

3. _Executar a aplicação_

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em [http://localhost:8080](http://localhost:8080) com o seu browser.

## Como executar os testes

Para executar os testes, basta executar o seguinte comando:

```bash
./mvnw clean test

# with jacoco report
./mvnw test jacoco:report # Unit Test

./mvnw test jacoco:report-integration #Integration Test
```

## Autor
Mariana Andrade , 103823

<br>
Testes e Qualidade de Software<br>
Universidade de Aveiro, 2023

