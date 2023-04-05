package pt.ua.deti.tqs.airQuality.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.airQuality.extApi.AirVisualAPI;
import pt.ua.deti.tqs.airQuality.model.Geocoding.CoordEntry;
import pt.ua.deti.tqs.airQuality.model.Statistics;
import pt.ua.deti.tqs.airQuality.model.Storage;
import pt.ua.deti.tqs.airQuality.model.airVisual.AirVisualKey;
import pt.ua.deti.tqs.airQuality.model.airVisual.WeatherEntry;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


@Service
public class AirVisualService {
    private final AirVisualAPI api;
    private final GeocodingService geocodingService;
    private final OpenWeatherService openWeatherService;
    private final Storage<String, List<String>> locations;
    private final Storage<AirVisualKey, WeatherEntry> weatherStorage;
    private final Logger logger = LoggerFactory.getLogger(AirVisualService.class);

    public AirVisualService(AirVisualAPI api, GeocodingService geocodingService, OpenWeatherService openWeatherService, Storage<String, List<String>> locations, Storage<AirVisualKey, WeatherEntry> weatherStorage) {
        this.api = api;
        this.geocodingService = geocodingService;
        this.openWeatherService = openWeatherService;

        this.locations = locations;
        this.weatherStorage = weatherStorage;
    }

    @Autowired
    public AirVisualService(AirVisualAPI api, GeocodingService geocodingService, OpenWeatherService openWeatherService) {
        this.api = api;
        this.geocodingService = geocodingService;
        this.openWeatherService = openWeatherService;

        this.locations = new Storage<>();
        this.weatherStorage = new Storage<>();
    }

    public List<String> countries() throws URISyntaxException, IOException {
        logger.info("[SERVICE AirVisualService - COUNTRIES]");
        List<String> cached = locations.get("countries");
        if (cached == null) {
            Statistics.getInstance().missCache();
            logger.info("\tFetching countries from AirVisual API");
            List<String> countries = api.getCountries();
            if (countries == null) {
                Statistics.getInstance().failedAirVisualRequest();
                logger.error("\tFailed to fetch countries from AirVisual API");
                return null;
            }
            Statistics.getInstance().successfulAirVisualRequest();
            locations.add("countries", countries);
            logger.info("\t[CACHE SAVE] Countries");
            return countries;
        }
        Statistics.getInstance().hitCache();
        logger.info("\t[CACHE] Getting Countries List");
        return cached;
    }

    public List<String> states(String country) throws URISyntaxException, IOException {
        logger.info("[SERVICE AirVisualService - STATES]");
        List<String> cached = locations.get(country);
        if (cached == null) {
            Statistics.getInstance().missCache();
            logger.info("\tFetching states from AirVisual API");
            List<String> states = api.getStates(country);
            if (states == null) {
                logger.error("\tFailed to fetch states from AirVisual API");
                Statistics.getInstance().failedAirVisualRequest();
                return null;
            }

            locations.add(country, states);
            logger.info("\t[CACHE SAVE] States");
            Statistics.getInstance().successfulAirVisualRequest();
            return states;
        }
        Statistics.getInstance().hitCache();
        logger.info("\t[CACHE] Getting States List");
        return cached;
    }

    public List<String> cities(String country, String state) throws URISyntaxException, IOException {
        logger.info("[SERVICE AirVisualService - CITIES]");
        List<String> cached = locations.get(country + ":" + state);
        if (cached == null) {
            Statistics.getInstance().missCache();
            logger.info("\tFetching cities from AirVisual API");
            List<String> cities = api.getCities(country, state);
            if (cities == null) {
                logger.error("\tFailed to fetch cities from AirVisual API");
                Statistics.getInstance().failedAirVisualRequest();
                return null;
            }
            Statistics.getInstance().successfulAirVisualRequest();
            locations.add(country + ":" + state, cities);
            logger.info("\t[CACHE SAVE] Cities");
            return cities;
        }
        Statistics.getInstance().hitCache();
        logger.info("\t[CACHE] Getting Cities List");
        return cached;
    }

    public WeatherEntry getWeather(String city, String state, String country) throws URISyntaxException, IOException {
        logger.info("[SERVICE AirVisualService - WEATHER]");
        WeatherEntry fromCache = weatherStorage.get(new AirVisualKey(city, state, country));

        if (fromCache != null) {
            Statistics.getInstance().hitCache();
            logger.info("\t[Cache] Getting weather info for city {}", city);
            return fromCache;
        }
        Statistics.getInstance().missCache();
        logger.info("\tFetching weather from AirVisual API");
        WeatherEntry fromApi = api.getWeather(city, state, country);
        if (fromApi == null) {
            logger.error("\tFailed to fetch weather from AirVisual API");
            Statistics.getInstance().failedAirVisualRequest();

            logger.info("\tFetching weather from AirVisual API");
            CoordEntry coord = geocodingService.getCoordinates(city,country);
            if (coord == null) {
                logger.error("\tFailed to fetch Coordinates from Geocoding API");
                Statistics.getInstance().failedGeocodingRequest();
                return null;
            }
            Statistics.getInstance().successfulGeocodingRequest();

            fromApi = openWeatherService.getWeather(coord.getLat(), coord.getLon());
            if (fromApi == null) {
                logger.error("\tFailed to fetch weather from OpenWeather API");
                Statistics.getInstance().failedOpenWeatherRequest();
                return null;
            }
            Statistics.getInstance().successfulOpenWeatherRequest();
            fromApi.setState(state);
        }

        weatherStorage.add(new AirVisualKey(city, state, country), fromApi);
        logger.info("\t[Cache] Saving weather info for city {}", city);
        Statistics.getInstance().successfulAirVisualRequest();
        return fromApi;
    }

}
