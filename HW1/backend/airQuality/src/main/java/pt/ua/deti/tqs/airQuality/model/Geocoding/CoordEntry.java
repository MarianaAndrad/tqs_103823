package pt.ua.deti.tqs.airQuality.model.Geocoding;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoordEntry {
    private double lat;
    private double lon;
}
