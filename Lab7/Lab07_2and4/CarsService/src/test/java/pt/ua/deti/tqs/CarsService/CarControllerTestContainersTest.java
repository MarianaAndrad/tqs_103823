package pt.ua.deti.tqs.CarsService;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pt.ua.deti.tqs.CarsService.Data.CarRepository;
import pt.ua.deti.tqs.CarsService.Model.Car;

import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")// note the TestPropertySource to enforce the ddl generation!
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarControllerTestContainersTest {

    // instantiate the container passing selected config
    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
            .withUsername("demo")
            .withPassword("demopass")
            .withDatabaseName("test_db");
             /// .withInitScript( PATH )
    @LocalServerPort
    int localPortForTestServer;
    Car car1, car2;

    @Autowired
    private CarRepository repository;

    // read configuration from running db
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);

    }

    @BeforeEach
    public void setUpTestCars() throws Exception {
        car1 = repository.save(new Car("kia", "stinger"));
        car2 = repository.save(new Car("tesla", "model x"));
    }

    @AfterEach
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    @Order(1)
    void whenPostCar_thenCreateCar() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(localPortForTestServer)
                .pathSegment("api", "cars")
                .build()
                .toUriString();

        Car car = new Car("ford", "mustang");
        RestAssured.given()
                .port(localPortForTestServer)
                .contentType("application/json")
                .body(car)
                .when()
                .post("/api/cars")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("model", equalTo("ford"))
                .body("maker", equalTo("mustang"));
    }

    @Test
    @Order(2)
    public void givenManyCars_whenGetAllCars_thenReturnJsonArray() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(localPortForTestServer)
                .pathSegment("api", "cars")
                .build()
                .toUriString();

        RestAssured.given()
                .port(localPortForTestServer)
                .when()
                .get("/api/cars")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(2))
                .body("[0].model", equalTo("kia"))
                .body("[0].maker", equalTo("stinger"))
                .body("[1].model", equalTo("tesla"))
                .body("[1].maker", equalTo("model x"));
    }

    @Test
    @Order(3)
    public void givenCar_whenGetCarById_thenReturnJson() {

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(localPortForTestServer)
                .pathSegment("api", "carId", String.valueOf(car1.getCarId()))
                .build()
                .toUriString();

        RestAssured.given()
                .port(localPortForTestServer)
                .when()
                .get(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("model", equalTo("kia"))
                .body("maker", equalTo("stinger"));
    }

    @Test
    @Order(4)
    void givenCar_whenGetCarsByModel_thenReturnJson(){
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(localPortForTestServer)
                .pathSegment("api", "carsModel", car1.getModel())
                .build()
                .toUriString();

        RestAssured.given()
                .port(localPortForTestServer)
                .when()
                .get(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(1))
                .body("[0].model", equalTo("kia"))
                .body("[0].maker", equalTo("stinger"));
    }

    @Test
    @Order(5)
    void givenCar_whenGetCarsByMaket_thenReturnJson(){
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(localPortForTestServer)
                .pathSegment("api", "carsMaker", car1.getMaker())
                .build()
                .toUriString();

        RestAssured.given()
                .port(localPortForTestServer)
                .when()
                .get(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(1))
                .body("[0].model", equalTo("kia"))
                .body("[0].maker", equalTo("stinger"));
    }

    @Test
    @Order(6)
    void givenExistingCar_whenCheckIfCarExistsById_thenReturnTrue(){
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(localPortForTestServer)
                .pathSegment("api", "Bool","carID", String.valueOf(car1.getCarId()))
                .build()
                .toUriString();

        RestAssured.given()
                .port(localPortForTestServer)
                .when()
                .get(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body( equalTo("true"));
    }

    @Test
    @Order(7)
    void givenNonExistingCar_whenCheckIfCarExistsById_thenReturnFalse(){
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(localPortForTestServer)
                .pathSegment("api", "Bool","carID", String.valueOf(100))
                .build()
                .toUriString();

        RestAssured.given()
                .port(localPortForTestServer)
                .when()
                .get(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body( equalTo("false"));
    }

    @Test
    @Order(8)
    void whenDeleteCar_thenDeleteCar() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(localPortForTestServer)
                .pathSegment("api", "car", String.valueOf(car1.getCarId()))
                .build()
                .toUriString();

        RestAssured.given()
                .port(localPortForTestServer)
                .contentType("application/json")
                .when()
                .delete(endpoint)
                .then()
                .statusCode(202)
                .body(equalTo("Car deleted successfully"));
    }

    @Test
    @Order(9)
    void whenDeleteCarInvalidID_thenDeleteCar(){
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(localPortForTestServer)
                .pathSegment("api", "car", String.valueOf(10000L))
                .build()
                .toUriString();

        RestAssured.given()
                .port(localPortForTestServer)
                .contentType("application/json")
                .when()
                .delete(endpoint)
                .then()
                .statusCode(404)
                .body( equalTo("Car not found"));
    }


}
