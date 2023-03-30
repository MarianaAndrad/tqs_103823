package pt.ua.deti.tqs.CarsService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.CarsService.Controllers.CarRestController;
import pt.ua.deti.tqs.CarsService.Model.Car;
import pt.ua.deti.tqs.CarsService.Service.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CarRestController.class)
class CarAssuredControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void whenPostCar_thenCreateCar(){
        Car car = new Car(1L,"Nissan", "Micra");
        Mockito.when(service.save(car)).thenReturn(car);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(car)
                .when()
                    .request("POST", "/api/cars")
                .then()
                    .statusCode(201)
                    .body("model", equalTo("Nissan"))
                    .body("maker", equalTo("Micra"));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetAllCars_thenReturnJsonArray() {
        Car car2 = new Car(2L,"Audi", "A3");
        Car car3 = new Car(3L,"BMW", "X5");

        List<Car> allCars = Arrays.asList(car2,car3);

        when(service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                    .request("GET", "/api/cars")
                .then()
                    .statusCode(200)
                .body("$.size()", equalTo(2))
                .body("[0].model", equalTo(car2.getModel()))
                .body("[1].model", equalTo(car3.getModel()))
                .body("[0].maker", equalTo(car2.getMaker()))
                .body("[1].maker", equalTo(car3.getMaker()));

        verify(service, times(1)).getAllCars();
    }


    @Test
    void givenCar_whenGetCarById_thenReturnJson() {
        Long id = 1L;
        Car car = new Car ( id,"Nissan", "Nissan3");


        Mockito.when(service.getCarDetails(1L)).thenReturn(Optional.of(car));

        RestAssuredMockMvc.given()
                    .contentType("application/json")
                .when()
                    .request("GET", "/api/carId/"+ id)
                .then()
                .statusCode(200)
                .body("model", equalTo("Nissan"))
                .body("maker", equalTo("Nissan3"));

        verify(service, times(1)).getCarDetails(Mockito.anyLong());
    }

    @Test
    void givenCar_whenGetCarsByModel_thenReturnJson() {
        Car car2 = new Car ("Audi", "A1");
        Car car4 = new Car ("Audi", "A2");

        List<Car> allCars = Arrays.asList(car2,car4);

        when(service.getCarByModel("Audi")).thenReturn(allCars);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                    .request("GET", "/api/carsModel/Audi")
                .then()
                    .statusCode(200)
                .body("$.size()", equalTo(2))
                .body("[0].model", equalTo(car2.getModel()))
                .body("[1].model", equalTo(car4.getModel()))
                .body("[0].maker", equalTo(car2.getMaker()))
                .body("[1].maker", equalTo(car4.getMaker()));

        verify(service, times(1)).getCarByModel("Audi");
    }

    @Test
    void givenCar_whenGetCarsByMaket_thenReturnJson() {
        Car car2 = new Car ("Audi", "A1");
        Car car4 = new Car ("Astro", "A1");

        List<Car> allCars = Arrays.asList(car2,car4);

        when(service.getCarByMaker("A1")).thenReturn(allCars);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .request("GET", "/api/carsMaker/A1")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(2))
                .body("[0].model", equalTo(car2.getModel()))
                .body("[1].model", equalTo(car4.getModel()))
                .body("[0].maker", equalTo(car2.getMaker()))
                .body("[1].maker", equalTo(car4.getMaker()));

        verify(service, times(1)).getCarByMaker("A1");
    }

    @Test
    void givenExistingCar_whenCheckIfCarExistsById_thenReturnTrue() throws Exception {
            Long carId = 1L;
            Car car = new Car( carId, "Audi", "A1");

            when(service.existsByCarId(carId)).thenReturn(true);

            RestAssuredMockMvc.given()
                    .contentType("application/json")
                    .when()
                    .request("GET", "/api/Bool/carID/" + carId)
                    .then()
                    .statusCode(200)
                    .body(equalTo("true"));

            verify(service, times(1)).existsByCarId(carId);
    }

    @Test
    void givenCar_whenGetExistingCarByInvalidID_thenReturnJson() throws Exception {
        when(service.existsByCarId(Mockito.anyLong())).thenReturn(false);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .request("GET", "/api/Bool/carID/13")
                .then()
                .statusCode(200)
                .body(equalTo("false"));

        verify(service, times(1)).existsByCarId(Mockito.anyLong());
    }


    @Test
    void whenDeleteCar_thenDeleteCar() throws Exception{
        Car car = new Car (11L,"Nissan", "Nissan3");

        when(service.deleteCar(Mockito.anyLong())).thenReturn("Car deleted successfully");

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .request("DELETE", "/api/car/11")
                .then()
                .statusCode(202)
                .body(equalTo("Car deleted successfully"));

        verify(service, times(1)).deleteCar(Mockito.anyLong());
    }

    @Test
    void whenDeleteCarInvalidID_thenDeleteCar() throws Exception{
        Car car = new Car (11L,"Nissan", "Nissan3");
        when(service.deleteCar(Mockito.anyLong())).thenReturn("Car not found");

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .when()
                .request("DELETE", "/api/car/11")
                .then()
                .statusCode(404)
                .body(equalTo("Car not found"));

        verify(service, times(1)).deleteCar(Mockito.anyLong());
    }
}
