package pt.ua.deti.tqs.airquality.model.airvisual;

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
