package sdmExam;

public class OccupiedPositionException extends Exception {

    public OccupiedPositionException(Position position){
        super("Position %s is already occupied.", position);
    }
}

