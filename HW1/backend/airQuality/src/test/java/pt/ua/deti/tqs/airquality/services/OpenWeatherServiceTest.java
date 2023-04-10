package pt.ua.deti.tqs.airquality.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ua.deti.tqs.airquality.extapi.OpenWeatherApi;
import pt.ua.deti.tqs.airquality.model.Storage;
import pt.ua.deti.tqs.airquality.model.airvisual.WeatherEntry;
import pt.ua.deti.tqs.airquality.model.openweather.AirQualityEntry;
import pt.ua.deti.tqs.airquality.model.openweather.OpenWeatherKey;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@SpringBootTest
class OpenWeatherServiceTest {

    @MockBean
    private OpenWeatherApi api;

    @MockBean
    private Storage<OpenWeatherKey, AirQualityEntry> airQualityStorage;

    @MockBean
    private Storage<OpenWeatherKey, WeatherEntry> weatherStorage;


    private OpenWeatherService service;

    @BeforeEach
    void setUp() {
        service = new OpenWeatherService(api, airQualityStorage, weatherStorage);
    }

    @Test
    void testGetAirQualityWhenInCache() throws IOException {
        OpenWeatherKey key = new OpenWeatherKey(42.85, -8.62);
        when(airQualityStorage.get(key)).thenReturn(new AirQualityEntry()); //ALTERAR SAIDA DE DADOS
        AirQualityEntry entry = service.getAirQuality(42.85, -8.62);

        assertThat(entry, notNullValue());

        verify(api, times(0)).getAirQualityInfo(42.85, -8.62);
        verify(airQualityStorage, times(1)).get(key);
        verify(airQualityStorage, times(0)).add(key, entry);
        verify(weatherStorage, times(0)).get(any());
        verify(weatherStorage, times(0)).add(any(), any());
    }

    @Test
    void testGetAirQualityWhenNotInCache() throws IOException{
        OpenWeatherKey key = new OpenWeatherKey(42.85, -8.62);
        when(airQualityStorage.get(key)).thenReturn(null);
        when(api.getAirQualityInfo(42.85, -8.62)).thenReturn(new AirQualityEntry()); //ALTERAR SAIDA DE DADOS
        AirQualityEntry entry = service.getAirQuality(42.85, -8.62);

        assertThat(entry, notNullValue());

        verify(api, times(1)).getAirQualityInfo(42.85, -8.62);
        verify(airQualityStorage, times(1)).get(key);
        verify(airQualityStorage, times(1)).add(key, entry);
        verify(weatherStorage, times(0)).get(any());
        verify(weatherStorage, times(0)).add(any(), any());
    }

    @Test
    void testGetAirQualityWhenNotInCacheOrApi() throws IOException{
        OpenWeatherKey key = new OpenWeatherKey(42.85, -8.62);
        when(airQualityStorage.get(key)).thenReturn(null);
        when(api.getAirQualityInfo(42.85, -8.62)).thenReturn(null);
        AirQualityEntry entry = service.getAirQuality(42.85, -8.62);

        assertThat(entry, nullValue());

        verify(api, times(1)).getAirQualityInfo(42.85, -8.62);
        verify(airQualityStorage, times(1)).get(key);
        verify(airQualityStorage, times(0)).add(key, entry);
        verify(weatherStorage, times(0)).get(any());
        verify(weatherStorage, times(0)).add(any(), any());
    }

    @Test
    void testGetWeatherInfoWhenInCache() throws IOException {
        OpenWeatherKey key = new OpenWeatherKey(42.85, -8.62);
        when(weatherStorage.get(key)).thenReturn(new WeatherEntry()); //ALTERAR SAIDA DE DADOS
        WeatherEntry entry = service.getWeather(42.85, -8.62);

        assertThat(entry, notNullValue());

        verify(api, times(0)).getWeatherInfo(42.85, -8.62);
        verify(weatherStorage, times(1)).get(key);
        verify(weatherStorage, times(0)).add(key, entry);
        verify(airQualityStorage, times(0)).get(any());
        verify(airQualityStorage, times(0)).add(any(), any());
    }

    @Test
    void testGetWeatherInfoWhenNotInCache() throws IOException{
        OpenWeatherKey key = new OpenWeatherKey(42.85, -8.62);
        when(weatherStorage.get(key)).thenReturn(null);
        when(api.getWeatherInfo(42.85, -8.62)).thenReturn(new WeatherEntry()); //ALTERAR SAIDA DE DADOS
        WeatherEntry entry = service.getWeather(42.85, -8.62);

        assertThat(entry, notNullValue());

        verify(api, times(1)).getWeatherInfo(42.85, -8.62);
        verify(weatherStorage, times(1)).get(key);
        verify(weatherStorage, times(1)).add(key, entry);
        verify(airQualityStorage, times(0)).get(any());
        verify(airQualityStorage, times(0)).add(any(), any());
    }

    @Test
    void testGetWeatherInfoWhenNotInCacheOrApi() throws IOException{
        OpenWeatherKey key = new OpenWeatherKey(42.85, -8.62);
        when(weatherStorage.get(key)).thenReturn(null);
        when(api.getWeatherInfo(42.85, -8.62)).thenReturn(null);
        WeatherEntry entry = service.getWeather(42.85, -8.62);

        assertThat(entry, nullValue());

        verify(api, times(1)).getWeatherInfo(42.85, -8.62);
        verify(weatherStorage, times(1)).get(key);
        verify(weatherStorage, times(0)).add(key, entry);
        verify(airQualityStorage, times(0)).get(any());
        verify(airQualityStorage, times(0)).add(any(), any());
    }
}