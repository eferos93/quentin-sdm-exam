package quentin.exceptions;

import quentin.core.Position;

public class OccupiedPositionException extends quentinException {

    public OccupiedPositionException(Position position){
        super(position + " is already occupied.");
    }
}

