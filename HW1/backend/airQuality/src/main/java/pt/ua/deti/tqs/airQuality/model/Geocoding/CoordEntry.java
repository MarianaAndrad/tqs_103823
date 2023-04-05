package pt.ua.deti.tqs.airQuality.model.Geocoding;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CoordEntry {
    private double lat;
    private double lon;
}
