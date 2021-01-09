package sdmExam;

public class IllegalMoveException extends Exception {
    public IllegalMoveException(String player_colour, Position position){
        super(player_colour + " player cannot put a stone diagonally adjacent to another stone of the same colour this" + position.toString() +
                " without a colour alike orthogonally adjacent.");
    }
}
