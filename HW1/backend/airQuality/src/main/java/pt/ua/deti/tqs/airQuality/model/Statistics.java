package pt.ua.deti.tqs.airQuality.model;

import java.util.HashMap;

public class Statistics {
    private static Statistics instance = null;

    private Statistics() {}

    public static Statistics getInstance() {
        if (instance == null) {
            instance = new Statistics();
        }
        return instance;
    }

    private Integer cacheHits = 0; // number of times the cache was used
    private Integer cacheMisses = 0; //number of times the cache was not used

    private Integer successfulRequests = 0; //number of requests made to our API with success
    private Integer failedRequests = 0; //number of requests made to our API with failure

    private Integer successfulGeocodingRequests = 0; //number of requests made to the Geocoding API with success
    private Integer failedGeocodingRequests = 0; //number of requests made to the Geocoding API with failure

    private Integer successfulAirVisualRequests = 0; //number of requests made to the Air Quality API with success
    private Integer failedAirVisualRequests = 0; //number of requests made to the Air Quality API with failure

    private Integer successfulOpenWeatherRequests = 0; //number of requests made to the Weather API with success
    private Integer failedOpenWeatherRequests = 0; //number of requests made to the Weather API with failure


    public void hitCache() {
        cacheHits++;
    }

    public void missCache() {
        cacheMisses++;
    }

    public void successfulRequest() {
        successfulRequests++;
    }

    public void failedRequest() {
        failedRequests++;
    }

    public void successfulGeocodingRequest() {
        successfulGeocodingRequests++;
    }

    public void failedGeocodingRequest() {
        failedGeocodingRequests++;
    }

    public void successfulAirVisualRequest() {
        successfulAirVisualRequests++;
    }

    public void failedAirVisualRequest() {
        failedAirVisualRequests++;
    }

    public void successfulOpenWeatherRequest() {
        successfulOpenWeatherRequests++;
    }

    public void failedOpenWeatherRequest() {
        failedOpenWeatherRequests++;
    }

    public void reset() {
        cacheHits = 0;
        cacheMisses = 0;
        successfulRequests = 0;
        failedRequests = 0;
        successfulGeocodingRequests = 0;
        failedGeocodingRequests = 0;
        successfulAirVisualRequests = 0;
        failedAirVisualRequests = 0;
        successfulOpenWeatherRequests = 0;
        failedOpenWeatherRequests = 0;
    }

    public HashMap<String, Integer> getStatistics() {
        HashMap<String, Integer> result = new HashMap<>();

        result.put("cacheHits", cacheHits);
        result.put("cacheMisses", cacheMisses);
        result.put("successfulRequests", successfulRequests);
        result.put("failedRequests", failedRequests);
        result.put("successfulGeocodingRequests", successfulGeocodingRequests);
        result.put("failedGeocodingRequests", failedGeocodingRequests);
        result.put("successfulAirVisualRequests", successfulAirVisualRequests);
        result.put("failedAirVisualRequests", failedAirVisualRequests);
        result.put("successfulOpenWeatherRequests", successfulOpenWeatherRequests);
        result.put("failedOpenWeatherRequests", failedOpenWeatherRequests);

        return result;
    }
}
