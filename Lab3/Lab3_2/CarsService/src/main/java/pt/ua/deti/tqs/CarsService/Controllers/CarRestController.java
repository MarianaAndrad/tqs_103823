package pt.ua.deti.tqs.CarsService.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.tqs.CarsService.Service.CarManagerService;
import pt.ua.deti.tqs.CarsService.Model.Car;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarRestController {

    private final CarManagerService carManagerService;

    public CarRestController(CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }


    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return null;
    }

    @GetMapping(path = "/cars",  produces = "application/json")
    public List<Car> getAllCars() {
        return null;
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId){
        return null;
    }
}
