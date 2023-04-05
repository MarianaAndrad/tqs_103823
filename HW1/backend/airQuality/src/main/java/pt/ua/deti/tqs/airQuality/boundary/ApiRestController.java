package pt.ua.deti.tqs.airQuality.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.tqs.airQuality.model.Geocoding.CoordEntry;
import pt.ua.deti.tqs.airQuality.model.Statistics;
import pt.ua.deti.tqs.airQuality.model.airVisual.WeatherEntry;
import pt.ua.deti.tqs.airQuality.model.openWeather.AirQualityEntry;
import pt.ua.deti.tqs.airQuality.services.AirVisualService;
import pt.ua.deti.tqs.airQuality.services.GeocodingService;
import pt.ua.deti.tqs.airQuality.services.OpenWeatherService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ApiRestController {

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
    public ResponseEntity<List<String>> getCountries() throws URISyntaxException, IOException {
        List<String> countries = airVisualService.countries();
        if (countries == null) {
            Statistics.getInstance().failedRequest();
            return ResponseEntity.notFound().build();
        }
        Statistics.getInstance().successfulRequest();
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/{country}/states")
    public ResponseEntity<List<String>> getStates(@PathVariable(value = "country") String country) throws URISyntaxException, IOException {
        List<String> states = airVisualService.states(country);
        if (states == null) {
            Statistics.getInstance().failedRequest();
            return ResponseEntity.notFound().build();
        }
        Statistics.getInstance().successfulRequest();
        return ResponseEntity.ok().body(states);
    }

    @GetMapping("/{country}/{state}/cities")
    public ResponseEntity<List<String>> getCities(@PathVariable(value = "country") String country, @PathVariable(value = "state") String state) throws URISyntaxException, IOException {
        List<String> cities = airVisualService.cities(country, state);
        if (cities == null) {
            Statistics.getInstance().failedRequest();
            return ResponseEntity.notFound().build();
        }
        Statistics.getInstance().successfulRequest();
        return ResponseEntity.ok().body(cities);
    }

    @GetMapping("/{country}/{state}/{city}/weather")
    public ResponseEntity<WeatherEntry> getWeather(@PathVariable(value = "country") String country, @PathVariable(value = "state") String state, @PathVariable(value = "city") String city) throws URISyntaxException, IOException {
        WeatherEntry weather = airVisualService.getWeather(city, state, country);
        if (weather == null) {
            Statistics.getInstance().failedRequest();
            return ResponseEntity.notFound().build();
        }
        Statistics.getInstance().successfulRequest();
        return ResponseEntity.ok().body(weather);
    }

    @GetMapping("/{country}/{city}/geocoding")
    public ResponseEntity<CoordEntry> getGeocoding(@PathVariable(value = "country") String country, @PathVariable(value = "city") String city) throws URISyntaxException, IOException {
        CoordEntry geocoding = geocodingService.getCoordinates(city, country);
        if (geocoding == null) {
            Statistics.getInstance().failedRequest();
            return ResponseEntity.notFound().build();
        }
        Statistics.getInstance().successfulRequest();
        return ResponseEntity.ok().body(geocoding);
    }

    @GetMapping("/{lat}/{lon}/weather")
    public ResponseEntity<WeatherEntry> getWeather(@PathVariable(value = "lat") Double lat, @PathVariable(value = "lon") Double lon) throws URISyntaxException, IOException {
        WeatherEntry weather = openWeatherService.getWeather(lat, lon);
        if (weather == null) {
            Statistics.getInstance().failedRequest();
            return ResponseEntity.notFound().build();
        }
        Statistics.getInstance().successfulRequest();
        return ResponseEntity.ok().body(weather);
    }

    @GetMapping("/{lat}/{lon}/air-quality")
    public ResponseEntity<AirQualityEntry> getAirQuality(@PathVariable(value = "lat") Double lat, @PathVariable(value = "lon") Double lon) throws URISyntaxException, IOException {
        AirQualityEntry airQuality = openWeatherService.getAirQuality(lat, lon);
        if (airQuality == null) {
            Statistics.getInstance().failedRequest();
            return ResponseEntity.notFound().build();
        }
        Statistics.getInstance().successfulRequest();
        return ResponseEntity.ok().body(airQuality);
    }

    @GetMapping("/statistics")
    public ResponseEntity<HashMap<String, Integer>> getStatistics() {
        return ResponseEntity.ok().body(statistics.getStatistics());
    }
}

