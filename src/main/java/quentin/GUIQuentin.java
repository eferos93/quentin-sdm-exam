package quentin;

import javafx.application.Application;
import quentin.UI.GUI.GUI;
import quentin.UI.GUI.GUIInputHandler;
import quentin.UI.GUI.GUIOutputHandler;
import quentin.core.*;
import quentin.exceptions.IllegalMoveException;
import quentin.exceptions.OccupiedPositionException;

import java.util.InputMismatchException;

public class GUIQuentin extends Quentin<GUIInputHandler, GUIOutputHandler> {

    private boolean playEndSuccessfully = true;
    private Position newPosition;

    public GUIQuentin(int boardSize, GUIInputHandler inputHandler, GUIOutputHandler outputHandler, String blackPlayerName, String whitePlayerName) {
        super(boardSize, inputHandler, outputHandler, blackPlayerName,whitePlayerName);
    }

    public void setNewPosition(Position position){
        this.newPosition = position;
    }

    public Player getCurrentPlayer() {return isFirstTurn() ? getPlayerOfColor(Stone.BLACK) : getPlayerOfColor(getLastPlay().getOppositeColor());}

    public Player getLastPlayer() { return getPlayerOfColor(getCurrentPlayer().getColor().getOppositeColor()); }

    public boolean getPlayEndSuccessfully(){
        return playEndSuccessfully;
    }

    private boolean isWhitePlayerFirstTurn(Player currentPlayer) {
        return !whiteAlreadyPlayed && currentPlayer.getColor() == Stone.WHITE;
    }

    public boolean checkAndPerformPieRule(Player currentPlayer){
        if (isWhitePlayerFirstTurn(currentPlayer)) {
            whiteAlreadyPlayed = true;
            try{
                if (inputHandler.askPie()) {
                    applyPieRule();
                    outputHandler.notifyPieRule(getPlayers());
                    return true;
                }else{
                    return false;
                }
            }catch (InputMismatchException exception) {
                GUIOutputHandler.notifyException(exception.getMessage());
            }
        }
        return false;
    }

    public boolean checkAndPerformEndGameRule() {
        return checkForWinner();
    }

    public boolean checkNewMove() {

        if(!checkIfPlayerIsAbleToMakeAMove(getCurrentPlayer())) { //TODO: not sure it is used
            outputHandler.notifyInvalidMove();
            return false;
        }

        if (isOccupied(newPosition)) {
            GUIOutputHandler.notifyException(new OccupiedPositionException(newPosition).getMessage());
            return false;
        }

        if(isIllegalMove(getCurrentPlayer().getColor(), newPosition)){
            GUIOutputHandler.notifyException(new IllegalMoveException(newPosition).getMessage());
            return false;
        }

        return true;
    }

    @Override
    public void play() {

        if (Boolean.TRUE.equals(checkAndPerformPieRule(getCurrentPlayer()))){
            playEndSuccessfully = false;
            return;
        }

        try{
            makeMove(getCurrentPlayer().getColor(), newPosition);
        } catch (Exception exception){
            playEndSuccessfully = false;
            GUIOutputHandler.notifyException(exception.getMessage());
        }

    }

    public static void main (String[] args) { new Thread(() -> Application.launch(GUI.class)).start(); }
}