package pt.ua.deti.tqs.airQuality.model.openWeather;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OpenWeatherKey {
    private Double latitude;
    private Double longitude;
}
