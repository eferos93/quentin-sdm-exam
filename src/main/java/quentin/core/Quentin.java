package quentin.core;

import quentin.ui.InputHandler;
import quentin.ui.OutputHandler;
import quentin.exceptions.*;

import java.util.List;
import java.util.stream.Stream;

public abstract class Quentin<InputHandlerImplementation extends InputHandler, OutputHandlerImplementation extends OutputHandler> {
    protected final GameState gameState;
    private final Player playerOne;
    private final Player playerTwo;
    protected final InputHandlerImplementation inputHandler;
    protected final OutputHandlerImplementation outputHandler;

    protected Quentin(int boardSize, InputHandlerImplementation inputHandler, OutputHandlerImplementation outputHandler,
                   String blackPlayerName, String whitePlayerName) {
        this.gameState = new GameState(boardSize);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.playerOne = new Player(Colour.BLACK, blackPlayerName);
        this.playerTwo = new Player(Colour.WHITE, whitePlayerName);
    }

    protected void makeMove(Colour colour, Position position) throws QuentinException {
        gameState.makeMove(colour, position);
    }

    public boolean isCurrentPlayerNotAbleToMakeAMove() {
        return gameState.isCurrentPlayerNotAbleToMakeAMove(getCurrentPlayer());
    }

    protected Player getPlayerOfColor(Colour colour) {
        return getPlayers().stream().filter(player -> player.getColor() == colour).findFirst().orElseThrow();
    }

    public Player getCurrentPlayer() {
        return getPlayerOfColor(gameState.getCurrentPlayerColour());
    }

    public void passTurn() {
        Player currentPlayer = getCurrentPlayer();
        this.gameState.setLastPlay(currentPlayer.getColor());
        outputHandler.notifyPass(currentPlayer);
    }

    protected boolean checkForWinner() {
        return gameState.getWinner().map(winnerColor -> {
            outputHandler.notifyWinner(getPlayerOfColor(winnerColor));
            return true;
        }).orElse(false);
    }

    protected void applyPieRule() {
        Stream.of(playerOne, playerTwo).forEach(Player::changeSide);
    }

    protected List<Player> getPlayers() {
        return List.of(playerOne, playerTwo);
    }

    protected Board getBoard() {
        return this.gameState.getBoard();
    }

    protected boolean applyPieRuleIfPlayerWants(Player currentPlayer) {
        if (inputHandler.askPie(currentPlayer.getName())) {
            applyPieRule();
            outputHandler.notifyPieRule(getPlayers());
            return true;
        } else {
            return false;
        }
    }

    protected boolean isWhitePlayerFirstTurn() {
        return !gameState.doesWhitePlayerAlreadyPlayed() && getCurrentPlayer().getColor() == Colour.WHITE;
    }

    public abstract void play() throws QuentinException;
}