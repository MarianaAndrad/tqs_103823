package pt.ua.deti.tqs.airquality.boundary;


import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.airquality.model.geocoding.CoordEntry;
import pt.ua.deti.tqs.airquality.model.Statistics;
import pt.ua.deti.tqs.airquality.model.airvisual.WeatherEntry;
import pt.ua.deti.tqs.airquality.model.openweather.AirQualityEntry;
import pt.ua.deti.tqs.airquality.services.AirVisualService;
import pt.ua.deti.tqs.airquality.services.GeocodingService;
import pt.ua.deti.tqs.airquality.services.OpenWeatherService;

import java.util.List;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@WebMvcTest(ApiRestController.class)
@ExtendWith(MockitoExtension.class)
class ApiRestController_withRestAssuredTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirVisualService airVisualService;

    @MockBean
    private GeocodingService geocodingService;

    @MockBean
    private OpenWeatherService openWeatherService;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    @DisplayName("When the service returns a list of countries, then returns 200 and the list")
    void whenGetCountries_thenReturns200AndReturnListCountries() throws Exception {
        List<String> countries = List.of("Country1", "Country2");
        when(airVisualService.countries()).thenReturn(countries);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/countries")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("get(0)", is("Country1"))
                .body("get(1)", is("Country2"));

        verify(airVisualService, times(1)).countries();
        verifyNoInteractions(geocodingService);
        verifyNoInteractions(openWeatherService);
    }

    @Test
    @DisplayName("When the service fails, then returns 404")
    void whenGetCountriesAndServiceFails_thenReturns404() throws Exception {
        when(airVisualService.countries()).thenReturn(null);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/countries")
                .then()
                .statusCode(404);

        verify(airVisualService, times(1)).countries();
        verifyNoInteractions(geocodingService);
        verifyNoInteractions(openWeatherService);
    }

    @Test
    @DisplayName("When the service returns a list of states, then returns 200 and the list")
    void whenGetStates_thenReturns200AndReturnListStates() throws Exception {
        List<String> states = List.of("State1", "State2");
        when(airVisualService.states("Country")).thenReturn(states);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Country/states")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("get(0)", is("State1"))
                .body("get(1)", is("State2"));

        verify(airVisualService, times(1)).states("Country");
        verifyNoInteractions(geocodingService);
        verifyNoInteractions(openWeatherService);
    }

    @Test
    @DisplayName("When the state service fails, then returns 404")
    void whenGetStatesAndServiceFails_thenReturns404() throws Exception {
        when(airVisualService.states("Country")).thenReturn(null);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Country/states")
                .then()
                .statusCode(404);

        verify(airVisualService, times(1)).states("Country");
        verifyNoInteractions(geocodingService);
        verifyNoInteractions(openWeatherService);
    }

    @Test
    @DisplayName("When the service returns a list of cities, then returns 200 and the list")
    void whenGetCities_thenReturns200AndReturnListCities() throws Exception {
        List<String> cities = List.of("City1", "City2");
        when(airVisualService.cities("Country", "State")).thenReturn(cities);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Country/State/cities")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("get(0)", is("City1"))
                .body("get(1)", is("City2"));

        verify(airVisualService, times(1)).cities("Country", "State");
        verifyNoInteractions(geocodingService);
        verifyNoInteractions(openWeatherService);
    }

    @Test
    @DisplayName("When the city service fails, then returns 404")
    void whenGetCitiesAndServiceFails_thenReturns404() throws Exception {
        when(airVisualService.cities("Country", "State")).thenReturn(null);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Country/State/cities")
                .then()
                .statusCode(404);

        verify(airVisualService, times(1)).cities("Country", "State");
        verifyNoInteractions(geocodingService);
        verifyNoInteractions(openWeatherService);
    }

    @Test
    @DisplayName("When the service returns a weather, then returns 200 and the weather")
    void whenGetWeather_thenReturns200AndReturnWeather() throws Exception {
        WeatherEntry weather = new WeatherEntry();
        weather.setCity("City");
        weather.setCountry("Country");
        weather.setState("State");
        weather.setLatitude(6.0);
        weather.setLongitude(7.0);
        weather.setHumidity(1.0);
        weather.setPressure(2.0);
        weather.setTemperature(3.0);
        weather.setWindSpeed(4.0);
        weather.setWindDirection(5.0);
        weather.setDate("Today");
        weather.setIcon("icon");

        when(airVisualService.getWeather("City", "State", "Country")).thenReturn(weather);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Country/State/City/weather")
                .then()
                .statusCode(200)
                .body("city", is("City"))
                .body("country", is("Country"))
                .body("state", is("State"))
                .body("latitude", is(6.0F))
                .body("longitude", is(7.0F))
                .body("humidity", is(1.0F))
                .body("pressure", is(2.0F))
                .body("temperature", is(3.0F))
                .body("windSpeed", is(4.0F))
                .body("windDirection", is(5.0F))
                .body("date", is("Today"))
                .body("icon", is("icon"));

        verify(airVisualService, times(1)).getWeather("City", "State", "Country");
        verifyNoInteractions(geocodingService);
        verifyNoInteractions(openWeatherService);
    }

    @Test
    @DisplayName("When the weather service fails, then returns 404")
    void whenGetWeatherAndServiceFails_thenReturns404() throws Exception {
        when(airVisualService.getWeather("CIty", "State", "Country")).thenReturn(null);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Country/State/City/weather")
                .then()
                .statusCode(404);

        verify(airVisualService, times(1)).getWeather("City", "State", "Country");
        verifyNoInteractions(geocodingService);
        verifyNoInteractions(openWeatherService);
    }


    @Test
    @DisplayName("When the service returns a geocoding, then returns 200 and the geocoding")
    void whenGetGeocoding_thenReturns200AndReturnGeocoding() throws Exception {
        CoordEntry geocoding = new CoordEntry();
        geocoding.setLat(6.0);
        geocoding.setLon(7.0);

        when(geocodingService.getCoordinates("City", "Country")).thenReturn(geocoding);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Country/City/geocoding")
                .then()
                .statusCode(200)
                .body("lat", is(6.0F))
                .body("lon", is(7.0F));

        verify(geocodingService, times(1)).getCoordinates("City", "Country");
        verifyNoInteractions(airVisualService);
        verifyNoInteractions(openWeatherService);
    }

    @Test
    @DisplayName("When the geocoding service fails, then returns 404")
    void whenGetGeocodingAndServiceFails_thenReturns404() throws Exception {
        when(geocodingService.getCoordinates("City", "Country")).thenReturn(null);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/Country/City/geocoding")
                .then()
                .statusCode(404);

        verify(geocodingService, times(1)).getCoordinates("City", "Country");
        verifyNoInteractions(airVisualService);
        verifyNoInteractions(openWeatherService);
    }

    @Test
    @DisplayName("When the service returns a weather, then returns 200 and the weather")
    void whenGetWeatherByCoordinates_thenReturns200AndReturnWeather() throws Exception {
        WeatherEntry weather = new WeatherEntry();
        weather.setLongitude(7.0);
        weather.setLatitude(6.0);
        weather.setIcon("icon");
        weather.setTemperature(3.0);
        weather.setPressure(21.1);
        weather.setHumidity(12.3);
        weather.setWindSpeed(10.0);
        weather.setWindDirection(11.9);
        weather.setCountry("Country");
        weather.setCity("City");
        weather.setState("State");
        weather.setDate("Today");
        
        when(openWeatherService.getWeather(6.0, 7.0)).thenReturn(weather);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/6.0/7.0/weather")
                .then()
                .statusCode(200)
                .body("city", is("City"))
                .body("country", is("Country"))
                .body("state", is("State"))
                .body("latitude", is(6.0F))
                .body("longitude", is(7.0F))
                .body("humidity", is(12.3F))
                .body("pressure", is(21.1F))
                .body("temperature", is(3.0F))
                .body("windSpeed", is(10.0F))
                .body("windDirection", is(11.9F))
                .body("date", is("Today"))
                .body("icon", is("icon"));
        
        verify(openWeatherService, times(1)).getWeather(6.0, 7.0);
        verifyNoInteractions(airVisualService);
        verifyNoInteractions(geocodingService);
    }
    
    @Test
    @DisplayName("When the weather service fails, then returns 404")
    void whenGetWeatherByCoordinatesAndServiceFails_thenReturns404() throws Exception {
        when(openWeatherService.getWeather(6.0, 7.0)).thenReturn(null);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/6.0/7.0/weather")
                .then()
                .statusCode(404);

        verify(openWeatherService, times(1)).getWeather(6.0, 7.0);
        verifyNoInteractions(airVisualService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the service returns an air quality, then returns 200 and the air quality")
    void whenGetAirQualityByCoordinates_thenReturns200AndReturnAirQuality() throws Exception {
        AirQualityEntry airQuality = new AirQualityEntry();
        airQuality.setLongitude(7.0);
        airQuality.setLatitude(6.0);
        airQuality.setAqi(1.0);
        airQuality.setCo(2.0);
        airQuality.setNo(3.0);
        airQuality.setNo2(4.0);
        airQuality.setO3(5.0);
        airQuality.setSo2(6.0);
        airQuality.setPm2_5(7.0);
        airQuality.setPm10(8.0);
        airQuality.setNh3(9.0);
        airQuality.setDate("Date");

        when(openWeatherService.getAirQuality(6.0, 7.0)).thenReturn(airQuality);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/6.0/7.0/air-quality")
                .then()
                .statusCode(200)
                .body("longitude", is(7.0F))
                .body("latitude", is(6.0F))
                .body("aqi", is(1.0F))
                .body("co", is(2.0F))
                .body("no", is(3.0F))
                .body("no2", is(4.0F))
                .body("o3", is(5.0F))
                .body("so2", is(6.0F))
                .body("pm2_5", is(7.0F))
                .body("pm10", is(8.0F))
                .body("nh3", is(9.0F))
                .body("date", is("Date"));

        verify(openWeatherService, times(1)).getAirQuality(6.0, 7.0);
        verifyNoInteractions(airVisualService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the air quality service fails, then returns 404")
    void whenGetAirQualityByCoordinatesAndServiceFails_thenReturns404() throws Exception {
        when(openWeatherService.getAirQuality(6.0, 7.0)).thenReturn(null);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/6.0/7.0/air-quality")
                .then()
                .statusCode(404);

        verify(openWeatherService, times(1)).getAirQuality(6.0, 7.0);
        verifyNoInteractions(airVisualService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the service returns statistics, then returns 200 and the statistics")
    void whenGetStatistics_thenReturns200AndReturnStatistics() throws Exception {
        Statistics.getInstance().reset();

        RestAssuredMockMvc.given()
                .when()
                .get("/api/v1/statistics")
                .then()
                .statusCode(200)
                .body("cacheHits", is(0))
                .body("cacheMisses", is(0))
                .body("successfulRequests", is(1))
                .body("failedRequests", is(0))
                .body("successfulGeocodingRequests", is(0))
                .body("failedGeocodingRequests", is(0))
                .body("successfulAirVisualRequests", is(0))
                .body("failedAirVisualRequests", is(0))
                .body("successfulOpenWeatherRequests", is(0))
                .body("failedOpenWeatherRequests", is(0))
                .body("totalRequests", is(1))
                .body("successfulRequestsBySource", is(aMapWithSize(1)))
                .body("failedRequestsBySource", is(aMapWithSize(0)));

        verifyNoInteractions(openWeatherService);
        verifyNoInteractions(airVisualService);
        verifyNoInteractions(geocodingService);
    }

}
