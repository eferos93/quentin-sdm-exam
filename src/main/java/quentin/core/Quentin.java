package quentin.core;

import quentin.UI.InputHandler;
import quentin.UI.OutputHandler;
import quentin.exceptions.*;
import java.util.List;
import java.util.stream.Stream;

public abstract class Quentin<InputHandlerImplementation extends InputHandler, OutputHandlerImplementation extends OutputHandler> {
    private final Board board;
    private Stone lastPlay = Stone.NONE;
    private final Player playerOne;
    private final Player playerTwo;
    protected final InputHandlerImplementation inputHandler;
    protected final OutputHandlerImplementation outputHandler;
    protected boolean whiteAlreadyPlayed = false;

    public Quentin(int boardSize, InputHandlerImplementation inputHandler, OutputHandlerImplementation outputHandler,
                   String blackPlayerName, String whitePlayerName) {
        this.board = Board.buildBoard(boardSize);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.playerOne = new Player(Stone.BLACK, blackPlayerName);
        this.playerTwo = new Player(Stone.WHITE, whitePlayerName);
    }

    public Quentin(int boardSize, InputHandlerImplementation inputHandler, OutputHandlerImplementation outputHandler) {
        this.board = Board.buildBoard(boardSize);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.playerOne = new Player(Stone.BLACK, "player1");
        this.playerTwo = new Player(Stone.WHITE, "player2");
    }

    protected void makeMove(Stone color, Position position) throws Exception {
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

    protected boolean isFirstTurn() {
        return lastPlay == Stone.NONE;
    }

    private boolean isInvalidFirstPlayer(Stone playerColor) {
        return isFirstTurn() && playerColor == Stone.WHITE;
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

    protected void fillTerritories() {
        board.fillTerritories(lastPlay);
    }

    protected List<Player> getPlayers() {
        return List.of(playerOne, playerTwo);
    }

    protected Board getBoard() {
        return this.board;
    }

    protected Stone getLastPlay() {
        return this.lastPlay;
    }

    protected void setLastPlay(Stone colorOfPlayerWhoJustPlayed) {
        this.lastPlay = colorOfPlayerWhoJustPlayed;
    }

    public abstract void play();
}

