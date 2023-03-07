package pt.ua.deti.tqs.CarsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.CarsService.Controllers.CarRestController;
import pt.ua.deti.tqs.CarsService.Model.Car;
import pt.ua.deti.tqs.CarsService.Service.CarManagerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CarRestController.class)
public class CarController_withMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception{
    }

    @Test
    void whwnPostCar_thenCreateCar() throws Exception{
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

        when(service.getCarDetails(Mockito.anyLong())).thenReturn(java.util.Optional.of(car));

        mvc.perform(
                        get("/api/cars/1").
                                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.model", is("Nissan"))).
                andExpect(jsonPath("$.maker", is("Nissan3")));

        verify(service, times(1)).getCarDetails(Mockito.anyLong());
    }

}
