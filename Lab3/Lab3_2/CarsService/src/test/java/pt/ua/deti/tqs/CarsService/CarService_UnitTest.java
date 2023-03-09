package pt.ua.deti.tqs.CarsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.tqs.CarsService.Data.CarRepository;
import pt.ua.deti.tqs.CarsService.Model.Car;
import pt.ua.deti.tqs.CarsService.Service.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class CarService_UnitTest {

    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carService;

    @BeforeEach
    public void setUp() throws Exception{
        Car audi = new Car("A1", "audi");
        audi.setCarId(11L);

        Car bmw = new Car("BMW XM", "BMW");
        bmw.setCarId(12L);

        Car nissan = new Car("Nissan", "Nissan");
        nissan.setCarId(13L);

        List<Car> allCars = Arrays.asList(audi, bmw, nissan);

        Mockito.when(carRepository.save(audi)).thenReturn(audi);
        Mockito.when(carRepository.save(bmw)).thenReturn(bmw);
        Mockito.when(carRepository.save(nissan)).thenReturn(nissan);

        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findByCarId(audi.getCarId())).thenReturn(audi);
        Mockito.when(carRepository.findByCarId(bmw.getCarId())).thenReturn(bmw);
        Mockito.when(carRepository.findByCarId(nissan.getCarId())).thenReturn(nissan);

        Mockito.when(carRepository.existsByCarId(audi.getCarId())).thenReturn(true);
        Mockito.when(carRepository.existsByCarId(bmw.getCarId())).thenReturn(true);
        Mockito.when(carRepository.existsByCarId(nissan.getCarId())).thenReturn(true);
        Mockito.when(carRepository.existsByCarId(-10L)).thenReturn(false);

        Mockito.when(carRepository.findByModel(audi.getModel())).thenReturn(List.of(audi));
        Mockito.when(carRepository.findByModel(bmw.getModel())).thenReturn(List.of(bmw));
        Mockito.when(carRepository.findByModel(nissan.getModel())).thenReturn(List.of(nissan));

        Mockito.when(carRepository.findByMaker(audi.getMaker())).thenReturn(List.of(audi));
        Mockito.when(carRepository.findByMaker(bmw.getMaker())).thenReturn(List.of(bmw));
        Mockito.when(carRepository.findByMaker(nissan.getMaker())).thenReturn(List.of(nissan));

        Mockito.when(carRepository.findByCarId(-10L)).thenReturn(null);
    }

    @Test
    public void whenValidCarID_thenCarShouldBeFound() {
        Long carId = 11L;
        Optional<Car> found = carService.getCarDetails(carId);

        assertThat(found.isPresent()).isEqualTo(true);
        assertThat(found.get().getCarId()).isEqualTo(carId);

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(carId);
    }

    @Test
    public void whenInValidCarID_thenCarShouldNotBeFound() {
        Long carId = -10L;
        Optional<Car> found = carService.getCarDetails(carId);

        assertThat(found.isPresent()).isEqualTo(false);
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(carId);

    }

    @Test
    public void given3Cars_whenGetAll_thenReturn3Records(){
        Car audi = new Car(11L,"A1", "audi");
        Car bmw = new Car(12L,"BMW XM", "BMW");
        Car nissan = new Car(13L,"Nissan", "Nissan");

        List<Car> allCars = carService.getAllCars();

        verifyFindAllCarsIsCalledOnce();
        assertThat(allCars).hasSize(3).extracting(Car::getCarId).contains(nissan.getCarId(), bmw.getCarId(), audi.getCarId());
        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(nissan.getModel(), bmw.getModel(), audi.getModel());
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).contains(nissan.getMaker(), bmw.getMaker(), audi.getMaker());
    }

    private void verifyFindAllCarsIsCalledOnce(){
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }

    @Test
    public void whenGetCarDetails_thenCarShouldBeFound(){
        Optional<Car> carFound = carService.getCarDetails(11L);

        assertThat(carFound.isPresent()).isEqualTo(true);

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(11L);

        assertThat(carFound).isNotNull();
    }


    @Test
    public void whenGetCarDetails_thenCarShouldNotBeFound() {
        Optional<Car> carFound = carService.getCarDetails(-10L);

        assertThat(carFound.isPresent()).isEqualTo(false);

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(-10L);

    }

    @Test
    public void whenSaveCar_thenCarShouldBeSaved(){
        Car audi = new Car(11L,"A1", "audi");
        Car savedCar = carService.save(audi);

        assertThat(savedCar).isNotNull().isEqualTo(audi);

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).save(audi);

        assertThat(savedCar.getCarId()).isEqualTo(audi.getCarId());
        assertThat(savedCar.getModel()).isEqualTo(audi.getModel());
        assertThat(savedCar.getMaker()).isEqualTo(audi.getMaker());
    }

    @Test
    public void whenExistsCar_thenCarShouldBeFound(){
        Long carId = 11L;
        boolean found = carService.existsByCarId(carId);

        assertThat(found).isEqualTo(true);

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).existsByCarId(carId);
    }

    @Test
    public void whenExistsCar_thenCarShouldNotBeFound(){
        Long carId = -10L;
        boolean found = carService.existsByCarId(carId);

        assertThat(found).isEqualTo(false);

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).existsByCarId(carId);
    }

    @Test
    public void whenDeleteCar_thenCarShouldBeDeleted(){
        Long carId = 11L;
        String response = carService.deleteCar(carId);

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).deleteByCarId(carId);

        assertThat(response).isEqualTo("Car deleted successfully");
    }

    @Test
    public void whenDeleteCar_thenCarShouldNotBeDeleted(){
        Long carId = -10L;
        String response = carService.deleteCar(carId);

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).deleteByCarId(carId);

        assertThat(response).isEqualTo("Car not found");
    }

    @Test
