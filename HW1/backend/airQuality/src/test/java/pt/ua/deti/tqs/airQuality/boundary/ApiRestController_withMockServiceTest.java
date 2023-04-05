package pt.ua.deti.tqs.airQuality.boundary;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.airQuality.services.AirVisualService;
import pt.ua.deti.tqs.airQuality.services.GeocodingService;
import pt.ua.deti.tqs.airQuality.services.OpenWeatherService;

@WebMvcTest(ApiRestController.class)
class ApiRestController_withMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirVisualService airVisualService;

    @MockBean
    private GeocodingService geocodingService;

    @MockBean
    private OpenWeatherService openWeatherService;

    @BeforeEach
    public void setUp() throws Exception {
    }
}