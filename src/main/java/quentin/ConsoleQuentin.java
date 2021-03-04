package quentin;


import quentin.UI.console.ConsoleInputHandler;
import quentin.UI.console.ConsoleOutputHandler;
import quentin.core.*;
import quentin.exceptions.QuentinException;

import java.util.InputMismatchException;

public class ConsoleQuentin extends Quentin<ConsoleInputHandler, ConsoleOutputHandler> {
    public ConsoleQuentin(int boardSize, ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler,
                          String blackPlayerName, String whitePlayerName) {
        super(boardSize, inputHandler, outputHandler, blackPlayerName, whitePlayerName);
    }
    public ConsoleQuentin(int boardSize, ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler) {
        this(boardSize, inputHandler, outputHandler, "Player 1", "Player 2");
    }

    private int getCoordinate(Runnable printer) {
        while(true) {
            try {
                printer.run();
                return inputHandler.getInteger();
            } catch (InputMismatchException exception) {
                outputHandler.notifyException(exception);
            }
        }
    }

    private Position getPosition() {
        return Position.in(getCoordinate(outputHandler::askRowCoordinate),
                getCoordinate(outputHandler::askColumnCoordinate));
    }

    private void getCoordinatesAndMakeMove(Player currentPlayer) {
        boolean areCoordinatesValid = false;
        do {
            try {
                makeMove(currentPlayer.getColor(), getPosition());
                areCoordinatesValid = true;
            } catch (QuentinException exception) {
                outputHandler.notifyException(exception);
            }
        } while (!areCoordinatesValid);
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
                    outputHandler.notifyException(exception);
                }
            }
        }
        return false;
    }

    @Override
    public void play() {
        while (true) {
            Player currentPlayer = getCurrentPlayer();
            outputHandler.displayBoard(getBoard());
            outputHandler.displayPlayer(currentPlayer);
            if (isCurrentPlayerNotAbleToMakeAMove()) { passTurn(); continue; }
            if (askForPieRule(currentPlayer)) { continue; }
            getCoordinatesAndMakeMove(currentPlayer);
            if (checkForWinner()) { break; }
            fillTerritories();
            if (checkForWinner()) { break; }
        }
    }

    private static int getBoardSize(ConsoleInputHandler consoleInputHandler, ConsoleOutputHandler consoleOutputHandler) {
        consoleOutputHandler.askBoardSize();
        int boardSize = 0;
        boolean insertedAValidBoardSize = false;

        while(!insertedAValidBoardSize){
            try {
                boardSize = consoleInputHandler.getInteger();
                if (boardSize < 4 || boardSize > 13) {
                    throw new InputMismatchException("Invalid board size! It must be between 4 and 13!");
                }
                insertedAValidBoardSize = true;
            } catch (InputMismatchException exception) {
                consoleOutputHandler.notifyException(exception);
            }
        }
        return boardSize;
    }

    private static Quentin<ConsoleInputHandler, ConsoleOutputHandler> initialise(
            ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler) {
        outputHandler.displayTitle();
        outputHandler.displayInstructions();
        int boardSize = getBoardSize(inputHandler, outputHandler);
        outputHandler.askBlackPlayerName();
        String blackPlayerName = inputHandler.askPlayerName();
        outputHandler.askWhitePlayerName();
        String whitePlayerName = inputHandler.askPlayerName();
        return new ConsoleQuentin(boardSize, inputHandler, outputHandler,
                blackPlayerName, whitePlayerName);
    }

    public static void main(String... args) {
        boolean wantToReplay = false;
        boolean invalidReplayInput;
        do {
            ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
            ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
            initialise(consoleInputHandler, consoleOutputHandler).play();
            consoleOutputHandler.printWantToReplay();
            try {
                wantToReplay = consoleInputHandler.wantToReplay();
                invalidReplayInput = false;
            } catch (InputMismatchException exception) {
                consoleOutputHandler.notifyException(exception);
                invalidReplayInput = true;
            }
        } while (wantToReplay && !invalidReplayInput);
    }
}