public void whenUpdateCar_thenCarShouldBeUpdated(){
        Car audi = new Car(11L,"A1", "audi");
        Car updatedCar = carService.updateCar(audi);

        assertThat(updatedCar).isNotNull().isEqualTo(audi);

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).save(audi);

        assertThat(updatedCar.getCarId()).isEqualTo(audi.getCarId());
        assertThat(updatedCar.getModel()).isEqualTo(audi.getModel());
        assertThat(updatedCar.getMaker()).isEqualTo(audi.getMaker());
    }

    @Test
    public void whenUpdateCar_thenCarShouldNotBeUpdated(){
        Car audi = new Car(-10L,"A1", "audi");
        Car updatedCar = carService.updateCar(audi);

        assertThat(updatedCar).isNotNull().isEqualTo(audi);

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).save(audi);

        assertThat(updatedCar.getCarId()).isEqualTo(audi.getCarId());
        assertThat(updatedCar.getModel()).isEqualTo(audi.getModel());
        assertThat(updatedCar.getMaker()).isEqualTo(audi.getMaker());
    }

    @Test
    public void whenGetCarByModel_thenCarShouldBeFound(){
        String model = "A1";
        List<Car> carsFound = carService.getCarByModel(model);

        assertThat(carsFound).isNotNull();

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByModel(model);

        assertThat(carsFound).hasSize(1).extracting(Car::getCarId).contains(11L);
        assertThat(carsFound).hasSize(1).extracting(Car::getModel).contains("A1");
        assertThat(carsFound).hasSize(1).extracting(Car::getMaker).contains("audi");
    }

    @Test
    public void whenGetCarByModel_thenCarShouldNotBeFound(){
        String model = "A2";
        List<Car> carsFound = carService.getCarByModel(model);

        assertThat(carsFound).isNotNull();

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByModel(model);

        assertThat(carsFound).hasSize(0);
    }

    @Test
    public void whenGetCarByMaker_thenCarShouldBeFound(){
        String maker = "audi";
        List<Car> carsFound = carService.getCarByMaker(maker);

        assertThat(carsFound).isNotNull();

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByMaker(maker);

        assertThat(carsFound).hasSize(1).extracting(Car::getCarId).contains(11L);
        assertThat(carsFound).hasSize(1).extracting(Car::getModel).contains("A1");
        assertThat(carsFound).hasSize(1).extracting(Car::getMaker).contains("audi");
    }

    @Test
    public void whenGetCarByMaker_thenCarShouldNotBeFound(){
        String maker = "audi2";
        List<Car> carsFound = carService.getCarByMaker(maker);

        assertThat(carsFound).isNotNull();

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByMaker(maker);

        assertThat(carsFound).hasSize(0);
    }
}
