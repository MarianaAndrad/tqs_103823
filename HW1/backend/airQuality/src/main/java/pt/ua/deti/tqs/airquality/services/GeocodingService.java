package pt.ua.deti.tqs.airquality.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.airquality.extapi.GeocodingApi;
import pt.ua.deti.tqs.airquality.model.Statistics;
import pt.ua.deti.tqs.airquality.model.geocoding.CoordEntry;
import pt.ua.deti.tqs.airquality.model.Storage;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class GeocodingService {
    private final GeocodingApi api;
    private final Storage<String, CoordEntry> storage;

    private final Logger logger = LoggerFactory.getLogger(GeocodingService.class);

    public GeocodingService(GeocodingApi api, Storage<String, CoordEntry> storage) {
        this.api = api;
        this.storage = storage;
    }

    @Autowired
    public GeocodingService(GeocodingApi api) {
        this.api = api;
        this.storage = new Storage<>();
    }

    public CoordEntry getCoordinates(String city, String country) throws IOException, URISyntaxException {
        logger.info("[SERVICE GeocodingService - COORDINATES]");
        CoordEntry entry = storage.get(country + ":" + city);
        if (entry == null) {
            Statistics.getInstance().missCache();
            logger.info("\tFetching coordinates from Geocoding API");
            entry = api.getCoords(city, country);
            if (entry == null) {
                logger.error("\tFailed to fetch coordinates from Geocoding API");
                Statistics.getInstance().failedGeocodingRequest();
                return null;
            }
            storage.add(country + ":" + city, entry);
            Statistics.getInstance().successfulGeocodingRequest();
            logger.info("\t[CACHE SAVE] Coordinates");
            return entry;
        }
        logger.info("\t[CACHE] Getting Coordinates");
        Statistics.getInstance().hitCache();
        return entry;
    }
}
