package pt.ua.deti.tqs.airQuality.boundary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.airQuality.model.Geocoding.CoordEntry;
import pt.ua.deti.tqs.airQuality.model.Statistics;
import pt.ua.deti.tqs.airQuality.model.airVisual.WeatherEntry;
import pt.ua.deti.tqs.airQuality.model.openWeather.AirQualityEntry;
import pt.ua.deti.tqs.airQuality.services.AirVisualService;
import pt.ua.deti.tqs.airQuality.services.GeocodingService;
import pt.ua.deti.tqs.airQuality.services.OpenWeatherService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiRestController.class)
// @Disabled // REMOVE THIS LINE TO RUN THE TESTS But if you do, the tests will fail, because the number of requests to the external APIs is limited
class ApiRestController_withMockServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirVisualService airVisualService;

    @MockBean
    private GeocodingService geocodingService;

    @MockBean
    private OpenWeatherService openWeatherService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("When the service returns a list of countries, then returns 200 and the list")
    void whenGetCountries_thenReturns200AndReturnListCountries() throws Exception {
        List<String> countries = Arrays.asList("Portugal", "Spain");
        when(airVisualService.countries()).thenReturn(countries);

        mockMvc.perform(get("/api/v1/countries")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("Portugal")))
                .andExpect(jsonPath("$[1]", is("Spain")));

        verify(airVisualService, times(1)).countries();
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the service fails, then returns 404")
    void whenGetCountries_thenReturns404() throws Exception {
        when(airVisualService.countries()).thenReturn(null);

        mockMvc.perform(get("/api/v1/countries")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(airVisualService, times(1)).countries();
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the service returns a list of states, then returns 200 and the list")
    void whenGetStates_thenReturns200AndReturnListStates() throws Exception {
        List<String> states = Arrays.asList("Lisboa", "Porto");
        when(airVisualService.states("Portugal")).thenReturn(states);

        mockMvc.perform(get("/api/v1/Portugal/states")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("Lisboa")))
                .andExpect(jsonPath("$[1]", is("Porto")));

        verify(airVisualService, times(1)).states("Portugal");
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }
    @Test
    @DisplayName("When the state service fails, then returns 404")
    void whenGetStates_thenReturns404() throws Exception {
        when(airVisualService.states("Portugal")).thenReturn(null);

        mockMvc.perform(get("/api/v1/Portugal/states")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(airVisualService, times(1)).states("Portugal");
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the service returns a list of cities, then returns 200 and the list")
    void testGetCities() throws Exception {
        List<String> cities = Arrays.asList("Lisboa", "Porto");
        when(airVisualService.cities("Portugal", "Lisboa")).thenReturn(cities);

        mockMvc.perform(get("/api/v1/Portugal/Lisboa/cities")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("Lisboa")))
                .andExpect(jsonPath("$[1]", is("Porto")));

        verify(airVisualService, times(1)).cities("Portugal", "Lisboa");
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the city service fails, then returns 404")
    void whenGetCities_thenReturns404() throws Exception {
        when(airVisualService.cities("Portugal", "Lisboa")).thenReturn(null);

        mockMvc.perform(get("/api/v1/Portugal/Lisboa/cities")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(airVisualService, times(1)).cities("Portugal", "Lisboa");
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }
    @Test
    @DisplayName("When the service returns a weather, then returns 200 and the weather")
    void whenGetWeather_thenReturns200AndReturnWeather() throws Exception {
        WeatherEntry weather = new WeatherEntry("Agueda", "Aveiro", "Portugal", 7.0, 6.0, "icon", 3.0, 21.1, 12.3, 10.0, 11.9, "Today");
        when(airVisualService.getWeather("Agueda", "Aveiro", "Portugal")).thenReturn(weather);

        mockMvc.perform(get("/api/v1/Portugal/Aveiro/Agueda/weather")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", is(weather.getCountry())))
                .andExpect(jsonPath("$.state", is(weather.getState())))
                .andExpect(jsonPath("$.city", is(weather.getCity())))
                .andExpect(jsonPath("$.longitude", is(weather.getLongitude())))
                .andExpect(jsonPath("$.latitude", is(weather.getLatitude())))
                .andExpect(jsonPath("$.icon", is(weather.getIcon())))
                .andExpect(jsonPath("$.temperature", is(weather.getTemperature())))
                .andExpect(jsonPath("$.humidity", is(weather.getHumidity())))
                .andExpect(jsonPath("$.windSpeed", is(weather.getWindSpeed())))
                .andExpect(jsonPath("$.windDirection", is(weather.getWindDirection())))
                .andExpect(jsonPath("$.pressure", is(weather.getPressure())))
                .andExpect(jsonPath("$.date", is(weather.getDate())));

        verify(airVisualService, times(1)).getWeather("Agueda", "Aveiro", "Portugal");
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the weather service fails, then returns 404")
    void whenGetWeather_thenReturns404() throws Exception {
        when(airVisualService.getWeather("Agueda", "Aveiro", "Portugal")).thenReturn(null);

        mockMvc.perform(get("/api/v1/Portugal/Aveiro/Agueda/weather")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(airVisualService, times(1)).getWeather("Agueda", "Aveiro", "Portugal");
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the service returns a geocoding, then returns 200 and the geocoding")
    void whenGetGeocoding_thenReturns200AndReturnGeocoding() throws Exception {
        CoordEntry geocoding = new CoordEntry(40.85,-8.625);

        when(geocodingService.getCoordinates("Ovar", "Portugal")).thenReturn(geocoding);

        mockMvc.perform(get("/api/v1/Portugal/Ovar/geocoding")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lat", is(geocoding.getLat())))
                .andExpect(jsonPath("$.lon", is(geocoding.getLon())));

        verify(geocodingService, times(1)).getCoordinates("Ovar", "Portugal");
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(airVisualService);
    }
    @Test
    @DisplayName("When the geocoding service fails, then returns 404")
    void whenGetGeocoding_thenReturns404() throws Exception {
        when(geocodingService.getCoordinates("Agueda", "Portugal")).thenReturn(null);

        mockMvc.perform(get("/api/v1/Portugal/Agueda/geocoding")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(geocodingService, times(1)).getCoordinates("Agueda", "Portugal");
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(airVisualService);
    }

    @Test
    @DisplayName("When the service returns a weather, then returns 200 and the weather")
    void whenGetWeatherByCoordinates_thenReturns200AndReturnWeather() throws Exception {
        WeatherEntry weather = new WeatherEntry("Portugal", "Lisboa", "Porto", 7.0, 6.0, "icon", 3.0, 21.1, 12.3, 10.0, 11.9, "Today");

        when(openWeatherService.getWeather(7.0, 6.0)).thenReturn(weather);

        mockMvc.perform(get("/api/v1/7.0/6.0/weather")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", is(weather.getCountry())))
                .andExpect(jsonPath("$.state", is(weather.getState())))
                .andExpect(jsonPath("$.city", is(weather.getCity())))
                .andExpect(jsonPath("$.longitude", is(weather.getLongitude())))
                .andExpect(jsonPath("$.latitude", is(weather.getLatitude())))
                .andExpect(jsonPath("$.icon", is(weather.getIcon())))
                .andExpect(jsonPath("$.temperature", is(weather.getTemperature())))
                .andExpect(jsonPath("$.humidity", is(weather.getHumidity())))
                .andExpect(jsonPath("$.windSpeed", is(weather.getWindSpeed())))
                .andExpect(jsonPath("$.windDirection", is(weather.getWindDirection())))
                .andExpect(jsonPath("$.pressure", is(weather.getPressure())))
                .andExpect(jsonPath("$.date", is(weather.getDate())));


        verify(openWeatherService, times(1)).getWeather(7.0, 6.0);
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the weather service fails, then returns 404")
    void whenGetWeatherByCoordinates_thenReturns404() throws Exception {
        when(openWeatherService.getWeather(7.0, 6.0)).thenReturn(null);

        mockMvc.perform(get("/api/v1/7.0/6.0/weather")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(openWeatherService, times(1)).getWeather(7.0, 6.0);
        verifyNoMoreInteractions(airVisualService);
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

        mockMvc.perform(get("/api/v1/6.0/7.0/air-quality")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longitude", is(airQuality.getLongitude())))
                .andExpect(jsonPath("$.latitude", is(airQuality.getLatitude())))
                .andExpect(jsonPath("$.aqi", is(airQuality.getAqi())))
                .andExpect(jsonPath("$.co", is(airQuality.getCo())))
                .andExpect(jsonPath("$.no", is(airQuality.getNo())))
                .andExpect(jsonPath("$.no2", is(airQuality.getNo2())))
                .andExpect(jsonPath("$.o3", is(airQuality.getO3())))
                .andExpect(jsonPath("$.so2", is(airQuality.getSo2())))
                .andExpect(jsonPath("$.pm2_5", is(airQuality.getPm2_5())))
                .andExpect(jsonPath("$.pm10", is(airQuality.getPm10())))
                .andExpect(jsonPath("$.nh3", is(airQuality.getNh3())))
                .andExpect(jsonPath("$.date", is(airQuality.getDate())));

        verify(openWeatherService, times(1)).getAirQuality(6.0, 7.0);
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }

    @Test
    @DisplayName("When the air quality service fails, then returns 404")
    void whenGetAirQualityByCoordinates_thenReturns404() throws Exception {
        when(openWeatherService.getAirQuality(6.0, 7.0)).thenReturn(null);

        mockMvc.perform(get("/api/v1/6.0/7.0/air-quality")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(openWeatherService, times(1)).getAirQuality(6.0, 7.0);
        verifyNoMoreInteractions(openWeatherService);
        verifyNoInteractions(geocodingService);
    }
    @Test
    @DisplayName("When the service returns statistics, then returns 200 and the statistics")
    void whenGetStatistics_thenReturns200AndReturnStatistics() throws Exception {
        Statistics.getInstance().reset();

        mockMvc.perform(get("/api/v1/statistics")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cacheHits", is(0)))
                .andExpect(jsonPath("$.cacheMisses", is(0)))
                .andExpect(jsonPath("$.successfulRequests", is(1)))
                .andExpect(jsonPath("$.failedRequests", is(0)))
                .andExpect(jsonPath("$.successfulGeocodingRequests", is(0)))
                .andExpect(jsonPath("$.failedGeocodingRequests", is(0)))
                .andExpect(jsonPath("$.successfulAirVisualRequests", is(0)))
                .andExpect(jsonPath("$.failedAirVisualRequests", is(0)))
                .andExpect(jsonPath("$.successfulOpenWeatherRequests", is(0)))
                .andExpect(jsonPath("$.failedOpenWeatherRequests", is(0)))
                .andExpect(jsonPath("$.totalRequests", is(1)))
                .andExpect(jsonPath("$.successfulRequestsBySource", is(aMapWithSize(1))))
                .andExpect(jsonPath("$.failedRequestsBySource", is(aMapWithSize(0))));

        verifyNoInteractions(openWeatherService);
        verifyNoInteractions(airVisualService);
        verifyNoInteractions(geocodingService);
    }

}