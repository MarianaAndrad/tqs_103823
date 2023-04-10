package pt.ua.deti.tqs.airquality.model.openweather;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AirQualityEntry {
    private Double latitude;
    private Double longitude;

    private Double aqi;
    private Double co;
    private Double no;
    private Double no2;
    private Double o3;
    private Double so2;
    private Double pm2_5;
    private Double pm10;
    private Double nh3;

    private String Date;
}
