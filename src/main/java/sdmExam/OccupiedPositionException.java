package sdmExam;

public class OccupiedPositionException extends Exception {

    public OccupiedPositionException(Position position){
        super(position.toString() + "is already occupied.");
    }
}

