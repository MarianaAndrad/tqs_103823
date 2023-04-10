package pt.ua.deti.tqs.airquality.model;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

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
    void testTTL() throws InterruptedException {
        storage.add("key", "value");
        Thread.sleep(5);
        assertNull(storage.get("key"));
    }
}