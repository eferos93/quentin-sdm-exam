package quentin;


import quentin.UI.console.ConsoleInputHandler;
import quentin.UI.console.ConsoleOutputHandler;
import quentin.core.*;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class QuentinConsole extends Quentin<ConsoleInputHandler, ConsoleOutputHandler> {

    public QuentinConsole(int boardSize, ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler,
                          String blackPlayerName, String whitePlayerName) {
        super(boardSize, inputHandler, outputHandler, blackPlayerName, whitePlayerName);
    }
    public QuentinConsole(int boardSize, ConsoleInputHandler inputHandler, ConsoleOutputHandler outputHandler) {
        super(boardSize, inputHandler, outputHandler);
    }

    //TODO: Refactor the play and run methods
    @Override
    public void play() {
        Player currentPlayer = isFirstTurn() ?
                getPlayerOfColor(Stone.BLACK) :
                getPlayerOfColor(getLastPlay().getOppositeColor());

        outputHandler.displayBoard(getBoard());
        outputHandler.displayPlayer(currentPlayer);

        if(!isPlayerAbleToMakeAMove(currentPlayer.getColor())) {
            setLastPlay(currentPlayer.getColor());
            outputHandler.notifyPass(currentPlayer);
            play();
        }

        if (!whiteAlreadyPlayed && currentPlayer.getColor() == Stone.WHITE) {
            whiteAlreadyPlayed = true;
            boolean isPieApplied;
            while(true) {
                try {
                    outputHandler.askPie();
                    isPieApplied = inputHandler.askPie();
                    break;
                } catch(InputMismatchException exception) {
                    ConsoleOutputHandler.notifyException(exception.getMessage());
                }
            }
            if (isPieApplied) {
                applyPieRule();
                outputHandler.notifyPieRule(getPlayers());
                play();
            }

        }

        int rowCoordinate, columnCoordinate;
        while (true) {
            while (true) {
                try {
                    outputHandler.askRowCoordinate();
                    rowCoordinate = ConsoleInputHandler.getInteger();
                    break;
                } catch (InputMismatchException exception) {
                    ConsoleOutputHandler.notifyException(exception.getMessage());
                }
            }

            while (true) {
                try {
                    outputHandler.askColumnCoordinate();
                    columnCoordinate = ConsoleInputHandler.getInteger();
                    break;
                } catch (InputMismatchException exception) {
                    ConsoleOutputHandler.notifyException(exception.getMessage());
                }
            }

            try {
                makeMove(currentPlayer.getColor(), Position.in(rowCoordinate, columnCoordinate));
                break;
            } catch (Exception exception) {
                ConsoleOutputHandler.notifyException(exception.getMessage());
            }
        }

        Stone winnerColor = getWinner();
        if(winnerColor != Stone.NONE) {
            outputHandler.notifyWinner(getPlayerOfColor(winnerColor));
            return;
        }

        fillTerritories();

        winnerColor = getWinner();
        if (winnerColor != Stone.NONE) {
            outputHandler.notifyWinner(getPlayerOfColor(winnerColor));
            return;
        }

        play();
    }

    private Player getPlayerOfColor(Stone color) throws NoSuchElementException {
        return getPlayers().stream()
                .filter(player -> player.getColor() == color)
                .findFirst()
                .orElseThrow();
    }

    private static Quentin<ConsoleInputHandler, ConsoleOutputHandler> initialise() {
        ConsoleOutputHandler.displayTitle();
        ConsoleOutputHandler.displayInstructions();
        ConsoleOutputHandler.askBoardSize();

        while(true) {
            try {
                int boardSize = ConsoleInputHandler.getInteger();
                if (boardSize < 4 || boardSize > 13) {
                    throw new InputMismatchException("Invalid board size! It must be between 4 and 13!");
                }

                break;
            } catch(InputMismatchException exception) {
                ConsoleOutputHandler.notifyException(exception.getMessage());
            }
        }

        ConsoleOutputHandler.askBlackPlayerName();
        String blackPlayerName = ConsoleInputHandler.askBlackPlayerName();
        ConsoleOutputHandler.askWhitePlayerName();
        String whitePlayerName = ConsoleInputHandler.askWhitePlayerName();

        return new QuentinConsole(5, new ConsoleInputHandler(), new ConsoleOutputHandler(),
                blackPlayerName, whitePlayerName);
    }

    public static void main(String... args) {
        Quentin<ConsoleInputHandler, ConsoleOutputHandler> quentin = initialise();
        quentin.play();
    }
}
