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

import parking.entity.ParkingPlace;
import parking.exception.ParkingPlaceNotFoundException;
import parking.repository.ParkingPlaceRepository;

@RestController
public class ParkingPlaceController {
    
    private final ParkingPlaceRepository repository;
    private final ParkingPlaceModelAssembler assembler;

    ParkingPlaceController(ParkingPlaceRepository repository, ParkingPlaceModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/parking-places")
    CollectionModel<EntityModel<ParkingPlace>> all() {
        List<EntityModel<ParkingPlace>> parkingPlaces = repository.findAll()
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(parkingPlaces,
            linkTo(methodOn(ParkingPlaceController.class).all()).withSelfRel()
        );   
    }

    @PostMapping("/parking-places")
    ResponseEntity<?> newParkingPlace(@RequestBody ParkingPlace newParkingPlace) {
        EntityModel<ParkingPlace> entityModel = assembler.toModel(repository.save(newParkingPlace));
        
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @GetMapping("/parking-places/{id}")
    EntityModel<ParkingPlace> one(@PathVariable Long id) {
        
        ParkingPlace parkingPlace = repository.findById(id)
            .orElseThrow(() -> new ParkingPlaceNotFoundException(id));
        
        return assembler.toModel(parkingPlace);
    }

    @PutMapping("/parking-places/{id}")
    ResponseEntity<?> replaceParkingPlace(@RequestBody ParkingPlace newParkingPlace, @PathVariable Long id) {
        
        ParkingPlace updatedParkingPlace = repository.findById(id)
            .map(parkingPlace -> {
                parkingPlace.setName(newParkingPlace.getName());
                parkingPlace.setCnpj(newParkingPlace.getCnpj());
                return repository.save(parkingPlace);
            })
            .orElseGet(() -> {
                newParkingPlace.setId(id);
                return repository.save(newParkingPlace);
            });
        
        EntityModel<ParkingPlace> entityModel = assembler.toModel(updatedParkingPlace);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/parking-places/{id}")
    ResponseEntity<?> deleteParkingPlace(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
