package quentin.core;

import quentin.UI.InputHandler;
import quentin.UI.OutputHandler;
import quentin.exceptions.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public abstract class Quentin<InputHandlerImplementation extends InputHandler, OutputHandlerImplementation extends OutputHandler> {
    private final Board board;
    private Stone lastPlay = Stone.NONE;
    private final Player playerOne;
    private final Player playerTwo;
    protected final InputHandlerImplementation inputHandler;
    protected final OutputHandlerImplementation outputHandler;

    protected Quentin(int boardSize, InputHandlerImplementation inputHandler, OutputHandlerImplementation outputHandler,
                   String blackPlayerName, String whitePlayerName) {
        this.board = Board.buildBoard(boardSize);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.playerOne = new Player(Stone.BLACK, blackPlayerName);
        this.playerTwo = new Player(Stone.WHITE, whitePlayerName);
    }

    protected Quentin(int boardSize, InputHandlerImplementation inputHandler, OutputHandlerImplementation outputHandler) {
        this.board = Board.buildBoard(boardSize);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.playerOne = new Player(Stone.BLACK, "player1");
        this.playerTwo = new Player(Stone.WHITE, "player2");
    }

    protected void makeMove(Stone color, Position position) throws Exception {
        /*
        if (isInvalidFirstPlayer(color)) {
            throw new InvalidFirstPlayerException();
        }
        if (isARepeatedPlay(color)) {
            throw new RepeatedPlayException();
        }
        if (isOccupied(position)) {
            throw new OccupiedPositionException(position);
        }
        if (isIllegalMove(color, position)) {
            throw new IllegalMoveException(position);
        }
         */
        board.addStoneAt(color, position);
        lastPlay = color;
    }

    protected boolean isOccupied(Position position) {
        return board.isOccupied(position);
    }

    //TODO: maybe rename this method to avoid overloading
    protected boolean isIllegalMove(Stone playerColor, Position position) {
        return isIllegalMove(playerColor, board.intersectionAt(position));
    }

    private boolean isIllegalMove(Stone playerColor, Intersection intersection) {
        return board.existsDiagonallyAdjacentWithStone(intersection, playerColor) &&
                !board.existsOrthogonallyAdjacentWithStone(intersection, playerColor);
    }

    protected boolean isARepeatedPlay(Stone playerColor) {
        return lastPlay == playerColor;
    }

    protected boolean isFirstTurn() {
        return lastPlay == Stone.NONE;
    }

    protected boolean isInvalidFirstPlayer(Stone playerColor) {
        return isFirstTurn() && playerColor == Stone.WHITE;
    }

    public boolean isPlayerAbleToMakeAMove(Stone playerColor) {
        return board.getEmptyIntersections().anyMatch(emptyIntersection -> !isIllegalMove(playerColor, emptyIntersection));
    }

    protected Player getPlayerOfColor(Stone color) throws NoSuchElementException {
        return getPlayers().stream().filter(player -> player.getColor() == color).findFirst().orElseThrow();
    }

    protected boolean checkIfPlayerIsAbleToMakeAMove(Player currentPlayer) {
        if(!isPlayerAbleToMakeAMove(currentPlayer.getColor())) {
            setLastPlay(currentPlayer.getColor());
            outputHandler.notifyPass(currentPlayer);
            return false;
        }
        return true;
    }

    protected boolean checkForWinner() {
        Stone winnerColor = getWinner();
        if (winnerColor != Stone.NONE) {
            outputHandler.notifyWinner(getPlayerOfColor(winnerColor));
            return true;
        }
        return false;
    }

    protected Stone getWinner() {
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

    public Stone getLastPlay() {
        return this.lastPlay;
    }

    protected void setLastPlay(Stone colorOfPlayerWhoJustPlayed) {
        this.lastPlay = colorOfPlayerWhoJustPlayed;
    }

    public abstract void play();
}

