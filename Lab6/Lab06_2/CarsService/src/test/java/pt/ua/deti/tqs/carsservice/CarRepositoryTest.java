package pt.ua.deti.tqs.carsservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.ua.deti.tqs.carsservice.data.CarRepository;
import pt.ua.deti.tqs.carsservice.model.Car;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindCarExistingId_thenReturnCar(){
        Car car = new Car("test", "test");
        entityManager.persistAndFlush(car);

        Car fromDb = carRepository.findByCarId(car.getCarId());
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getModel()).isEqualTo(car.getModel());
        assertThat(fromDb.getMaker()).isEqualTo(car.getMaker());
    }

    @Test
    void whenFindCarNonExistingId_thenReturnNull(){
        Car fromDb = carRepository.findByCarId(-1L);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car audi = new Car("Audi", "A4");
        Car nissan = new Car("Nissan", "Qashqai");
        Car bmw = new Car("BMW", "X5");


        entityManager.persist(audi);
        entityManager.persist(nissan);
        entityManager.persist(bmw);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getModel).containsOnly(audi.getModel(), nissan.getModel(), bmw.getModel());
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(audi.getMaker(), nissan.getMaker(), bmw.getMaker());
    }

}
