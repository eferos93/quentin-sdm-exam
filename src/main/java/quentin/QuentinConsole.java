package quentin;


import quentin.UI.console.ConsoleInputHandler;
import quentin.UI.console.ConsoleOutputHandler;
import quentin.core.*;

import java.util.InputMismatchException;

public class QuentinConsole extends Quentin<ConsoleInputHandler, ConsoleOutputHandler> {
    private boolean whiteAlreadyPlayed = false;
    public QuentinConsole(int boardSize, ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler,
                          String blackPlayerName, String whitePlayerName) {
        super(boardSize, inputHandler, outputHandler, blackPlayerName, whitePlayerName);
    }
    public QuentinConsole(int boardSize, ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler) {
        super(boardSize, inputHandler, outputHandler);
    }

    private int getCoordinate(Runnable printer) {
        while(true) {
            try {
                printer.run();
                return ConsoleInputHandler.getInteger();
            } catch (InputMismatchException exception) {
                ConsoleOutputHandler.notifyException(exception.getMessage());
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
                ConsoleOutputHandler.notifyException(exception.getMessage());
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
                    outputHandler.askPie();
                    if (inputHandler.askPie()) {
                        applyPieRule();
                        outputHandler.notifyPieRule(getPlayers());
                        return true;
                    } else {
                        return false;
                    }
                } catch (InputMismatchException exception) {
                    ConsoleOutputHandler.notifyException(exception.getMessage());
                }
            }
        }
        return false;
    }


    @Override
    public void play() {
        Player currentPlayer = isFirstTurn() ? getPlayerOfColor(Stone.BLACK) : getPlayerOfColor(getLastPlay().getOppositeColor());
        outputHandler.displayBoard(getBoard());
        outputHandler.displayPlayer(currentPlayer);
        if (!checkIfPlayerIsAbleToMakeAMove(currentPlayer)) { play(); }
        if (askForPieRule(currentPlayer)) { play(); }
        getCoordinatesAndMakeMove(currentPlayer);
        if (checkForWinner()) { return; }
        fillTerritories();
        if (checkForWinner()) { return; }
        play();
    }

    private static int getBoardSize() {
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
                ConsoleOutputHandler.notifyException(exception.getMessage());
            }
        }
        return boardSize;
    }

    private static Quentin<ConsoleInputHandler, ConsoleOutputHandler> initialise() {
        ConsoleOutputHandler.displayTitle();
        ConsoleOutputHandler.displayInstructions();
        int boardSize = getBoardSize();
        ConsoleOutputHandler.askBlackPlayerName();
        String blackPlayerName = ConsoleInputHandler.askBlackPlayerName();
        ConsoleOutputHandler.askWhitePlayerName();
        String whitePlayerName = ConsoleInputHandler.askWhitePlayerName();
        return new QuentinConsole(boardSize, new ConsoleInputHandler(), new ConsoleOutputHandler(),
                blackPlayerName, whitePlayerName);
    }

    public static void main(String... args) {
       initialise().play();
    }
}
