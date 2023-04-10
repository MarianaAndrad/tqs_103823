package pt.ua.deti.tqs.airquality.model.geocoding;

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
