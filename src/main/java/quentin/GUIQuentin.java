package quentin;

import javafx.application.Application;
import quentin.ui.gui.GUI;
import quentin.ui.gui.GUIInputHandler;
import quentin.ui.gui.GUIOutputHandler;
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

    public Stream<Intersection> getNonEmptyIntersections() {
        return getBoard().getNonEmptyIntersections();
    }

    public boolean askPlayerForPieRule() {
        boolean applyPieRule = false;
        if (isWhitePlayerFirstTurn()) {
            gameState.setWhiteAlreadyPlayed(true);
            try {
                applyPieRule = applyPieRuleIfPlayerWants(getCurrentPlayer());
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