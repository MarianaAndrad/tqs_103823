package pt.ua.deti.tqs.airQuality.boundary;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.airQuality.AirQualityApplication;
import pt.ua.deti.tqs.airQuality.model.Statistics;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AirQualityApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureMockMvc
public class ApiRestControllerIT {
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
        Statistics.getInstance().reset();
    }

    @Test
    @DisplayName("Test if the API returns 200 when getting the list of countries")
    void whenGetCountries_thenStatus200() {
        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/countries")
                .then()
                .statusCode(200);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/statistics")
                .then()
                .statusCode(200)
                .body("cacheMisses", is(1))
                .body("cacheHits", is(0))
                .body("successfulRequests", is(2))
                .body("failedRequests", is(0))
                .body("successfulGeocodingRequests", is(0))
                .body("failedGeocodingRequests", is(0))
                .body("successfulAirVisualRequests", is(1))
                .body("failedAirVisualRequests", is(0))
                .body("successfulOpenWeatherRequests", is(0))
                .body("failedOpenWeatherRequests", is(0));
    }

    @Test
    @DisplayName("Test if the API returns 200 when getting the list of states")
    void whenGetStates_thenStatus200() {
        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Portugal/states")
                .then()
                .statusCode(200);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/statistics")
                .then()
                .statusCode(200)
                .body("cacheMisses", is(1))
                .body("cacheHits", is(0))
                .body("successfulRequests", is(2))
                .body("failedRequests", is(0))
                .body("successfulGeocodingRequests", is(0))
                .body("failedGeocodingRequests", is(0))
                .body("successfulAirVisualRequests", is(1))
                .body("failedAirVisualRequests", is(0))
                .body("successfulOpenWeatherRequests", is(0))
                .body("failedOpenWeatherRequests", is(0));
    }

    @Test
    @DisplayName("Test if the API returns 200 when getting the list of cities")
    void whenGetCities_thenStatus200() {
        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Portugal/Aveiro/cities")
                .then()
                .statusCode(200);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/statistics")
                .then()
                .statusCode(200)
                .body("cacheMisses", is(1))
                .body("cacheHits", is(0))
                .body("successfulRequests", is(2))
                .body("failedRequests", is(0))
                .body("successfulGeocodingRequests", is(0))
                .body("failedGeocodingRequests", is(0))
                .body("successfulAirVisualRequests", is(1))
                .body("failedAirVisualRequests", is(0))
                .body("successfulOpenWeatherRequests", is(0))
                .body("failedOpenWeatherRequests", is(0));
    }

    @Test
    @DisplayName("Test if the API returns 200 when getting the weather")
    void whenGetWeather_thenStatus200() {
        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Portugal/Aveiro/Agueda/weather")
                .then()
                .statusCode(200);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/statistics")
                .then()
                .statusCode(200)
                .body("cacheMisses", greaterThan(0))
                .body("cacheHits", is(0))
                .body("successfulRequests", is(2))
                .body("failedRequests", is(0))
                .body("successfulGeocodingRequests", greaterThanOrEqualTo(0))
                .body("failedGeocodingRequests", is(0))
                .body("successfulAirVisualRequests", is(1))
                .body("failedAirVisualRequests", greaterThanOrEqualTo(0))
                .body("successfulOpenWeatherRequests", greaterThanOrEqualTo(0))
                .body("failedOpenWeatherRequests", is(0));
    }

    @Test
    @DisplayName("Test if the API returns 200 when getting the geocoding")
    void whenGetGeocoding_thenStatus200() {
        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Portugal/Aveiro/geocoding")
                .then()
                .statusCode(200);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/statistics")
                .then()
                .statusCode(200)
                .body("cacheMisses", is(1))
                .body("cacheHits", is(0))
                .body("successfulRequests", is(2))
                .body("failedRequests", is(0))
                .body("successfulGeocodingRequests", is(1))
                .body("failedGeocodingRequests", is(0))
                .body("successfulAirVisualRequests", is(0))
                .body("failedAirVisualRequests", is(0))
                .body("successfulOpenWeatherRequests", is(0))
                .body("failedOpenWeatherRequests", is(0));
    }


    @Test
    @DisplayName("Test if the API returns 200 when getting the weather by coordinates")
    void whenGetWeatherByCoordinates_thenStatus200() {
        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/40/-8/weather")
                .then()
                .statusCode(200);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/statistics")
                .then()
                .statusCode(200)
                .body("cacheMisses", is(1))
                .body("cacheHits", is(0))
                .body("successfulRequests", is(2))
                .body("failedRequests", is(0))
                .body("successfulGeocodingRequests", is(0))
                .body("failedGeocodingRequests", is(0))
                .body("successfulAirVisualRequests", is(0))
                .body("failedAirVisualRequests", is(0))
                .body("successfulOpenWeatherRequests", is(1))
                .body("failedOpenWeatherRequests", is(0));
    }

    @Test
    @DisplayName("Test if the API returns 200 when getting the air quality by coordinates")
    void whenGetAirQualityByCoordinates_thenStatus200() {
        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/38.7167/-9.1333/air-quality")
                .then()
                .statusCode(200);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/statistics")
                .then()
                .statusCode(200)
                .body("cacheMisses", is(1))
                .body("cacheHits", is(0))
                .body("successfulRequests", is(2))
                .body("failedRequests", is(0))
                .body("successfulGeocodingRequests", is(0))
                .body("failedGeocodingRequests", is(0))
                .body("successfulAirVisualRequests", is(0))
                .body("failedAirVisualRequests", is(0))
                .body("successfulOpenWeatherRequests", is(1))
                .body("failedOpenWeatherRequests", is(0));
    }

    @Test
    @DisplayName("Test if the API returns 200 when getting the statistics")
    void whenGetStatistics_thenStatus200() {
        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/statistics")
                .then()
                .statusCode(200);
    }
}
