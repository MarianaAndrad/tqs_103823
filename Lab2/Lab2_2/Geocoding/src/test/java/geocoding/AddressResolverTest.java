package geocoding;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @Mock
    ISimpleHttpClient client;

    @InjectMocks
    AddressResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new AddressResolver(client);
    }


    @Test
    //@Disabled
    @DisplayName("when resolve deti gps return jacinto magalhae address")
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        //todo: implement test
        double latitude = 40.634159;
        double longitude = -8.65795;

        String response ="{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"© 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.634159,\"lng\":-8.65795}},\"locations\":[{\"street\":\"Avenida João Jacinto de Magalhães\",\"adminArea6\":\"Aveiro\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Aveiro\",\"adminArea5Type\":\"City\",\"adminArea4\":\"Aveiro\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-149\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.63416,\"lng\":-8.65795},\"displayLatLng\":{\"lat\":40.63416,\"lng\":-8.65795},\"mapUrl\":\"\",\"roadMetadata\":{\"speedLimitUnits\":\"mph\",\"speedLimit\":31,\"tollRoad\":null}}]}]}";
        when(client.doHttpGet(anyString())).thenReturn(response);

        // will crash for now...need to set the resolver before using it
        Optional<Address> result = resolver.findAddressForLocation( latitude,longitude);
        //return
        Address expected = new Address( "Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null);

        assertTrue(result.isPresent());
        assertEquals( expected, result.get());

        verify(client).doHttpGet(anyString());

    }

    @Test
    //@Disabled
    @DisplayName("when bad coordidates then return no valid address")
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        ///todo: implement test
        // Bad coordinates
        double latitude = 50.1234;
        double longitude = -200.5678;

        String response ="{\"info\":{\"statuscode\":400,\"copyright\":{\"text\":\"© 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2022 MapQuest, Inc.\"},\"messages\":[\"Illegal argument from request: Invalid LatLng specified.\"]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{},\"locations\":[]}]}";
        when(client.doHttpGet(anyString())).thenReturn(response);

        Optional<Address> result = resolver.findAddressForLocation(latitude,longitude);

        assertFalse(result.isPresent());
        verify(client).doHttpGet(anyString());

    }

    @Test
    @DisplayName("when coordinates null then return no valid address")
    public void whenCoordinatesNull_thenReturnExpection() throws IOException, URISyntaxException, ParseException {
        //Coordinates null
        Double latitude = null;
        Double longitude = null;

        assertThrows(NullPointerException.class, () -> resolver.findAddressForLocation(latitude, longitude));
    }

}