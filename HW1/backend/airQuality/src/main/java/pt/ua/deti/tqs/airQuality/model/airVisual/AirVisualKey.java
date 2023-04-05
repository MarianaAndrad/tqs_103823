package pt.ua.deti.tqs.airQuality.model.airVisual;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AirVisualKey {
    private String city;
    private String state;
    private String country;
}
