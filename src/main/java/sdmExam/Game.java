package sdmExam;

import sdmExam.exceptions.*;

public class Game {
    private final Board board;
    private Stone lastPlay = Stone.NONE;

    public Game() {
        board = new Board();
    }

    private Game(Board board) { this.board = board;  }

    private Game(int boardSize) { this.board = Board.buildTestBoard(boardSize); }
    protected static Game buildTestGame(Board board) { return new Game(board); }
    protected static Game buildTestGame(int boardSize) { return new Game(boardSize); }

    public void play(Stone player, Position position) throws Exception {

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
            throw new IllegalMoveException(position);
        }

        board.addStoneAt(player, position);
        lastPlay = player;
    }

    //TODO: maybe rename this method to avoid overloading
    private boolean isIllegalMove(Stone player, Position position) {
        final Intersection intersection = board.intersectionAt(position);
        return isIllegalMove(player, intersection);
    }

    private boolean isIllegalMove(Stone player, Intersection intersection) {
        return board.existsDiagonallyAdjacentWithStone(intersection, player) &&
                !board.existsOrthogonallyAdjacentWithStone(intersection, player);
    }

    private boolean isARepeatedPlay(Stone player) {
        return lastPlay == player;
    }

    private boolean isInvalidFirstPlayer(Stone player) {
        return lastPlay == Stone.NONE && player == Stone.WHITE;
    }

    public boolean isPlayerAbleToMakeAMove(Stone player) {
        return board.getEmptyIntersections()
                .anyMatch(emptyIntersection -> !isIllegalMove(player, emptyIntersection));
    }

    public Stone getWinner() {
        return board.colorWithCompleteChain();
    }

    public void applyPieRule() {
        board.pie();
    }
}
