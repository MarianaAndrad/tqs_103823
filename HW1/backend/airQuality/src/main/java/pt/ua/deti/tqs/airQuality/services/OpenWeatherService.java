package pt.ua.deti.tqs.airQuality.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.airQuality.extApi.OpenWeatherAPI;
import pt.ua.deti.tqs.airQuality.model.Statistics;
import pt.ua.deti.tqs.airQuality.model.Storage;
import pt.ua.deti.tqs.airQuality.model.airVisual.WeatherEntry;
import pt.ua.deti.tqs.airQuality.model.openWeather.AirQualityEntry;
import pt.ua.deti.tqs.airQuality.model.openWeather.OpenWeatherKey;

import java.io.IOException;


@Service
public class OpenWeatherService {
    private final OpenWeatherAPI api;
    private final Storage<OpenWeatherKey, AirQualityEntry> airQualityStorage;
    private final Storage<OpenWeatherKey, WeatherEntry> weatherStorage;

    private final Logger logger = LoggerFactory.getLogger(OpenWeatherService.class);

    public OpenWeatherService(OpenWeatherAPI api, Storage<OpenWeatherKey, AirQualityEntry> airQualityStorage, Storage<OpenWeatherKey, WeatherEntry> weatherStorage) {
        this.api = api;
        this.airQualityStorage = airQualityStorage;
        this.weatherStorage = weatherStorage;
    }

    @Autowired
    public OpenWeatherService(OpenWeatherAPI api) {
        this.api = api;
        this.airQualityStorage = new Storage<>();
        this.weatherStorage = new Storage<>();
    }

    public AirQualityEntry getAirQuality(Double lat, Double lon) throws IOException {
        logger.info("[SERVICE OpenWeatherService - AIR QUALITY]");
        OpenWeatherKey key = new OpenWeatherKey(lat, lon);
        AirQualityEntry entry = airQualityStorage.get(key);
        if (entry == null) {
            Statistics.getInstance().missCache();
            logger.info("\tFetching air quality from OpenWeather API");
            entry = api.getAirQualityInfo(lat, lon);
            if (entry == null) {
                logger.error("\tFailed to fetch air quality from OpenWeather API");
                Statistics.getInstance().failedOpenWeatherRequest();
                return null;
            }

            airQualityStorage.add(key, entry);
            Statistics.getInstance().successfulOpenWeatherRequest();
            logger.info("\t[CACHE SAVE] Air Quality");
            return entry;
        }
        Statistics.getInstance().hitCache();
        logger.info("\t[CACHE] Getting Air Quality");
        return entry;
    }

    public WeatherEntry getWeather(Double lat, Double lon) throws IOException {
        logger.info("[SERVICE OpenWeatherService - WEATHER]");
        OpenWeatherKey key = new OpenWeatherKey(lat, lon);
        WeatherEntry entry = weatherStorage.get(key);
        if (entry == null) {
            Statistics.getInstance().missCache();
            logger.info("\tFetching weather from OpenWeather API");
            entry = api.getWeatherInfo(lat, lon);
            if (entry == null) {
                logger.error("\tFailed to fetch weather from OpenWeather API");
                Statistics.getInstance().failedOpenWeatherRequest();
                return null;
            }
            weatherStorage.add(key, entry);
            Statistics.getInstance().successfulOpenWeatherRequest();
            logger.info("\t[CACHE SAVE] Weather");
            return entry;
        }
        Statistics.getInstance().hitCache();
        logger.info("\t[CACHE] Getting Weather");
        return entry;
    }
}
