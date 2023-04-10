package pt.ua.deti.tqs.airquality;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigUtilsTest {

    @Test
    void  TestConvertFromDataTimeToDate() {
        String dateTime = "2020-01-01T00:00:00.000Z";
        String expected = "01-01-2020";
        String result = ConfigUtils.convertDateTime(dateTime);
        assertEquals(expected, result);
    }

    @Test
    void TestConvertFromTimestampToDate() {
        long timestamp = 1577836800;
        String expected = "01-01-2020";
        String result = ConfigUtils.timestampToDate(timestamp);
        assertEquals(expected, result);
    }


}