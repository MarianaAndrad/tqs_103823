package pt.ua.deti.tqs.airQuality.model.Geocoding;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GeoKey {
    private String country;
    private String city;
}
