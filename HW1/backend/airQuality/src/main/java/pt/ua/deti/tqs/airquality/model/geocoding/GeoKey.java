package pt.ua.deti.tqs.airquality.model.geocoding;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeoKey {
    private String country;
    private String city;
}
