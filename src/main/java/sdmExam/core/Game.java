package sdmExam.core;

import sdmExam.exceptions.*;
import java.util.stream.Stream;

public class Game {
    private final Board board;
    private Stone lastPlay = Stone.NONE;
    private final Player playerOne, playerTwo;

    private Game(Board board) {
        this.board = board;
        playerOne = new Player(Stone.BLACK, "player1");
        playerTwo = new Player(Stone.WHITE, "player2");
    }

    public Game() {
        this(13);
    }

    public Game(int boardSize) {
        this(Board.buildBoard(boardSize));
    }

    public static Game buildGame(Board board) {
        return new Game(board);
    }

    public static Game buildGame(int boardSize) {
        return new Game(boardSize);
    }

    public void makeMove(Stone color, Position position) throws Exception {

        if (isInvalidFirstPlayer(color)) {
            throw new InvalidFirstPlayerException();
        }

        if (isARepeatedPlay(color)) {
            throw new RepeatedPlayException();
        }

        if (board.isOccupied(position)) {
            throw new OccupiedPositionException(position);
        }

        if (isIllegalMove(color, position)) {
            throw new IllegalMoveException(position);
        }

        board.addStoneAt(color, position);
        lastPlay = color;
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
        Stream.of(playerOne, playerTwo).forEach(Player::changeSide);
    }
}
