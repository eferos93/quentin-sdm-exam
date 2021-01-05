package sdmExam;

public class Game {
    private final Board board = new Board();
    private Stone lastPlay = Stone.NONE;

    public void play(Stone player, Position position) throws Exception {

        if (isInvalidFirstPlayer(player)) {
            throw new Exception("Black player should play first.");
        }

        if (isARepeatedPlay(player)) {
            throw new Exception("A player cannot play twice in a row.");
        }

        if (board.isOccupied(position)) {
            throw new Exception("Position is already occupied.");
        }

        if (isIllegalMove(player, position)) {
            throw new Exception("A player cannot put a stone diagonally adjacent to another stone of the same colour" +
                    " without a colour alike orthogonally adjacent.");
        }

        board.addStoneAt(player, position);
        lastPlay = player;
    }

    private boolean isIllegalMove(Stone player, Position position) {
        final Intersection intersection = board.intersectionAt(position);
        return board.existsDiagonallyAdjacentWithStone(intersection, player) &&
                !board.existsOrthogonallyAdjacentWithStone(intersection, player);
    }

    private boolean isARepeatedPlay(Stone player) {
        return lastPlay == player;
    }

    private boolean isInvalidFirstPlayer(Stone player) {
        return isARepeatedPlay(Stone.NONE) && player == Stone.WHITE;
    }
}
