package integration;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolver;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AddressResolverIT {

    private ISimpleHttpClient client;
    private AddressResolver resolver;



    @BeforeEach
    public void init(){
        client = new TqsBasicHttpClient();
        resolver = new AddressResolver(client);
    }

    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        //todo
        double lat = 40.634159;
        double lon = -8.65795;

        Optional<Address> result = resolver.findAddressForLocation(lat, lon);
        Address expected = new Address( "Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null);
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        //todo
        // repeat the same tests conditions from AddressResolverTest, without mocks
        double lat = 50.1234;
        double lon = -200.5678;

        Optional<Address> result = resolver.findAddressForLocation(lat, lon);

        assertFalse(result.isPresent());
        assertThrows(NoSuchElementException.class, () -> result.get());
    }

    @Test
    public void whenCoordinatesNull_thenReturnExpection() throws IOException, URISyntaxException, ParseException {

        Double lat = null;
        Double lon = null;

        assertThrows(NullPointerException.class, () -> resolver.findAddressForLocation(lat, lon));
    }



}
