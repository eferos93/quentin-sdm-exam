package quentin;

import javafx.application.Application;
import quentin.UI.GUI.GUI;
import quentin.UI.GUI.GUIInputHandler;
import quentin.UI.GUI.GUIOutputHandler;
import quentin.core.*;

import java.util.InputMismatchException;

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
        return !whiteAlreadyPlayed && currentPlayer.getColor() == Stone.WHITE;
    }

    public boolean checkAndPerformPieRule(Player currentPlayer){
        if (isWhitePlayerFirstTurn(currentPlayer)) {
            whiteAlreadyPlayed = true;
            try {
                if (inputHandler.askPie(currentPlayer.getName())) {
                    applyPieRule();
                    outputHandler.notifyPieRule(getPlayers());
                    return true;
                }else{
                    return false;
                }
            } catch (InputMismatchException exception) {
                notifyException(exception.getMessage());
            }
        }
        return false;
    }

    public boolean checkAndPerformEndGameRule() {
        return checkForWinner();
    }

    @Override
    public void play() throws Exception {
        makeMove(getCurrentPlayer().getColor(), newPosition);
    }

    public void notifyException(String exceptionMessage) {
        outputHandler.notifyException(exceptionMessage);
    }

    public static void main (String[] args) { new Thread(() -> Application.launch(GUI.class)).start(); }
}