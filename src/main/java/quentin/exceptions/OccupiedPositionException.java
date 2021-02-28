package quentin.exceptions;

import quentin.core.Position;

public class OccupiedPositionException extends QuentinException {
    public OccupiedPositionException(Position position){
        super(position + " is already occupied.");
    }
}

