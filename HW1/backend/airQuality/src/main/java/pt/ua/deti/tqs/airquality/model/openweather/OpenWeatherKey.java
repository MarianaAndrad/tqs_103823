package pt.ua.deti.tqs.airquality.model.openweather;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OpenWeatherKey {
    private Double latitude;
    private Double longitude;
}
