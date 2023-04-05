package pt.ua.deti.tqs.airQuality.model;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.airQuality.model.Storage;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    private Storage<String, String> storage;

    @BeforeEach
    void setup() {
        storage = new Storage<>(4); // 4 ms
    }

    @Test
    void testAddAndGet(){
        storage.add("key", "value");
        assertEquals("value", storage.get("key"));
    }

    @Test
    void testTTL() {
        storage.add("key", "value");
        try{
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNull(storage.get("key"));
    }
}