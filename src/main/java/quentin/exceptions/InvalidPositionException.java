package quentin.exceptions;

import quentin.core.Position;

public class InvalidPositionException extends QuentinException {
    public InvalidPositionException(Position position){
        super(position + " is outside the board.");
    }
}
