package quentin.UI.GUI;

import javafx.application.Application;
import quentin.core.*;
import quentin.exceptions.IllegalMoveException;
import quentin.exceptions.OccupiedPositionException;

import java.util.InputMismatchException;

public class GUIQuentin extends Quentin<GUIInputHandler, GUIOutputHandler> {

    private boolean whiteAlreadyPlayed = false;
    private boolean playEndSuccessfully = true;
    private Position newPosition;

    public GUIQuentin(int boardSize, GUIInputHandler inputHandler, GUIOutputHandler outputHandler) {
        super(boardSize, inputHandler, outputHandler, "Player 1","Player 2");
    }

    public void setNewPosition(Position position){
        this.newPosition = position;
    }

    public Player getCurrentPlayer() {return isFirstTurn() ? getPlayerOfColor(Stone.BLACK) : getPlayerOfColor(getLastPlay().getOppositeColor());}

    public Player getLastPlayer() { return getPlayerOfColor(getCurrentPlayer().getColor().getOppositeColor()); }

    public Boolean getPlayEndSuccessfully(){
        return playEndSuccessfully;
    }

    private boolean isWhitePlayerFirstTurn(Player currentPlayer) {
        return !whiteAlreadyPlayed && currentPlayer.getColor() == Stone.WHITE;
    }

    public Boolean checkAndPerformPieRule(Player currentPlayer){
        if (isWhitePlayerFirstTurn(currentPlayer)) {
            whiteAlreadyPlayed = true;
            try{
                if (inputHandler.askPie()) {
                    applyPieRule();
                    outputHandler.notifyPieRule(getPlayers());
                    return true;
                }
            }catch (InputMismatchException exception) {
                GUIOutputHandler.notifyException(exception.getMessage());
            }
        }
        return false;
    }

    public Boolean checkAndPerformEndGameRule() {
        return checkForWinner();
    }

    public Boolean checkAndPerformNewMove() {

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

//        if(!getCoordinatesAndMakeMove(getCurrentPlayer())){ return; } // unnecessary (?)
//        if (checkForWinner()) { return; } // unnecessary (?)
    }

    public static void main (String[] args) { new Thread(() -> Application.launch(GUI.class)).start(); }
}
