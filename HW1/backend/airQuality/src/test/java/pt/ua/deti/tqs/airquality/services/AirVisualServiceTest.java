package pt.ua.deti.tqs.airquality.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ua.deti.tqs.airquality.extapi.AirVisualApi;
import pt.ua.deti.tqs.airquality.model.geocoding.CoordEntry;
import pt.ua.deti.tqs.airquality.model.Storage;
import pt.ua.deti.tqs.airquality.model.airvisual.AirVisualKey;
import pt.ua.deti.tqs.airquality.model.airvisual.WeatherEntry;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AirVisualServiceTest {
    @MockBean
    private AirVisualApi api;

    @MockBean
    private OpenWeatherService openWeatherService;

    @MockBean
    private GeocodingService geocodingService;

    @MockBean
    private Storage<String, List<String>> locations;

    @MockBean
    private Storage<AirVisualKey, WeatherEntry> weatherStorage;

    private AirVisualService service;
    @BeforeEach
    void setup() {
        service = new AirVisualService(api, geocodingService, openWeatherService, locations, weatherStorage);
    }
    @Test
    void testCountriesWhenInCache() throws URISyntaxException, IOException {
        when(locations.get("countries")).thenReturn(List.of("Portugal", "Spain"));
        List<String> fromService = service.countries();

        assertThat(fromService, hasItems("Portugal", "Spain"));

        verify(api, times(0)).getCountries();

        verify(locations, times(1)).get("countries");
        verify(weatherStorage, times(0)).get(any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());
    }
    @Test
    void testCountriesWhenNotInCache() throws URISyntaxException, IOException {
        when(locations.get("countries")).thenReturn(null);
        when(api.getCountries()).thenReturn(List.of("Portugal", "Spain"));
        List<String> fromService = service.countries();

        assertThat(fromService, hasItems("Portugal", "Spain"));

        verify(api, times(1)).getCountries();

        verify(locations, times(1)).get("countries");
        verify(locations, times(1)).add("countries", List.of("Portugal", "Spain"));
        verify(weatherStorage, times(0)).get(any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());
    }

    @Test
    void testCountriesWhenNotInCacheOrApi() throws URISyntaxException, IOException {
        when(locations.get("countries")).thenReturn(null);
        when(api.getCountries()).thenReturn(null);
        List<String> fromService = service.countries();

        assertThat(fromService, is(nullValue()));

        verify(api, times(1)).getCountries();

        verify(locations, times(1)).get("countries");
        verify(locations, times(0)).add("countries", List.of("Portugal", "Spain"));
        verify(weatherStorage, times(0)).get(any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());
    }

    @Test
    void testStatesWhenInCache() throws URISyntaxException, IOException {
        when(locations.get("Portugal")).thenReturn(List.of("Lisboa", "Porto"));
        List<String> fromService = service.states("Portugal");

        assertThat(fromService, hasItems("Lisboa", "Porto"));

        verify(api, times(0)).getStates("Portugal");

        verify(locations, times(1)).get("Portugal");
        verify(weatherStorage, times(0)).get(any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());
    }

    @Test
    void testStatesWhenNotInCache() throws URISyntaxException, IOException {
        when(locations.get("Portugal")).thenReturn(null);
        when(api.getStates("Portugal")).thenReturn(List.of("Lisboa", "Porto"));
        List<String> fromService = service.states("Portugal");

        assertThat(fromService, hasItems("Lisboa", "Porto"));

        verify(api, times(1)).getStates("Portugal");

        verify(locations, times(1)).get("Portugal");
        verify(locations, times(1)).add("Portugal", List.of("Lisboa", "Porto"));
        verify(weatherStorage, times(0)).get(any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());
    }

    @Test
    void testStatesWhenNotInCacheOrApi() throws URISyntaxException, IOException {
        when(locations.get("Portugal")).thenReturn(null);
        when(api.getStates("Portugal")).thenReturn(null);
        List<String> fromService = service.states("Portugal");

        assertThat(fromService, is(nullValue()));

        verify(api, times(1)).getStates("Portugal");

        verify(locations, times(1)).get("Portugal");
        verify(locations, times(0)).add("Portugal", List.of("Lisboa", "Porto"));
        verify(weatherStorage, times(0)).get(any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());
    }

    @Test
    void testCitiesInCache() throws URISyntaxException, IOException {
        when(locations.get("Portugal:Lisboa")).thenReturn(List.of("Lisboa", "Porto"));
        List<String> fromService = service.cities("Portugal", "Lisboa");

        assertThat(fromService, hasItems("Lisboa", "Porto"));

        verify(api, times(0)).getCities("Portugal", "Lisboa");

        verify(locations, times(1)).get("Portugal:Lisboa");
        verify(weatherStorage, times(0)).get(any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());
    }

    @Test
    void testCitiesNotInCache() throws URISyntaxException, IOException {
        when(locations.get("Portugal:Lisboa")).thenReturn(null);
        when(api.getCities("Portugal", "Lisboa")).thenReturn(List.of("Lisboa", "Porto"));
        List<String> fromService = service.cities("Portugal", "Lisboa");

        assertThat(fromService, hasItems("Lisboa", "Porto"));

        verify(api, times(1)).getCities("Portugal", "Lisboa");

        verify(locations, times(1)).get("Portugal:Lisboa");
        verify(locations, times(1)).add("Portugal:Lisboa", List.of("Lisboa", "Porto"));
        verify(weatherStorage, times(0)).get(any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());
    }

    @Test
    void testCitiesNotInCacheOrApi()  throws URISyntaxException, IOException {
        when(locations.get("Portugal:Lisboa")).thenReturn(null);
        when(api.getCities("Portugal", "Lisboa")).thenReturn(null);
        List<String> fromService = service.cities("Portugal", "Lisboa");

        assertThat(fromService, is(nullValue()));

        verify(api, times(1)).getCities("Portugal", "Lisboa");

        verify(locations, times(1)).get("Portugal:Lisboa");
        verify(locations, times(0)).add("Portugal:Lisboa", List.of("Lisboa", "Porto"));
        verify(weatherStorage, times(0)).get(any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());
    }

    @Test
    void testWeatherInCache() throws URISyntaxException, IOException{
        when(weatherStorage.get(new AirVisualKey("Lisboa", "Lisboa", "Portugal"))).thenReturn(new WeatherEntry("Lisboa", "Lisboa", "Portugal", 30.34, -8.8, "05-04-2023", 12.0, 12012.0, 24.5, 2.49, 78.0, "01n"));
        WeatherEntry fromService = service.getWeather("Lisboa", "Lisboa", "Portugal");

        assertThat(fromService, is(notNullValue()));

        verify(api, times(0)).getWeather("Lisboa", "Lisboa", "Portugal");
        verify(weatherStorage, times(1)).get(new AirVisualKey("Lisboa", "Lisboa", "Portugal"));
        verify(weatherStorage, times(0)).add(any(), any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());

    }

    @Test
    void testWeatherNotInCacheButInMainApi() throws URISyntaxException, IOException {
        when(weatherStorage.get(new AirVisualKey("Lisboa", "Lisboa", "Portugal"))).thenReturn(null);
        when(api.getWeather("Lisboa", "Lisboa", "Portugal")).thenReturn(new WeatherEntry("Lisboa", "Lisboa", "Portugal", 30.34, -8.8, "05-04-2023", 12.0, 12012.0, 24.5, 2.49, 78.0, "01n"));
        WeatherEntry fromService = service.getWeather("Lisboa", "Lisboa", "Portugal");

        assertThat(fromService, is(notNullValue()));

        verify(api, times(1)).getWeather("Lisboa", "Lisboa", "Portugal");
        verify(weatherStorage, times(1)).get(new AirVisualKey("Lisboa", "Lisboa", "Portugal"));
        verify(weatherStorage, times(1)).add(new AirVisualKey("Lisboa", "Lisboa", "Portugal"), new WeatherEntry("Lisboa", "Lisboa", "Portugal", 30.34, -8.8, "05-04-2023", 12.0, 12012.0, 24.5, 2.49, 78.0, "01n"));
        verify(locations, times(0)).get(any());
        verify(openWeatherService, times(0)).getWeather(any(), any());
        verify(geocodingService, times(0)).getCoordinates(any(),any());
    }

    @Test
    void testWeatherNotInCacheOrMainApiButInOpenWeather() throws URISyntaxException, IOException {
        when(weatherStorage.get(new AirVisualKey("Lisboa", "Lisboa", "Portugal"))).thenReturn(null);
        when(api.getWeather("Lisboa", "Lisboa", "Portugal")).thenReturn(null);
        when(geocodingService.getCoordinates("Lisboa", "Portugal")).thenReturn(new CoordEntry(30.34, -8.8));
        when(openWeatherService.getWeather(30.34, -8.8)).thenReturn(new WeatherEntry("Lisboa", "Lisboa", "Portugal", 30.34, -8.8, "05-04-2023", 12.0, 12012.0, 24.5, 2.49, 78.0, "01n"));
        WeatherEntry fromService = service.getWeather("Lisboa", "Lisboa", "Portugal");

        assertThat(fromService, is(notNullValue()));

        verify(api, times(1)).getWeather("Lisboa", "Lisboa", "Portugal");
        verify(weatherStorage, times(1)).get(new AirVisualKey("Lisboa", "Lisboa", "Portugal"));
        verify(geocodingService, times(1)).getCoordinates("Lisboa", "Portugal");
        verify(openWeatherService, times(1)).getWeather(30.34, -8.8);
        verify(weatherStorage, times(1)).add(new AirVisualKey("Lisboa", "Lisboa", "Portugal"),new WeatherEntry("Lisboa", "Lisboa", "Portugal", 30.34, -8.8, "05-04-2023", 12.0, 12012.0, 24.5, 2.49, 78.0, "01n"));
        verify(locations, times(0)).get(any());
    }

    @Test
    void testWeatherNotInCacheOrMainApiOrSecondApi() throws URISyntaxException, IOException {
        when(weatherStorage.get(new AirVisualKey("Lisboa", "Lisboa", "Portugal"))).thenReturn(null);
        when(api.getWeather("Lisboa", "Lisboa", "Portugal")).thenReturn(null);
        when(geocodingService.getCoordinates("Lisboa", "Portugal")).thenReturn(new CoordEntry(30.34, -8.8));
        when(openWeatherService.getWeather(30.34, -8.8)).thenReturn(null);
        WeatherEntry fromService = service.getWeather("Lisboa", "Lisboa", "Portugal");

        assertThat(fromService, is(nullValue()));

        verify(api, times(1)).getWeather("Lisboa", "Lisboa", "Portugal");
        verify(weatherStorage, times(1)).get(new AirVisualKey("Lisboa", "Lisboa", "Portugal"));
        verify(geocodingService, times(1)).getCoordinates("Lisboa", "Portugal");
        verify(openWeatherService, times(1)).getWeather(30.34, -8.8);
        verify(weatherStorage, times(0)).add(new AirVisualKey("Lisboa", "Lisboa", "Portugal"),new WeatherEntry("Lisboa", "Lisboa", "Portugal", 30.34, -8.8, "05-04-2023", 12.0, 12012.0, 24.5, 2.49, 78.0, "01n"));
        verify(locations, times(0)).get(any());
    }

    @Test
    void testWeatherNotInCacheOrMainApiButCoorNull() throws URISyntaxException, IOException {
        when(weatherStorage.get(new AirVisualKey("Lisboa", "Lisboa", "Portugal"))).thenReturn(null);
        when(api.getWeather("Lisboa", "Lisboa", "Portugal")).thenReturn(null);
        when(geocodingService.getCoordinates("Lisboa", "Portugal")).thenReturn(null);
        when(openWeatherService.getWeather(30.34, -8.8)).thenReturn(new WeatherEntry("Lisboa", "Lisboa", "Portugal", 30.34, -8.8, "05-04-2023", 12.0, 12012.0, 24.5, 2.49, 78.0, "01n"));
        WeatherEntry fromService = service.getWeather("Lisboa", "Lisboa", "Portugal");

        assertThat(fromService, is(nullValue()));

        verify(api, times(1)).getWeather("Lisboa", "Lisboa", "Portugal");
        verify(weatherStorage, times(1)).get(new AirVisualKey("Lisboa", "Lisboa", "Portugal"));
        verify(geocodingService, times(1)).getCoordinates("Lisboa", "Portugal");
        verify(openWeatherService, times(0)).getWeather(30.34, -8.8);
        verify(weatherStorage, times(0)).add(new AirVisualKey("Lisboa", "Lisboa", "Portugal"),new WeatherEntry("Lisboa", "Lisboa", "Portugal", 30.34, -8.8, "05-04-2023", 12.0, 12012.0, 24.5, 2.49, 78.0, "01n"));
        verify(locations, times(0)).get(any());
    }


}