# Air Quality
Esta aplicação oferece detalhes uma pesquisa  _Air Quality_ e _Weather_ e detalhes de cache local.

<!-- COLOCAR DEMO OR GIf E link the video -->
![demo]()

# Arquitetura 
<!-- COLOCAR E FAZER DIAGRAMA -->
![diagrama]() 

## Techonologies stack 
Component | Tecnology
--- | ---
frontend | Next.js
backend | Spring Boot
API | [Air Visual](https://api-docs.iqair.com/?version=latest) <br> [OpenWeatherMap - Geocoding](https://openweathermap.org/api/geocoding-api) <br>[OpenWeatherMap - Current weather ](https://openweathermap.org/current)


# How to run

```bash 
# in the root folder (HW1)

cd backend/airQuality
./mvnw spring-boot:run

# in another terminal in the root folder (HW1)

cd frontend
npm start 
```
## How to run tests
```bash
# in the root folder (HW1)

cd backend/airQuality
./mvnw clean test

# with jacoco report
./mvnw test jacoco:report #Unit Test

./mvnw test jacoco:report-integration #Integration Test
```

## Detalhes
É possível encontrar mais informação sobre o projeto no [Relatório do Projeto]() e [Apresentação do projeto]().

## Autor
Mariana Andrade , 103823

<br>
Testes e Qualidade de Software<br>
Universidade de Aveiro, 2023
