package pt.ua.deti.tqs.CarsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.CarsService.Model.Car;
import pt.ua.deti.tqs.CarsService.Service.CarManagerService;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*

public class CarAssuredControllerTest {

    private Car car;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

}
