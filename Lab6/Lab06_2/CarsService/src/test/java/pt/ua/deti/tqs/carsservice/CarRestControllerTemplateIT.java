package pt.ua.deti.tqs.carsservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import pt.ua.deti.tqs.carsservice.data.CarRepository;
import pt.ua.deti.tqs.carsservice.model.Car;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureTestDatabase // this annotation is not needed because we are using the same database
@TestPropertySource(locations = "/application-integrationtest.properties")
class CarRestControllerTemplateIT {
    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;


    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car audi = new Car("Audi", "A4");
        ResponseEntity<Car> response = restTemplate.postForEntity("/api/cars", audi, Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getModel).containsOnly(audi.getModel());
        assertThat(found).extracting(Car::getMaker).containsOnly(audi.getMaker());
    }

    @Test
    void whenValidInput_thenGetCar_thenStatus200() {
        createTestCar("Audi", "A4");
        createTestCar("Nissan", "Qashqai");
        createTestCar("BMW", "X5");

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("Audi", "Nissan", "BMW");

    }

    @Test
    void whenValidInput_thenGetDetailsCar_thenStatus200(){
        Car car = new Car("Audi", "A3");
        carRepository.saveAndFlush(car);

        ResponseEntity<Car> response = restTemplate.exchange("/api/carId/" + car.getCarId(), HttpMethod.GET, null, new ParameterizedTypeReference<Car>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody().getModel()).isEqualTo("Audi");
        assertThat(response.getBody().getMaker()).isEqualTo("A3");
    }

    @Test
    void whenValidInput_thenGetCarByModel_thenStatus200(){
        Car car = new Car("Audi", "A3");
        carRepository.saveAndFlush(car);
        Car car2 = new Car("Audi", "A4");
        carRepository.saveAndFlush(car2);

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/carsModel/" + car.getModel() , HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("Audi", "Audi");
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("A3", "A4");

    }

    @Test
    void whenValidInput_thenGetCarByMaker_thenStatus200(){
        Car car = new Car("Audi", "A3");
        carRepository.saveAndFlush(car);
        Car car2 = new Car("astro", "A3");
        carRepository.saveAndFlush(car2);

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/carsMaker/" + car.getMaker() , HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("Audi", "astro");
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("A3", "A3");
    }

    @Test
    void whenValidInput_thenExistCarValidId_thenStatus200(){
        Car car = new Car(1L,"Audi", "A3");
        carRepository.saveAndFlush(car);

        ResponseEntity<Boolean> response = restTemplate
                .exchange("/api/Bool/carID/" + car.getCarId() , HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isTrue();
    }

    @Test
    void whenValidInput_thenExistCarInValidId_thenStatus200(){
        Car car = new Car(1L,"Audi", "A3");
        carRepository.saveAndFlush(car);

        ResponseEntity<Boolean> response = restTemplate
                .exchange("/api/Bool/carID/2" , HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isFalse();
    }

    private void createTestCar(String model, String market) {
        Car newcar = new Car(model, market);
        carRepository.saveAndFlush(newcar);
    }

    @Test
    void whenValidInput_thenDeleteCar() {
        Car car = new Car(11L,"Audi", "A4");
        carRepository.saveAndFlush(car);
        ResponseEntity<String> response = restTemplate
                .exchange("/api/car/"+ car.getCarId() , HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody()).isEqualTo("Car deleted successfully");
    }


}
