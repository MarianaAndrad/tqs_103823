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
        HttpStatus status = HttpStatus.CREATED;
        Car savedCar = carManagerService.save(car);
        return new ResponseEntity<>(car, status);
    }

    @GetMapping(path = "/cars",  produces = "application/json")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) throws ResourceNotFoundException {
        Car car = carManagerService.getCarDetails(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for id: " + carId));
        return ResponseEntity.ok().body(car);
    }

}
