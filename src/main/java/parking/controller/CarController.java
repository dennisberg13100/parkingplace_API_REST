package parking.controller;

import java.util.List;
import java.util.stream.Collectors;
// No auto import for this shit 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import parking.entity.Car;
import parking.exception.CarNotFoundException;
import parking.repository.CarRepository;

@RestController
public class CarController {
    
    private final CarRepository repository; 
    private final CarModelAssembler assembler;


    CarController(CarRepository repository,CarModelAssembler assembler ) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/cars")
    CollectionModel<EntityModel<Car>> all() {

        List<EntityModel<Car>> cars = repository.findAll()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(cars,
            linkTo(methodOn(CarController.class).all()).withSelfRel());
    }

    @PostMapping("/cars")
    ResponseEntity<?> newCar(@RequestBody Car car) {
        
        EntityModel<Car> entityModel = assembler.toModel(repository.save(car));
        
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/cars/{id}")
    EntityModel<Car> one(@PathVariable Long id) {
        Car car = repository.findById(id)
            .orElseThrow(() -> new CarNotFoundException(id));
        
        return assembler.toModel(car);
    }

    @PutMapping("/cars/{id}")
    ResponseEntity<?> updateCar(@RequestBody Car newCar, @PathVariable Long id) {
        
        Car updatedCar = repository.findById(id)
            .map(car -> {
                car.setModel(newCar.getModel());
                car.setPlate(newCar.getPlate());
                car.setSize(newCar.getSize());
                return repository.save(car);
            })
            .orElseGet(() -> {
                newCar.setId(id);
                return repository.save(newCar);
            });

        EntityModel<Car> entityModel = assembler.toModel(updatedCar);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/cars/{id}") 
    ResponseEntity<?> deleteCar(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
