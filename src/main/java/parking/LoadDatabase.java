package parking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import parking.constants.Size;
import parking.entity.Car;
import parking.entity.ParkingPlace;
import parking.repository.CarRepository;
import parking.repository.ParkingPlaceRepository;

@Configuration
class LoadDatabase {
    
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CarRepository carroRepository, ParkingPlaceRepository parkingPlaceRepository) {
        return args -> {
            log.info("Preloading: " + carroRepository.save(new Car("ABC1234", "Sandero", Size.MIDDLE)));
            log.info("Preloading: " + carroRepository.save(new Car("BCD1234", "Uno", Size.SMALL)));
            log.info("Preloading: " + parkingPlaceRepository.save(new ParkingPlace("Estacionaqui", "999999999")));
        };
    }
}
