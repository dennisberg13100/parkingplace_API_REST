package parking.exception;

public class ParkingPlaceNotFoundException extends RuntimeException{
    public ParkingPlaceNotFoundException(Long id) {
        super("Could not find parking place " + id);
    }
}
