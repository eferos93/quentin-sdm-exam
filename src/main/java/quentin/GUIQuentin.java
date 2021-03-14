package quentin;

import javafx.application.Application;
import quentin.UI.GUI.GUI;
import quentin.UI.GUI.GUIInputHandler;
import quentin.UI.GUI.GUIOutputHandler;
import quentin.core.*;

import java.util.InputMismatchException;
import java.util.stream.Stream;

public class GUIQuentin extends Quentin<GUIInputHandler, GUIOutputHandler> {

    private Position newPosition;

    public GUIQuentin(int boardSize, GUIInputHandler inputHandler, GUIOutputHandler outputHandler, String blackPlayerName, String whitePlayerName) {
        super(boardSize, inputHandler, outputHandler, blackPlayerName,whitePlayerName);
    }

    public void setNewPosition(Position position){
        this.newPosition = position;
    }

    public Player getLastPlayer() { return getPlayerOfColor(getCurrentPlayer().getColor().getOppositeColor()); }

    private boolean isWhitePlayerFirstTurn(Player currentPlayer) {
        return !whiteAlreadyPlayed && currentPlayer.getColor() == Colour.WHITE;
    }

    public Stream<Intersection> getNonEmptyIntersections() {
        return getBoard().getNonEmptyIntersections();
    }

    public boolean askPlayerForPieRule() {
        Player currentPlayer = getCurrentPlayer();
        boolean applyPieRule = false;
        if (isWhitePlayerFirstTurn(currentPlayer)) {
            whiteAlreadyPlayed = true;
            try {
                applyPieRule = applyPieRuleIfPlayerWants(currentPlayer);
            } catch (InputMismatchException exception) {
                notifyException(exception);
            }
        }
        return applyPieRule;
    }

    public boolean isGameEnded() {
        return checkForWinner();
    }

    public void notifyException(Exception exception) {
        outputHandler.notifyException(exception);
    }

    @Override
    public void play() {
        makeMove(getCurrentPlayer().getColor(), newPosition);
    }

    public static void main (String[] args) { new Thread(() -> Application.launch(GUI.class)).start(); }
}