package quentin.core;

import quentin.UI.InputHandler;
import quentin.UI.OutputHandler;
import quentin.exceptions.*;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Quentin<InputHandlerImplementation extends InputHandler, OutputHandlerImplementation extends OutputHandler> {
    private Board board;
    private Stone lastPlay = Stone.NONE;
    private final Player playerOne = new Player(Stone.BLACK, "player1" );
    private final Player playerTwo = new Player( Stone.WHITE, "player2");
    private final InputHandlerImplementation inputHandler;
    private final OutputHandlerImplementation outputHandler;
    boolean whiteAlreadyPlayed = false;

    public Quentin(int boardSize, InputHandlerImplementation inputHandler, OutputHandlerImplementation outputHandler) {
        this.board = Board.buildBoard(boardSize);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    protected void makeMove(Stone color, Position position) throws Exception {

        if (isInvalidFirstPlayer(color)) {
            throw new InvalidFirstPlayerException();
        }

        if (isARepeatedPlay(color)) {
            throw new RepeatedPlayException();
        }

        if (board.isOccupied(position)) {
            throw new OccupiedPositionException(position);
        }

        if (isIllegalMove(color, position)) {
            throw new IllegalMoveException(position);
        }

        board.addStoneAt(color, position);
        lastPlay = color;
    }

    //TODO: maybe rename this method to avoid overloading
    private boolean isIllegalMove(Stone playerColor, Position position) {
        return isIllegalMove(playerColor, board.intersectionAt(position));
    }

    private boolean isIllegalMove(Stone playerColor, Intersection intersection) {
        return board.existsDiagonallyAdjacentWithStone(intersection, playerColor) &&
                !board.existsOrthogonallyAdjacentWithStone(intersection, playerColor);
    }

    private boolean isARepeatedPlay(Stone playerColor) {
        return lastPlay == playerColor;
    }

    private boolean isFirstTurn() {
        return lastPlay == Stone.NONE;
    }

    private boolean isInvalidFirstPlayer(Stone playerColor) {
        return isFirstTurn() && playerColor == Stone.WHITE;
    }

    public boolean isPlayerAbleToMakeAMove(Stone playerColor) {
        return board.getEmptyIntersections()
                .anyMatch(emptyIntersection -> !isIllegalMove(playerColor, emptyIntersection));
    }

    public Stone getWinner() {
        return board.colorWithCompleteChain();
    }

    public void applyPieRule() {
        Stream.of(playerOne, playerTwo).forEach(Player::changeSide);
    }

    public void run() {
        outputHandler.displayTitle();
        outputHandler.displayInstructions();
        outputHandler.askBoardSize();
        while(true) {
            try {
                int boardSize = inputHandler.getInteger();
                if (boardSize < 4 || boardSize > 13) {
                    throw new InputMismatchException("Invalid board size! It must be between 4 and 13!");
                }
                board = Board.buildBoard(boardSize);
                break;
            } catch(InputMismatchException exception) {
                outputHandler.notifyException(exception.getMessage());
            }
        }

        outputHandler.askBlackPlayerName();
        String blackPlayerName = inputHandler.askBlackPlayerName();
        playerOne.setName(blackPlayerName);
        outputHandler.askWhitePlayerName();
        String whitePlayerName = inputHandler.askWhitePlayerName();
        playerTwo.setName(whitePlayerName);
        Player winner = play();
        outputHandler.notifyWinner(winner);
    }

    //TODO: Refactor the play and run methods
    public Player play() {
        Player currentPlayer = isFirstTurn() ?
                getPlayerOfColor(Stone.BLACK) :
                getPlayerOfColor(lastPlay.getOppositeColor());

        outputHandler.displayBoard(board);
        outputHandler.displayPlayer(currentPlayer);

        if(!isPlayerAbleToMakeAMove(currentPlayer.getColor())) {
            lastPlay = currentPlayer.getColor();
            outputHandler.notifyPass(currentPlayer);
            return play();
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
                    outputHandler.notifyException(exception.getMessage());
                }
            }
            if (isPieApplied) {
                applyPieRule();
                outputHandler.notifyPieRule(playerOne, playerTwo);
                return play();
            }

        }

        int rowCoordinate, columnCoordinate;
        while (true) {
            while (true) {
                try {
                    outputHandler.askRowCoordinate();
                    rowCoordinate = inputHandler.getInteger();
                    break;
                } catch (InputMismatchException exception) {
                    outputHandler.notifyException(exception.getMessage());
                }
            }

            while (true) {
                try {
                    outputHandler.askColumnCoordinate();
                    columnCoordinate = inputHandler.getInteger();
                    break;
                } catch (InputMismatchException exception) {
                    outputHandler.notifyException(exception.getMessage());
                }
            }

            try {
                makeMove(currentPlayer.getColor(), Position.in(rowCoordinate, columnCoordinate));
                break;
            } catch (Exception exception) {
                outputHandler.notifyException(exception.getMessage());
            }
        }

        Stone winnerColor = getWinner();
        if(winnerColor != Stone.NONE) {
            return getPlayerOfColor(winnerColor);
        }

        board.fillTerritories(lastPlay);

        winnerColor = getWinner();
        if (winnerColor != Stone.NONE) {
            outputHandler.notifyWinner(getPlayerOfColor(winnerColor));
            return getPlayerOfColor(winnerColor);
        }

        return play();
    }

    private Player getPlayerOfColor(Stone color) throws NoSuchElementException {
        return Stream.of(playerOne, playerTwo)
                .filter(player -> player.getColor() == color)
                .findFirst()
                .orElseThrow();
    }
}
