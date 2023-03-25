package pt.ua.deti.tqs.carsservice.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    private String model;

    private String maker;

    public Car() {
    }

    public Car(String model, String maker) {
        this.model = model;
        this.maker = maker;
    }

    public Car(Long carId, String model, String maker) {
        this.carId = carId;
        this.model = model;
        this.maker = maker;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    @Override
    public String toString() {
        return "Car{" + "carId=" + carId + ", model=" + model + ", maker=" + maker + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Car car = (Car) obj;
        return carId.equals(car.carId) && Objects.equals(maker, car.maker) && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, maker, model);
    }
}
