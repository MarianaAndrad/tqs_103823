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

    private Integer successfulGeocodingRequests = 0; //number of requests made to the Geocoding API with success
    private Integer failedGeocodingRequests = 0; //number of requests made to the Geocoding API with failure

    private Integer successfulAirVisualRequests = 0; //number of requests made to the Air Quality API with success
    private Integer failedAirVisualRequests = 0; //number of requests made to the Air Quality API with failure

    private Integer successfulOpenWeatherRequests = 0; //number of requests made to the Weather API with success
    private Integer failedOpenWeatherRequests = 0; //number of requests made to the Weather API with failure

    private HashMap<String, Integer> successfulRequestsBySource = new HashMap<>();
    private HashMap<String, Integer> failedRequestsBySource = new HashMap<>();

    public void hitCache() {
        cacheHits++;
    }

    public void missCache() {
        cacheMisses++;
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

    public void successfulRequestBySource(String source) {
        if (successfulRequestsBySource.containsKey(source)) {
            successfulRequestsBySource.put(source, successfulRequestsBySource.get(source) + 1);
        } else {
            successfulRequestsBySource.put(source, 1);
        }
    }

    public void failedRequestBySource(String source) {
        if (failedRequestsBySource.containsKey(source)) {
            failedRequestsBySource.put(source, failedRequestsBySource.get(source) + 1);
        } else {
            failedRequestsBySource.put(source, 1);
        }
    }

    public void reset() {
        cacheHits = 0;
        cacheMisses = 0;
        successfulGeocodingRequests = 0;
        failedGeocodingRequests = 0;
        successfulAirVisualRequests = 0;
        failedAirVisualRequests = 0;
        successfulOpenWeatherRequests = 0;
        failedOpenWeatherRequests = 0;
        successfulRequestsBySource = new HashMap<>();
        failedRequestsBySource = new HashMap<>();

    }

    public HashMap<String, Object> getStatistics() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("cacheHits", cacheHits);
        result.put("cacheMisses", cacheMisses);
        result.put("successfulRequests", successfulRequestsBySource.values().stream().reduce(0, Integer::sum));
        result.put("failedRequests", failedRequestsBySource.values().stream().reduce(0, Integer::sum));
        result.put("successfulGeocodingRequests", successfulGeocodingRequests);
        result.put("failedGeocodingRequests", failedGeocodingRequests);
        result.put("successfulAirVisualRequests", successfulAirVisualRequests);
        result.put("failedAirVisualRequests", failedAirVisualRequests);
        result.put("successfulOpenWeatherRequests", successfulOpenWeatherRequests);
        result.put("failedOpenWeatherRequests", failedOpenWeatherRequests);
        result.put("totalRequests", successfulRequestsBySource.values().stream().reduce(0, Integer::sum) + failedRequestsBySource.values().stream().reduce(0, Integer::sum));
        result.put("successfulRequestsBySource", successfulRequestsBySource);
        result.put("failedRequestsBySource", failedRequestsBySource);

        return result;
    }
}
