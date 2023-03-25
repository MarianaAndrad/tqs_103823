package pt.ua.deti.tqs.carsservice.service;

import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.carsservice.data.CarRepository;
import pt.ua.deti.tqs.carsservice.model.Car;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    final CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car car){
        return carRepository.save(car);
    }

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long carId) {
        return Optional.ofNullable(carRepository.findByCarId(carId));
    }

    public boolean existsByCarId(Long carId) {
        return carRepository.existsByCarId(carId);
    }

    public List<Car> getCarByModel(String model) {
        return carRepository.findByModel(model);
    }

    public List<Car> getCarByMaker(String maker) {
        return carRepository.findByMaker(maker);
    }

    public String deleteCar(Long carId) {
        if (carRepository.deleteByCarId(carId) != null){
            return "Car deleted successfully";
        }
        return "Car not found";
    }

}
