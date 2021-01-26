package quentin.core;

import quentin.UI.InputHandler;
import quentin.UI.OutputHandler;
import quentin.exceptions.*;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Quentin<InputHandlerImplementation extends InputHandler, OutputHandlerImplementation extends OutputHandler> {
    private final Board board;
    private Stone lastPlay = Stone.NONE;
    private final Player playerOne, playerTwo;
    private final InputHandlerImplementation inputHandler;
    private final OutputHandlerImplementation outputHandler;
    boolean whiteAlreadyPlayed = false;

    protected Quentin(int boardSize, InputHandlerImplementation inputHandler, OutputHandlerImplementation outputHandler) {
        this(boardSize, "player1", "player2", inputHandler, outputHandler);
    }

    protected Quentin(int boardSize,
                    String blackPlayerName, String whitePlayerName,
                    InputHandlerImplementation inputHandler, OutputHandlerImplementation outputHandler) {
        this.board = Board.buildBoard(boardSize);
        this.playerOne = new Player(Stone.BLACK, blackPlayerName);
        this.playerTwo = new Player(Stone.WHITE, whitePlayerName);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
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

    public void play() throws Exception {
        if(isFirstTurn()) {
            outputHandler.displayTitle();
            outputHandler.displayInstructions();
        }

        Player currentPlayer = isFirstTurn() ?
                getPlayerOfColor(Stone.BLACK) :
                getPlayerOfColor(lastPlay.getOppositeColor());

        outputHandler.displayBoard(board);
        outputHandler.displayPlayer(currentPlayer);

        if(!isPlayerAbleToMakeAMove(currentPlayer.getColor())) {
            lastPlay = currentPlayer.getColor();
            outputHandler.notifyPass(currentPlayer);
            play();
        }

        if (!whiteAlreadyPlayed && currentPlayer.getColor() == Stone.WHITE) {
            whiteAlreadyPlayed = true;
            outputHandler.askPie();
            if (inputHandler.askPie()) {
                applyPieRule();
                play();
            }
        }

        outputHandler.askRowCoordinate();
        int rowCoordinate = inputHandler.getInteger();
        outputHandler.askColumnCoordinate();
        int columnCoordinate = inputHandler.getInteger();

        makeMove(currentPlayer.getColor(), Position.in(rowCoordinate, columnCoordinate));

        Stone winnerColor = getWinner();
        if(winnerColor != Stone.NONE) {
            outputHandler.notifyWinner(getPlayerOfColor(winnerColor));
            return;
        }

        board.searchAndFillTerritories(lastPlay);

        winnerColor = getWinner();
        if (winnerColor != Stone.NONE) {
            outputHandler.notifyWinner(getPlayerOfColor(winnerColor));
            return;
        }

        play();
    }

    private Player getPlayerOfColor(Stone color) throws NoSuchElementException {
        return Stream.of(playerOne, playerTwo)
                .filter(player -> player.getColor() == color)
                .findFirst()
                .orElseThrow();
    }

    private boolean isFirstTurn() {
        return lastPlay == Stone.NONE;
    }
}
