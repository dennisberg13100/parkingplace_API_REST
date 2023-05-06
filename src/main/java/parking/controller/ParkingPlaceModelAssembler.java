package parking.controller;
// No auto import for this shit 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import parking.entity.ParkingPlace;

@Component
public class ParkingPlaceModelAssembler implements RepresentationModelAssembler<ParkingPlace, EntityModel<ParkingPlace>>{

    @Override
    public EntityModel<ParkingPlace> toModel(ParkingPlace parkingPlace) {
        return EntityModel.of(parkingPlace,
            linkTo(methodOn(ParkingPlaceController.class).one(parkingPlace.getId())).withSelfRel(),
            linkTo(methodOn(ParkingPlaceController.class).all()).withRel("parking-place")
        );    
    }   
}
