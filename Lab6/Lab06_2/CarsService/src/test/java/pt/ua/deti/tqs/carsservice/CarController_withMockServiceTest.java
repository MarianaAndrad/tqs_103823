package pt.ua.deti.tqs.carsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.carsservice.controllers.CarRestController;
import pt.ua.deti.tqs.carsservice.model.Car;
import pt.ua.deti.tqs.carsservice.service.CarManagerService;
import pt.ua.deti.tqs.carsservice.controllers.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CarRestController.class)
class CarController_withMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception{
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception{
        Car car = new Car ("Nissan", "Nissan3");

        when(service.save(Mockito.any())).thenReturn(car);

        mvc.perform(
                        post("/api/cars").
                                contentType(MediaType.APPLICATION_JSON).
                                content(JsonUtils.toJson(car))).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.model", is("Nissan"))).
                andExpect(jsonPath("$.maker", is("Nissan3")));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetAllCars_thenReturnJsonArray() throws Exception{
        Car car = new Car ("Nissan", "Nissan3");
        Car car2 = new Car ("Audi", "A1");
        Car car3 = new Car ("BMW", "BMWX7");


        List<Car> allCars = Arrays.asList(car, car2,car3);

        when(service.getAllCars()).thenReturn(allCars);

        mvc.perform(
                        get("/api/cars").
                                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(3))).
                andExpect(jsonPath("$[0].model", is("Nissan"))).
                andExpect(jsonPath("$[0].maker", is("Nissan3"))).
                andExpect(jsonPath("$[1].model", is("Audi"))).
                andExpect(jsonPath("$[1].maker", is("A1"))).
                andExpect(jsonPath("$[2].model", is("BMW"))).
                andExpect(jsonPath("$[2].maker", is("BMWX7")));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void givenCar_whenGetCarById_thenReturnJson() throws Exception{
        Car car = new Car ("Nissan", "Nissan3");
        car.setCarId(1L);

        when(service.getCarDetails(1L)).thenReturn(java.util.Optional.of(car));

        mvc.perform(
                        get("/api/carId/1").
                                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.carId", is(1))).
                andExpect(jsonPath("$.model", is("Nissan"))).
                andExpect(jsonPath("$.maker", is("Nissan3")));

        verify(service, times(1)).getCarDetails(Mockito.anyLong());
    }
    @Test
    void givenCar_whenGetCarsByModel_thenReturnJson() throws Exception{
        Car car2 = new Car ("Audi", "A1");
        Car car4 = new Car ("Audi", "A2");

        List<Car> allCars = Arrays.asList(car2,car4);

        when(service.getCarByModel("Audi")).thenReturn(allCars);

        mvc.perform(
                        get("/api/carsModel/Audi").
                                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$[0].model", is("Audi"))).
                andExpect(jsonPath("$[0].maker", is("A1"))).
                andExpect(jsonPath("$[1].model", is("Audi"))).
                andExpect(jsonPath("$[1].maker", is("A2")));

        verify(service, times(1)).getCarByModel("Audi");
    }

    @Test
    void givenCar_whenGetCarById_thenReturnNotFound() throws Exception {
        when(service.getCarDetails(100L)).thenReturn(java.util.Optional.empty());

        mvc.perform(get("/api/carId/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getCarDetails(Mockito.anyLong());
    }

    @Test
    void givenCar_whenGetCarsByMaket_thenReturnJson() throws Exception{
        Car car2 = new Car ("Audi", "A1");
        Car car4 = new Car ("Astro", "A1");

        List<Car> allCars = Arrays.asList(car2,car4);

        when(service.getCarByMaker("A1")).thenReturn(allCars);

        mvc.perform(
                        get("/api/carsMaker/A1").
                                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$[0].model", is("Audi"))).
                andExpect(jsonPath("$[0].maker", is("A1"))).
                andExpect(jsonPath("$[1].model", is("Astro"))).
                andExpect(jsonPath("$[1].maker", is("A1")));

        verify(service, times(1)).getCarByMaker("A1");
    }

    @Test
    void givenCar_whenGetExistingCarByValidID_thenReturnJson() throws Exception {
        Car car = new Car ("Audi", "A1");
        car.setCarId(11L);


        when(service.existsByCarId(Mockito.anyLong())).thenReturn(true);

        mvc.perform(
                        get("/api/Bool/carID/11").
                                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", is(true)));

        verify(service, times(1)).existsByCarId(Mockito.anyLong());
    }

    @Test
    void givenCar_whenGetExistingCarByInvalidID_thenReturnJson() throws Exception {
        when(service.existsByCarId(Mockito.anyLong())).thenReturn(false);

        mvc.perform(
                        get("/api/Bool/carID/13").
                                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", is(false)));

        verify(service, times(1)).existsByCarId(Mockito.anyLong());
    }

    @Test
    void whenDeleteCar_thenDeleteCar() throws Exception{
        Car car = new Car (11L,"Nissan", "Nissan3");

        when(service.deleteCar(Mockito.anyLong())).thenReturn("Car deleted successfully");

        mvc.perform(
                        delete("/api/car/11").
                                contentType(MediaType.APPLICATION_JSON).
                                content(JsonUtils.toJson(car))).
                andExpect(status().isAccepted()).
                andExpect(jsonPath("$", is("Car deleted successfully")));

        verify(service, times(1)).deleteCar(Mockito.any());
    }

    @Test
    void whenDeleteCarInvalidID_thenDeleteCar() throws Exception{
        Car car = new Car (11L,"Nissan", "Nissan3");
        when(service.deleteCar(Mockito.anyLong())).thenReturn("Car not found");

        mvc.perform(
                        delete("/api/car/15").
                                contentType(MediaType.APPLICATION_JSON).
                                content(JsonUtils.toJson(car))).
                andExpect(status().isNotFound()).
                andExpect(jsonPath("$", is("Car not found")));

        verify(service, times(1)).deleteCar(Mockito.any());
    }



}

