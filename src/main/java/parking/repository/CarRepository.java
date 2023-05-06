package parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import parking.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long>{
  
}
