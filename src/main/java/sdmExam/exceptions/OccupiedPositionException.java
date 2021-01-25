package sdmExam.exceptions;

import sdmExam.core.Position;

public class OccupiedPositionException extends Exception {

    public OccupiedPositionException(Position position){
        super(position + "is already occupied.");
    }
}

