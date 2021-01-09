package sdmExam;

public class Game {
    private final Board board;
    private Stone lastPlay = Stone.NONE;

    public Game() {
        board = new Board();
    }

    private Game(int boardSize) {
        board = Board.buildTestBoard(boardSize);
    }

    protected static Game buildTestGame(int boardSize) {
        return new Game(boardSize);
    }

    public void play(Stone player, Position position) throws OurException, InvalidFirstPlayerException, RepeatedPlayException, OccupiedPositionException {

        if (isInvalidFirstPlayer(player)) {
            throw new InvalidFirstPlayerException();
        }

        if (isARepeatedPlay(player)) {
            throw new RepeatedPlayException();
        }

        if (board.isOccupied(position)) {
            throw new OccupiedPositionException(position);
        }

        if (isIllegalMove(player, position)) {
            throw new OurException("A player cannot put a stone diagonally adjacent to another stone of the same colour" +
                    " without a colour alike orthogonally adjacent.", position);
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
}
