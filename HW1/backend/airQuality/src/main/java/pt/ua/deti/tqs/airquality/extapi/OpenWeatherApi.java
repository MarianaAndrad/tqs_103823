package pt.ua.deti.tqs.airquality.extapi;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.airquality.model.airvisual.WeatherEntry;
import pt.ua.deti.tqs.airquality.model.openweather.AirQualityEntry;

import java.io.IOException;

import static pt.ua.deti.tqs.airquality.ConfigUtils.timestampToDate;

@Service
public class OpenWeatherApi {
    @Value("${openweather.apikey}")
    private String apiKey;

    @Value("${openweather.baseurl}")
    private String baseUrl;

    private static final Logger log = LoggerFactory.getLogger(OpenWeatherApi.class);
    private final CloseableHttpClient httpClient;

    public OpenWeatherApi() {
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

    public WeatherEntry getWeatherInfo(Double lat, Double lon) throws IOException {
        log.info("[API  OpenWeather -- Weather]");
        log.info("\t[Request] Getting weather info for lat {} and lon {}", lat, lon);
        String url = baseUrl + "weather?";
        String response = constructUrlRequest(url + "lat=" + lat + "&lon=" + lon, 1);
        JSONObject responseJson =  new JSONObject(response);
        log.debug("\tResponse: {}", responseJson);

        if(responseJson.get("cod").toString().equals("404")) {
            log.info("\t[Error] No results found for lat {} and lon {}", lat,lon);
            return null;
        }

        JSONObject coord = responseJson.getJSONObject("coord");
        JSONArray weather = responseJson.getJSONArray("weather");
        JSONObject main = responseJson.getJSONObject("main");
        JSONObject wind = responseJson.getJSONObject("wind");
        JSONObject sys = responseJson.getJSONObject("sys");
        Long timestamp = responseJson.getLong("dt");
        String date = timestampToDate(timestamp);

        WeatherEntry weatherInfo = new WeatherEntry();
        weatherInfo.setLongitude(coord.getDouble("lon"));
        weatherInfo.setLatitude(coord.getDouble("lat"));
        weatherInfo.setIcon(weather.getJSONObject(0).getString("icon"));
        weatherInfo.setTemperature(main.getDouble("temp"));
        weatherInfo.setPressure((double) main.getInt("pressure"));
        weatherInfo.setHumidity((double) main.getInt("humidity"));
        weatherInfo.setWindSpeed(wind.getDouble("speed"));
        weatherInfo.setWindDirection(wind.getDouble("deg"));
        weatherInfo.setDate(date);
        weatherInfo.setCountry(sys.getString("country"));
        weatherInfo.setCity(responseJson.getString("name"));

        log.info("\tWeatherInfo: {}", weatherInfo);
        log.info("\t[Response] Get Weather Success");
        return weatherInfo;
    }

    public AirQualityEntry getAirQualityInfo(Double lat, Double lon) throws IOException {
        log.info("[API  OpenWeather -- Air Quality]");
        log.info("\t[Request] Getting air quality info for lat {} and lon {}", lat, lon);
        String url = baseUrl + "air_pollution?";
        String response = constructUrlRequest(url + "lat=" + lat + "&lon=" + lon, 1);
        JSONObject responseJson =  new JSONObject(response);
        log.debug("\tResponse: {}", responseJson);

        if(responseJson.get("list").toString().equals("404")) {
            log.info("\t[Error] No results found for lat {} and lon {}", lat,lon);
            return null;
        }

        JSONArray dataList = responseJson.getJSONArray("list");
        JSONObject main = dataList.getJSONObject(0).getJSONObject("main");
        JSONObject components = dataList.getJSONObject(0).getJSONObject("components");
        JSONObject dt = dataList.getJSONObject(0);
        Long timestamp = dt.getLong("dt");
        String date = timestampToDate(timestamp);

        AirQualityEntry airQualityInfo = new AirQualityEntry();
        airQualityInfo.setLatitude(lat);
        airQualityInfo.setLongitude(lon);
        airQualityInfo.setAqi(main.getDouble("aqi"));
        airQualityInfo.setCo(components.getDouble("co"));
        airQualityInfo.setNo(components.getDouble("no"));
        airQualityInfo.setNo2(components.getDouble("no2"));
        airQualityInfo.setO3(components.getDouble("o3"));
        airQualityInfo.setSo2(components.getDouble("so2"));
        airQualityInfo.setPm2_5(components.getDouble("pm2_5"));
        airQualityInfo.setPm10(components.getDouble("pm10"));
        airQualityInfo.setNh3(components.getDouble("nh3"));
        airQualityInfo.setDate(date);

        log.info("\tAirQualityInfo: {}", airQualityInfo);
        log.info("\t[Response] Get Air Quality Success");
        return airQualityInfo;
    }
}
