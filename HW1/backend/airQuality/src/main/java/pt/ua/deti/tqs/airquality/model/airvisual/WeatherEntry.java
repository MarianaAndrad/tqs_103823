package pt.ua.deti.tqs.airquality.model.airvisual;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WeatherEntry {
    private String city;
    private String state;
    private String country;
    private Double latitude;
    private Double longitude;

    private String date;
    private Double temperature;
    private Double pressure;
    private Double humidity;
    private Double windSpeed;
    private Double windDirection;
    private String icon;
}
