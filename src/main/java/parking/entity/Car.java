package parking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import parking.constants.Size;

import java.util.Objects;

@Entity
public class Car {
    
    private @Id @GeneratedValue Long id; 
    private String plate; 
    private String model; 
    private Size size; 


    public Car() {
    }

    public Car(String plate, String model, Size size) {
        this.plate = plate;
        this.model = model;
        this.size = size;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlate() {
        return this.plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Car)) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(plate, car.plate) && Objects.equals(model, car.model) && Objects.equals(size, car.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plate, model, size);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", plate='" + getPlate() + "'" +
            ", model='" + getModel() + "'" +
            ", size='" + getSize() + "'" +
            "}";
    }
    
    
}
