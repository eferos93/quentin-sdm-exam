package quentin;


import quentin.ui.console.ConsoleGameInitialiser;
import quentin.ui.console.ConsoleInputHandler;
import quentin.ui.console.ConsoleOutputHandler;
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

    private void getPositionAndMakeMove(Player currentPlayer) {
        boolean arePositionCoordinatesValid = false;
        do {
            try {
                makeMove(currentPlayer.getColor(), getPosition());
                arePositionCoordinatesValid = true;
            } catch (QuentinException exception) {
                outputHandler.notifyException(exception);
            }
        } while (!arePositionCoordinatesValid);
    }

    private boolean askForPieRule(Player currentPlayer) {
        boolean applyPieRule = false;
        if (isWhitePlayerFirstTurn()) {
            gameState.setWhiteAlreadyPlayed(true);
            while (true) {
                try {
                    outputHandler.askPie(currentPlayer.getName());
                    applyPieRule = applyPieRuleIfPlayerWants(currentPlayer);
                    break;
                } catch (InputMismatchException exception) {
                    outputHandler.notifyException(exception);
                }
            }
        }
        return applyPieRule;
    }

    @Override
    public void play() {
        while (true) {
            Player currentPlayer = getCurrentPlayer();
            outputHandler.displayBoard(getBoard());
            outputHandler.displayPlayer(currentPlayer);
            if (isCurrentPlayerNotAbleToMakeAMove()) { passTurn(); continue; }
            if (askForPieRule(currentPlayer)) { continue; }
            getPositionAndMakeMove(currentPlayer);
            if (checkForWinner()) { break; }
        }
    }

    public static void main(String... args) {
        boolean wantToReplay = false;
        boolean invalidReplayInput;
        ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
        ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
        do {
            ConsoleGameInitialiser.initialise(consoleInputHandler, consoleOutputHandler).play();
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
