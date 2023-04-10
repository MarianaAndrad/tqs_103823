package pt.ua.deti.tqs.airquality.boundary;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.tqs.airquality.model.geocoding.CoordEntry;
import pt.ua.deti.tqs.airquality.model.Statistics;
import pt.ua.deti.tqs.airquality.model.airvisual.WeatherEntry;
import pt.ua.deti.tqs.airquality.model.openweather.AirQualityEntry;
import pt.ua.deti.tqs.airquality.services.AirVisualService;
import pt.ua.deti.tqs.airquality.services.GeocodingService;
import pt.ua.deti.tqs.airquality.services.OpenWeatherService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ApiRestController {
    private Logger log = LoggerFactory.getLogger(ApiRestController.class);
    @Autowired
    private AirVisualService airVisualService;
    private Statistics statistics = Statistics.getInstance();
    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private OpenWeatherService openWeatherService;

    ApiRestController(AirVisualService airVisualService, GeocodingService geocodingService, OpenWeatherService openWeatherService) {
        this.airVisualService = airVisualService;
        this.geocodingService = geocodingService;
        this.openWeatherService = openWeatherService;
    }

    @GetMapping("/countries")
    @Operation(summary = "Get a list of countries")
    public ResponseEntity<List<String>> getCountries() throws URISyntaxException, IOException {
        log.info("[Controller] Getting Countries List");
        List<String> countries = airVisualService.countries();
        if (countries == null) {
            log.info("[Controller] Countries List Not Found");
            Statistics.getInstance().failedRequestBySource("/countries");
            return ResponseEntity.notFound().build();
        }
        log.info("[Controller] Countries List Found");
        Statistics.getInstance().successfulRequestBySource("/countries");
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/{country}/states")
    @Operation(summary = "Get a list of states")
    public ResponseEntity<List<String>> getStates(@PathVariable(value = "country") String country) throws URISyntaxException, IOException {
        log.info("[Controller] Getting States List");
        List<String> states = airVisualService.states(country);
        if (states == null) {
            log.info("[Controller] States List Not Found");
            Statistics.getInstance().failedRequestBySource("/" + country + "/states");
            return ResponseEntity.notFound().build();
        }
        log.info("[Controller] States List Found");
        Statistics.getInstance().successfulRequestBySource("/" + country + "/states");
        return ResponseEntity.ok().body(states);
    }

    @GetMapping("/{country}/{state}/cities")
    @Operation(summary = "Get a list of cities")
    public ResponseEntity<List<String>> getCities(@PathVariable(value = "country") String country, @PathVariable(value = "state") String state) throws URISyntaxException, IOException {
        List<String> cities = airVisualService.cities(country, state);
        log.info("[Controller] Getting Cities List");
        if (cities == null) {
            log.info("[Controller] Cities List Not Found");
            Statistics.getInstance().failedRequestBySource("/"+ country + "/" + state + "/cities");
            return ResponseEntity.notFound().build();
        }
        log.info("[Controller] Cities List Found");
        Statistics.getInstance().successfulRequestBySource("/"+ country + "/" + state + "/cities");
        return ResponseEntity.ok().body(cities);
    }

    @GetMapping("/{country}/{state}/{city}/weather")
    @Operation(summary = "Get the weather By City, State and Country")
    public ResponseEntity<WeatherEntry> getWeather(@PathVariable(value = "country") String country, @PathVariable(value = "state") String state, @PathVariable(value = "city") String city) throws URISyntaxException, IOException {
        log.info("[Controller] Getting Weather By CIty, State and Country");
        WeatherEntry weather = airVisualService.getWeather(city, state, country);
        if (weather == null) {
            log.info("[Controller] Weather Not Found");
            Statistics.getInstance().failedRequestBySource("/"+ country + "/" + state + "/" + city + "/weather (air visual)");
            return ResponseEntity.notFound().build();
        }
        log.info("[Controller] Weather Found");
        Statistics.getInstance().successfulRequestBySource("/"+ country + "/" + state + "/" + city + "/weather (air visual)");
        return ResponseEntity.ok().body(weather);
    }

    @GetMapping("/{country}/{city}/geocoding")
    @Operation(summary = "Get the geocoding By City and Country")
    public ResponseEntity<CoordEntry> getGeocoding(@PathVariable(value = "country") String country, @PathVariable(value = "city") String city) throws URISyntaxException, IOException {
        log.info("[Controller] Getting Geocoding By City and Country");
        CoordEntry geocoding = geocodingService.getCoordinates(city, country);
        if (geocoding == null) {
            log.info("[Controller] Geocoding Not Found");
            Statistics.getInstance().failedRequestBySource("/"+ country + "/" + city + "/geocoding");
            return ResponseEntity.notFound().build();
        }
        log.info("[Controller] Geocoding Found");
        Statistics.getInstance().successfulRequestBySource("/"+ country + "/" + city + "/geocoding");
        return ResponseEntity.ok().body(geocoding);
    }

    @GetMapping("/{lat}/{lon}/weather")
    @Operation(summary = "Get the weather By Latitude and Longitude")
    public ResponseEntity<WeatherEntry> getWeather(@PathVariable(value = "lat") Double lat, @PathVariable(value = "lon") Double lon) throws IOException {
        log.info("[Controller] Getting Weather By Latitude and Longitude");
        WeatherEntry weather = openWeatherService.getWeather(lat, lon);
        if (weather == null) {
            log.info("[Controller] Weather Not Found");
            Statistics.getInstance().failedRequestBySource("/"+ lat + "/" + lon + "/weather (open weather)");
            return ResponseEntity.notFound().build();
        }
        log.info("[Controller] Weather Found");
        Statistics.getInstance().successfulRequestBySource("/"+ lat + "/" + lon + "/weather (open weather)");
        return ResponseEntity.ok().body(weather);
    }

    @GetMapping("/{lat}/{lon}/air-quality")
    @Operation(summary = "Get the air quality By Latitude and Longitude")
    public ResponseEntity<AirQualityEntry> getAirQuality(@PathVariable(value = "lat") Double lat, @PathVariable(value = "lon") Double lon) throws IOException {
        log.info("[Controller] Getting Air Quality By Latitude and Longitude");
        AirQualityEntry airQuality = openWeatherService.getAirQuality(lat, lon);
        if (airQuality == null) {
            log.info("[Controller] Air Quality Not Found");
            Statistics.getInstance().failedRequestBySource("/"+ lat + "/" + lon + "/air-quality");
            return ResponseEntity.notFound().build();
        }
        log.info("[Controller] Air Quality Found");
        Statistics.getInstance().successfulRequestBySource("/"+ lat + "/" + lon + "/air-quality");
        return ResponseEntity.ok().body(airQuality);
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get the statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        log.info("[Controller] Getting Statistics");
        Statistics.getInstance().successfulRequestBySource("/statistics");
        return ResponseEntity.ok().body(statistics.getStatistics());
    }
}

