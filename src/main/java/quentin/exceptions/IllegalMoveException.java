package quentin.exceptions;

import quentin.core.Position;

public class IllegalMoveException extends quentinException {
    public IllegalMoveException(Position position){
        super("Player cannot put a stone diagonally adjacent to another stone of the same colour in this " + position +
                " without a colour alike orthogonally adjacent.");
    }
}
