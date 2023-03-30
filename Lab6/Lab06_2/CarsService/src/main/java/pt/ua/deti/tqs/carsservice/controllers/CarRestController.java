package pt.ua.deti.tqs.carsservice.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.tqs.carsservice.service.CarManagerService;
import pt.ua.deti.tqs.carsservice.model.Car;

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
        return new ResponseEntity<>(savedCar, status);
    }

    @GetMapping(path = "/cars",  produces = "application/json")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/carId/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) throws ResourceNotFoundException {
        Car car = carManagerService.getCarDetails(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for id: " + carId));
        System.out.println(car);
        return ResponseEntity.ok().body(car);
    }

    @GetMapping(path = "/carsModel/{model}", produces = "application/json")
    public List<Car> getCarByModel(@PathVariable(value = "model") String model)  {
        return carManagerService.getCarByModel(model);
    }

    @GetMapping(path="/carsMaker/{maker}", produces = "application/json")
    public List<Car> getCarByMaker(@PathVariable(value = "maker") String maker)  {
        return carManagerService.getCarByMaker(maker);
    }

    @GetMapping("/Bool/carID/{id}")
    public boolean existCarById(@PathVariable(value = "id") Long carId) {
        return carManagerService.existsByCarId(carId);
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable(value = "id") Long carId) {
        HttpStatus status = HttpStatus.ACCEPTED;
        if (carManagerService.deleteCar(carId).equals("Car deleted successfully")) {
            return new ResponseEntity<>("Car deleted successfully", status);
        } else {
            return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
        }

    }

}
