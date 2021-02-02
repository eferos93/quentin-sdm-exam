package quentin.UI.GUI;

import quentin.core.*;
import quentin.exceptions.IllegalMoveException;
import quentin.exceptions.OccupiedPositionException;

import java.util.InputMismatchException;

public class GUIQuentin extends Quentin<GUIInputHandler, GUIOutputHandler> {

    private boolean whiteAlreadyPlayed = false;
    public Position newPosition;
    public boolean playTerminate = false;

    public GUIQuentin(int boardSize, GUIInputHandler inputHandler, GUIOutputHandler outputHandler) {
        super(boardSize, inputHandler, outputHandler, "Player 1","Player 2");
    }

    public Player getCurrentPlayer() {return isFirstTurn() ? getPlayerOfColor(Stone.BLACK) : getPlayerOfColor(getLastPlay().getOppositeColor());}

    public Player getLastPlayer() { return getPlayerOfColor(getCurrentPlayer().getColor()); }

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

    public Boolean checkAndPerformNewMove(Position position) {

        if(!checkIfPlayerIsAbleToMakeAMove(getCurrentPlayer())){
            outputHandler.notifyInvalidMove();
            return false;
        }

        System.out.println(getBoard().intersectionAt(position));

        if (isOccupied(position)) {
            GUIOutputHandler.notifyException(new OccupiedPositionException(position).getMessage());
            return false;
        }

        if(isIllegalMove(getCurrentPlayer().getColor(), position)){
            GUIOutputHandler.notifyException(new IllegalMoveException(position).getMessage());
            return false;
        }

        return true;
    }

    private boolean getCoordinatesAndMakeMove(Player currentPlayer) {
        try{
            makeMove(currentPlayer.getColor(), newPosition);
            return true;
        } catch (Exception exception){
            GUIOutputHandler.notifyException(exception.getMessage());
        }
        return false;
    }

    @Override
    public void play() {
        playTerminate = true;

        if (Boolean.TRUE.equals(checkAndPerformPieRule(getCurrentPlayer()))){
            playTerminate = false;
            return;
        }

        if(!getCoordinatesAndMakeMove(getCurrentPlayer())){
            playTerminate = false;
            return;
        }


        if (checkForWinner()) {
            return;
        }

        fillTerritories();

        if (checkForWinner()) {
            return;
        }
    }
}
