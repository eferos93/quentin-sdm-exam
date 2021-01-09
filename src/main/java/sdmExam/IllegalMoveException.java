package sdmExam;

public class IllegalMoveException extends Exception {
    public IllegalMoveException(Position position){
        super("A player cannot put a stone diagonally adjacent to another stone of the same colour this" + position.toString() +
                " without a colour alike orthogonally adjacent.");
    }
}
