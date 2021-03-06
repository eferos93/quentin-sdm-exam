package quentin.exceptions;

import quentin.core.Position;

public class IllegalMoveException extends QuentinException {
    public IllegalMoveException(Position position) {
        super("You cannot put a stone diagonally adjacent to another stone of the same colour in this " + position +
                " if they not have a shared colour alike orthogonal stone.");
    }
}
