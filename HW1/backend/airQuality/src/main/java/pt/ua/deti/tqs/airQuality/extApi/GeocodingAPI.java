package pt.ua.deti.tqs.airQuality.extApi;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.airQuality.model.Geocoding.CoordEntry;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class GeocodingAPI {
    @Value("${geocoding.apikey}")
    private String apiKey;

    @Value("${geocoding.baseurl}")
    private String baseUrl;

    private final CloseableHttpClient httpClient;
    private static final Logger log = LoggerFactory.getLogger(GeocodingAPI.class);

    public GeocodingAPI() {
        this.httpClient = HttpClients.createDefault();
    }

    private String constructUrlRequest(String url, int flag) throws IOException {
        HttpGet get = new HttpGet(url + (flag == 0 ? "appid=" : "&appid=") + apiKey);
        CloseableHttpResponse response = this.httpClient.execute(get);

        if (response != null) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
        return null;
    }

    public CoordEntry getCoords(String city, String country) throws IOException, URISyntaxException {
        log.info("[API  Geocoding -- Coordinates]");
        log.info("\t[Request] Getting coordinates info for city {} and country {}", city,country);
        String url = baseUrl + "direct?q=" + city + "," + country + "&limit=1";
        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString(), 1);

        log.debug("\tResponse: {}", response);
        System.out.println(response);
        try {
            JSONArray responseJson = new JSONArray(response);

            CoordEntry coord = new CoordEntry();
            coord.setLat(responseJson.getJSONObject(0).getDouble("lat"));
            coord.setLon(responseJson.getJSONObject(0).getDouble("lon"));

            log.info("\t[Response] Coordinates info for city {} and country {}: {}", city,country,coord);
            return coord;
        } catch (Exception e) {
            JSONObject msg = new JSONObject(response);
            log.error("\t[Error] Failed to get coordinates info for city {} and country {}", city,country);
            log.error("\t[Error] Error message: {}", msg.getString("message"));
            return null;
        }
    }
}
