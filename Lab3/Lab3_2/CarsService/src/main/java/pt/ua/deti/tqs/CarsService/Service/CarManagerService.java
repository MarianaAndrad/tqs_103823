package pt.ua.deti.tqs.CarsService.Service;

import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.CarsService.Data.CarRepository;
import pt.ua.deti.tqs.CarsService.Model.Car;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    final CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Object any) {
        return null;
    }

    public List<Car> getAllCars(){
        return null;
    }

    public Optional<Car> getCarDetails(Long carId){
        return null;
    }


}