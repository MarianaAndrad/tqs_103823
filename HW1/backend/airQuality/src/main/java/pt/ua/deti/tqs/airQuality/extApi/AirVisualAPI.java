package pt.ua.deti.tqs.airQuality.extApi;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.airQuality.model.airVisual.WeatherEntry;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static pt.ua.deti.tqs.airQuality.ConfigUtils.convertDateTime;


@Service
public class AirVisualAPI {
    @Value("${airvisual.apikey}")
    private String apiKey;

    @Value("${airvisual.baseurl}")
    private String baseUrl;

    private final CloseableHttpClient httpClient;
    private static final Logger log = LoggerFactory.getLogger(AirVisualAPI.class);

    public AirVisualAPI() {
        this.httpClient = HttpClients.createDefault();
    }

    private String constructUrlRequest(String url, int flag ) throws IOException {
        HttpGet get = new HttpGet(url + (flag == 0 ? "key=" : "&key=") + apiKey);
        CloseableHttpResponse response = this.httpClient.execute(get);

        if (response != null) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
        return null;
    }

    public List<String> getCountries() throws URISyntaxException, IOException {
        log.info("[API AirVisual]");
        log.info("\t[REQUEST] Getting Countries List");

        String url = baseUrl + "countries?";
        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString(), 0);
        log.debug("\tResponse: {}", response);

        JSONObject responseJson = new JSONObject(response);

        if (!responseJson.getString("status").equals("success")) {
            JSONObject data = responseJson.getJSONObject("data");
            log.error("\t[Error] getting countries from AirVisual API: {}", data.get("message").toString());
            return null;
        }

        JSONArray countriesJson = responseJson.getJSONArray("data");
        List<String> countries = new ArrayList<>();

        for (Object countryObj : countriesJson) {
            JSONObject countryJson = (JSONObject) countryObj;
            countries.add(countryJson.getString("country"));
        }

        log.info("\tCountries: {}", countries);
        log.info("\t[Response] Get Countries Success");
        return countries;
    }

    public List<String> getStates(String country) throws URISyntaxException, IOException {
        log.info("[API AirVisual]");
        log.info("\t[REQUEST] Getting States List");

        String url = baseUrl + "states?country=" + country;
        log.info("\t[REQUEST] Getting List the States By Country: " + country);
        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString(),1);
        log.debug("Response: {}", response);

        JSONObject responseJson = new JSONObject(response);

        if (responseJson.getString("status").equals("fail")) {
            JSONObject data = responseJson.getJSONObject("data");
            log.error("\t[Error] getting states from AirVisual API: {}", data.get("message").toString());
            return null;
        }

        JSONArray statesJson = responseJson.getJSONArray("data");
        List<String> states = new ArrayList<>();

        for (Object stateObj : statesJson) {
            JSONObject stateJson = (JSONObject) stateObj;
            states.add(stateJson.getString("state"));
        }

        log.info("\tStates: {}", states);
        log.info("\t[Response] Get States Success");
        return states;
    }

    public List<String> getCities(String country, String state) throws URISyntaxException, IOException {
        log.info("[API AirVisual -- Cities]");
        log.info("\t[REQUEST] Getting Cities List");

        String url = baseUrl + "cities?state=" + state + "&country=" + country;
        log.info("\t[REQUEST] Getting List the Cities By State: " + state + " and Country: " + country);
        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString(),1);
        log.debug("\tResponse: {}", response);

        JSONObject responseJson = new JSONObject(response);

        if (responseJson.getString("status").equals("fail")) {
            JSONObject data = responseJson.getJSONObject("data");
            log.error("\t[Error] getting cities from AirVisual API: {}", data.get("message").toString());
            return null;
        }

        JSONArray citiesJson = responseJson.getJSONArray("data");
        List<String> cities = new ArrayList<>();

        for (Object cityObj : citiesJson) {
            JSONObject cityJson = (JSONObject) cityObj;
            cities.add(cityJson.getString("city"));
        }

        log.info("\tCities: {}", cities);
        log.info("\t[Response] Get Cities Success");
        return cities;
    }

    public WeatherEntry getWeather(String city, String state, String country) throws URISyntaxException, IOException {
        log.info("[API AirVisual -- Weather]");
        log.info("\t[Request] Getting weather info for city {}", city);

        String url = baseUrl + "city?city=" + city + "&state=" + state + "&country=" + country;
        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString(),1);
        log.debug("\tResponse: {}", response);
        System.out.println(response);

        JSONObject responseJson = new JSONObject(response);
        if (responseJson.getString("status").equals("fail")) {
            JSONObject data = new JSONObject(responseJson.get("data").toString());
            log.info("\t[Error] getting weather from AirVisual API: {}", data.get("message").toString());
            return null;
        }

        JSONObject data = new JSONObject(responseJson.get("data").toString());
        JSONObject current = new JSONObject(data.get("current").toString());
        JSONObject weather = new JSONObject(current.get("weather").toString());
        JSONObject location = new JSONObject(data.get("location").toString());
        JSONArray coordinates = new JSONArray(location.get("coordinates").toString());

        WeatherEntry weatherObject = new WeatherEntry(
                data.get("city").toString(),
                data.get("state").toString(),
                data.get("country").toString(),
                Double.parseDouble(coordinates.get(0).toString()),
                Double.parseDouble(coordinates.get(1).toString()),
                convertDateTime(weather.get("ts").toString()),
                Double.parseDouble(weather.get("tp").toString()),
                Double.parseDouble(weather.get("pr").toString()),
                Double.parseDouble(weather.get("hu").toString()),
                Double.parseDouble(weather.get("ws").toString()),
                Double.parseDouble(weather.get("wd").toString()),
                weather.get("ic").toString());

        log.info("\tWeather: {}", weatherObject);
        log.info("\t[Response] Get Weather Success");
        return weatherObject;
    }

}
