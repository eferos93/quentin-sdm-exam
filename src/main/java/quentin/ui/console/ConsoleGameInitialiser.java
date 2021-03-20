package quentin.ui.console;

import quentin.ConsoleQuentin;
import quentin.core.Quentin;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class ConsoleGameInitialiser {
    private static int getBoardSize(ConsoleInputHandler consoleInputHandler, ConsoleOutputHandler consoleOutputHandler) {
        consoleOutputHandler.askBoardSize();
        int boardSize = 0;
        boolean insertedAValidBoardSize = false;

        while (!insertedAValidBoardSize) {
            try {
                boardSize = consoleInputHandler.getInteger();
                if (boardSize < 4 || boardSize > 13) {
                    throw new InputMismatchException("Invalid board size! It must be between 4 and 13!");
                }
                insertedAValidBoardSize = true;
            } catch (NoSuchElementException exception) {
                consoleOutputHandler.notifyException(exception);
            }
        }
        return boardSize;
    }

    public static Quentin<ConsoleInputHandler, ConsoleOutputHandler> initialise(
            ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler) {
        outputHandler.displayTitle();
        outputHandler.displayInstructions();
        int boardSize = getBoardSize(inputHandler, outputHandler);
        outputHandler.askBlackPlayerName();
        String blackPlayerName = inputHandler.askPlayerName();
        if (blackPlayerName.equals("")) {
            blackPlayerName = "Player 1";
        }
        outputHandler.askWhitePlayerName();
        String whitePlayerName = inputHandler.askPlayerName();
        if (whitePlayerName.equals("")) {
            whitePlayerName = "Player 2";
        }
        return new ConsoleQuentin(boardSize, inputHandler, outputHandler,
                blackPlayerName, whitePlayerName);
    }
}