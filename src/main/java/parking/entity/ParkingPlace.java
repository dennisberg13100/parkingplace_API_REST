package parking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class ParkingPlace {
    
    private @Id @GeneratedValue Long id;
    private String name; 
    private String cnpj;


    public ParkingPlace() {
    }

    public ParkingPlace(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ParkingPlace)) {
            return false;
        }
        ParkingPlace parkingPlace = (ParkingPlace) o;
        return Objects.equals(id, parkingPlace.id) && Objects.equals(name, parkingPlace.name) && Objects.equals(cnpj, parkingPlace.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cnpj);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            "}";
    }
    
}
