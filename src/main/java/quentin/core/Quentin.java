package quentin.core;

import quentin.exceptions.*;
import java.util.stream.Stream;

public class Quentin {
    private final Board board;
    private Stone lastPlay = Stone.NONE;
    private final Player playerOne, playerTwo;

    private Quentin(int boardSize) {
        this(boardSize, "player1", "player2");
    }

    private Quentin(int boardSize, String blackPlayerName, String whitePlayerName) {
        this.board = Board.buildBoard(boardSize);
        this.playerOne = new Player(Stone.BLACK, blackPlayerName);
        this.playerTwo = new Player(Stone.WHITE, whitePlayerName);
    }

    public static Quentin buildGame(int boardSize) {
        return new Quentin(boardSize);
    }

    public static Quentin buildGame(int boardSize, String blackPlayerName, String whitePlayerGame) {
        return new Quentin(boardSize, blackPlayerName, whitePlayerGame);
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
    private boolean isIllegalMove(Stone playerColor, Position position) {
        return isIllegalMove(playerColor, board.intersectionAt(position));
    }

    private boolean isIllegalMove(Stone playerColor, Intersection intersection) {
        return board.existsDiagonallyAdjacentWithStone(intersection, playerColor) &&
                !board.existsOrthogonallyAdjacentWithStone(intersection, playerColor);
    }

    private boolean isARepeatedPlay(Stone playerColor) {
        return lastPlay == playerColor;
    }

    private boolean isInvalidFirstPlayer(Stone playerColor) {
        return lastPlay == Stone.NONE && playerColor == Stone.WHITE;
    }

    public boolean isPlayerAbleToMakeAMove(Stone playerColor) {
        return board.getEmptyIntersections()
                .anyMatch(emptyIntersection -> !isIllegalMove(playerColor, emptyIntersection));
    }

    public Stone getWinner() {
        return board.colorWithCompleteChain();
    }

    public void applyPieRule() {
        Stream.of(playerOne, playerTwo).forEach(Player::changeSide);
    }
}
