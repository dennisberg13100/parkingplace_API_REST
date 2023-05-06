package parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import parking.entity.ParkingPlace;

public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace, Long> {   
}
