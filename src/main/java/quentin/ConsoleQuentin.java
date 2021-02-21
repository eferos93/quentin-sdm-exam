package quentin;


import quentin.UI.console.ConsoleInputHandler;
import quentin.UI.console.ConsoleOutputHandler;
import quentin.core.*;

import java.util.InputMismatchException;

public class ConsoleQuentin extends Quentin<ConsoleInputHandler, ConsoleOutputHandler> {
    public ConsoleQuentin(int boardSize, ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler,
                          String blackPlayerName, String whitePlayerName) {
        super(boardSize, inputHandler, outputHandler, blackPlayerName, whitePlayerName);
    }
    public ConsoleQuentin(int boardSize, ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler) {
        super(boardSize, inputHandler, outputHandler);
    }

    private int getCoordinate(Runnable printer) {
        while(true) {
            try {
                printer.run();
                return ConsoleInputHandler.getInteger();
            } catch (InputMismatchException exception) {
                outputHandler.notifyException(exception.getMessage());
            }
        }
    }

    private Position getPosition() {
        return Position.in(getCoordinate(outputHandler::askRowCoordinate), getCoordinate(outputHandler::askColumnCoordinate));
    }

    private void getCoordinatesAndMakeMove(Player currentPlayer) {
        for (boolean areCoordinatesValid = false; !areCoordinatesValid; ){
            try {
                makeMove(currentPlayer.getColor(), getPosition());
                areCoordinatesValid = true;
            } catch (Exception exception) {
                outputHandler.notifyException(exception.getMessage());
            }
        }
    }

    private boolean isWhitePlayerFirstTurn(Player currentPlayer) {
        return !whiteAlreadyPlayed && currentPlayer.getColor() == Stone.WHITE;
    }

    private boolean askForPieRule(Player currentPlayer) {
        if (isWhitePlayerFirstTurn(currentPlayer)) {
            whiteAlreadyPlayed = true;
            while (true) {
                try {
                    outputHandler.askPie(currentPlayer.getName());
                    if (inputHandler.askPie(currentPlayer.getName())) {
                        applyPieRule();
                        outputHandler.notifyPieRule(getPlayers());
                        return true;
                    } else {
                        return false;
                    }
                } catch (InputMismatchException exception) {
                    outputHandler.notifyException(exception.getMessage());
                }
            }
        }
        return false;
    }


    @Override
    public void play() {
        Player currentPlayer = getCurrentPlayer();
        outputHandler.displayBoard(getBoard());
        outputHandler.displayPlayer(currentPlayer);
        if (!isCurrentPlayerAbleToMakeAMove()) { passTurn(); play(); }
        if (askForPieRule(currentPlayer)) { play(); }
        getCoordinatesAndMakeMove(currentPlayer);
        if (checkForWinner()) { return; }
        fillTerritories();
        if (checkForWinner()) { return; }
        play();
    }

    private static int getBoardSize(ConsoleOutputHandler consoleOutputHandler) {
        ConsoleOutputHandler.askBoardSize();
        int boardSize = 0;
        for (boolean insertedAValidBoardSize = false; !insertedAValidBoardSize;) {
            try {
                boardSize = ConsoleInputHandler.getInteger();
                if (boardSize < 4 || boardSize > 13) {
                    throw new InputMismatchException("Invalid board size! It must be between 4 and 13!");
                }
                insertedAValidBoardSize = true;
            } catch (InputMismatchException exception) {
                consoleOutputHandler.notifyException(exception.getMessage());
            }
        }
        return boardSize;
    }

    private static Quentin<ConsoleInputHandler, ConsoleOutputHandler> initialise() {
        ConsoleOutputHandler.displayTitle();
        ConsoleOutputHandler.displayInstructions();
        ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
        int boardSize = getBoardSize(consoleOutputHandler);
        ConsoleOutputHandler.askBlackPlayerName();
        String blackPlayerName = ConsoleInputHandler.askBlackPlayerName();
        ConsoleOutputHandler.askWhitePlayerName();
        String whitePlayerName = ConsoleInputHandler.askWhitePlayerName();
        return new ConsoleQuentin(boardSize, new ConsoleInputHandler(), consoleOutputHandler,
                blackPlayerName, whitePlayerName);
    }

    public static void main(String... args) throws Exception {
       initialise().play();
    }
}
