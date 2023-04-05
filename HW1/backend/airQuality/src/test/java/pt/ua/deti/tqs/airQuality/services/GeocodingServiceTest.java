package pt.ua.deti.tqs.airQuality.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ua.deti.tqs.airQuality.extApi.GeocodingAPI;
import pt.ua.deti.tqs.airQuality.model.Geocoding.CoordEntry;
import pt.ua.deti.tqs.airQuality.model.Storage;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class GeocodingServiceTest {

    @MockBean
    private GeocodingAPI api;

    @MockBean
    private Storage<String, CoordEntry> storage;

    private GeocodingService service;

    @BeforeEach
    void setUp() {
        service = new GeocodingService(api, storage);
    }

    @Test
    void testGetCoordWhenInCache() throws IOException, URISyntaxException {
        when(storage.get("Portugal:Ovar")).thenReturn(new CoordEntry(42.85, -8.62));
        CoordEntry entry = service.getCoordinates("Ovar", "Portugal");

        assertThat(entry.getLat(), is(42.85));
        assertThat(entry.getLon(), is(-8.62));

        verify(storage, times(1)).get("Portugal:Ovar");
        verify(api, times(0)).getCoords("Ovar", "Portugal");
    }


    @Test
    void testGetCoordWhenNotInCache() throws IOException, URISyntaxException {
        when(storage.get("Portugal:Ovar")).thenReturn(null);
        when(api.getCoords("Ovar", "Portugal")).thenReturn(new CoordEntry(42.85, -8.62));
        CoordEntry entry = service.getCoordinates("Ovar", "Portugal");
        assertEquals(42.85, entry.getLat());
        assertEquals(-8.62, entry.getLon());

        verify(storage,times(1)).get("Portugal:Ovar");
        verify(api,times(1)).getCoords("Ovar", "Portugal");
    }

    @Test
    void testGetCoordNotInCacheOrApi() throws IOException, URISyntaxException {
        when(storage.get("Portugal:Ovar")).thenReturn(null);
        when(api.getCoords("Ovar", "Portugal")).thenReturn(null);
        CoordEntry entry = service.getCoordinates("Ovar", "Portugal");
        assertNull(entry);

        verify(storage,times(1)).get("Portugal:Ovar");
        verify(api,times(1)).getCoords("Ovar", "Portugal");
    }
}