package sdmExam.exceptions;

import sdmExam.Position;

public class IllegalMoveException extends Exception {
    public IllegalMoveException(Position position){
        super("Player cannot put a stone diagonally adjacent to another stone of the same colour this" + position.toString() +
                " without a colour alike orthogonally adjacent.");
    }
}
