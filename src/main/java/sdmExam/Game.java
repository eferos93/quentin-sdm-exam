package sdmExam;

public class Game {
    private final Board board;
    private Stone lastPlay = Stone.NONE;

    public Game() {
        board = new Board();
    }

    private Game(Board board) { this.board = board;  }

    protected static Game buildTestGame(Board board) { return new Game(board); }

    public void play(Stone player, Position position) throws InvalidFirstPlayerException, RepeatedPlayException, OccupiedPositionException,IllegalMoveException {

        if (isInvalidFirstPlayer(player)) {
            throw new InvalidFirstPlayerException();
        }

        if (isARepeatedPlay(player)) {
            throw new RepeatedPlayException(getPlayerColour(player));
        }

        if (board.isOccupied(position)) {
            throw new OccupiedPositionException(position);
        }

        if (isIllegalMove(player, position)) {
            throw new IllegalMoveException(getPlayerColour(player), position);
        }

        board.addStoneAt(player, position);
        lastPlay = player;
    }

    private boolean isIllegalMove(Stone player, Position position) {
        final Intersection intersection = board.intersectionAt(position);
        return board.existsDiagonallyAdjacentWithStone(intersection, player) &&
                !board.existsOrthogonallyAdjacentWithStone(intersection, player);
    }

    private boolean isIllegalMove(Stone player, Intersection intersection) {
        return board.existsDiagonallyAdjacentWithStone(intersection, player) &&
                !board.existsOrthogonallyAdjacentWithStone(intersection, player);
    }

    private boolean isARepeatedPlay(Stone player) {
        return lastPlay == player;
    }

    private boolean isInvalidFirstPlayer(Stone player) {
        return isARepeatedPlay(Stone.NONE) && player == Stone.WHITE;
    }

    public boolean isPlayerAbleToMakeAMove(Stone player) {
        return board.stream()
                .filter(intersection -> !intersection.isOccupied())
                .anyMatch(emptyIntersection -> !isIllegalMove(player, emptyIntersection));
    }

    private String getPlayerColour(Stone player){
        if(player == Stone.BLACK)return "BLACK";
        else return "WHITE";
           }
}
