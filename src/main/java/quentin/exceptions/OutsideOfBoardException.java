package quentin.exceptions;

import quentin.core.Position;

public class OutsideOfBoardException extends QuentinException {
    public OutsideOfBoardException(Position position){
        super(position + " is outside of the board.");
    }
}
