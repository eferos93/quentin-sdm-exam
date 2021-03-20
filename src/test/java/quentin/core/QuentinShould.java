package quentin.core;

import org.junit.jupiter.api.Test;
import quentin.ConsoleQuentin;
import quentin.ui.console.ConsoleInputHandler;
import quentin.ui.console.ConsoleOutputHandler;

import static quentin.core.Position.in;
import static org.junit.jupiter.api.Assertions.*;

public class QuentinShould {
    private final Quentin<ConsoleInputHandler, ConsoleOutputHandler> quentin =
            new ConsoleQuentin(13, new ConsoleInputHandler(), new ConsoleOutputHandler());

    @Test
    public void notAllowWhitePlaysFirst() {
        assertThrows(Exception.class, () -> quentin.makeMove(Colour.WHITE, in(4, 5)));
    }

    @Test
    public void notAllowPlayerToPlayTwiceInARow() {
        assertThrows(Exception.class,
                () -> {
                    quentin.makeMove(Colour.BLACK, in(2, 2));
                    quentin.makeMove(Colour.BLACK, in(2, 3));
                });
    }

    @Test
    public void notAllowStoneInOccupiedIntersection() {
        assertThrows(Exception.class,
                () -> {
                    quentin.makeMove(Colour.BLACK, in(2, 2));
                    quentin.makeMove(Colour.WHITE, in(2, 2));
                });
    }

    @Test
    public void notAllowStoneOutsideBoard() {
        assertThrows(Exception.class, () -> quentin.makeMove(Colour.BLACK, in(-5, -5)));
    }

    @Test
    public void notAllowStoneInDiagonalAdjacentIntersection() {
        assertThrows(Exception.class,
                () -> {
                    quentin.makeMove(Colour.BLACK, in(1, 1));
                    quentin.makeMove(Colour.WHITE, in(1, 2));
                    quentin.makeMove(Colour.BLACK, in(2, 2));
                });
    }

    @Test
    public void provideNoWinner() {
        quentin.makeMove(Colour.BLACK, in(1, 1));
        quentin.makeMove(Colour.WHITE, in(2, 1));
        assertFalse(quentin.checkForWinner());
    }

    @Test
    public void provideCorrectWinner() {
        Quentin<ConsoleInputHandler, ConsoleOutputHandler> customQuentin =
                new ConsoleQuentin(4, new ConsoleInputHandler(), new ConsoleOutputHandler());
        customQuentin.makeMove(Colour.BLACK, in(1, 1));
        customQuentin.makeMove(Colour.WHITE, in(2, 2));
        customQuentin.makeMove(Colour.BLACK, in(2, 1));
        customQuentin.makeMove(Colour.WHITE, in(2, 3));
        customQuentin.makeMove(Colour.BLACK, in(3, 1));
        customQuentin.makeMove(Colour.WHITE, in(4, 1));
        customQuentin.makeMove(Colour.BLACK, in(3, 2));
        customQuentin.makeMove(Colour.WHITE, in(2, 4));
        customQuentin.makeMove(Colour.BLACK, in(4, 2));
        assertTrue(customQuentin.gameState.getWinner().map(winnerColor -> winnerColor == Colour.BLACK).orElse(false));
    }

    @Test
    public void provideCorrectWinnerWithPieRule() {
        Quentin<ConsoleInputHandler, ConsoleOutputHandler> customQuentin =
                new ConsoleQuentin(4, new ConsoleInputHandler(), new ConsoleOutputHandler());
        GameState gameState = customQuentin.gameState;
        customQuentin.makeMove(Colour.BLACK, in(1, 1));
        customQuentin.applyPieRule();
        customQuentin.makeMove(Colour.WHITE, in(1, 2));
        customQuentin.makeMove(Colour.BLACK, in(4, 4));
        assertTrue(gameState.getWinner().isEmpty());
        customQuentin.makeMove(Colour.WHITE, in(1, 3));
        customQuentin.makeMove(Colour.BLACK, in(2, 1));
        customQuentin.makeMove(Colour.WHITE, in(1, 4));
        customQuentin.makeMove(Colour.BLACK, in(3, 4));
        customQuentin.makeMove(Colour.WHITE, in(2, 4));
        assertTrue(gameState.getWinner().isEmpty());
        customQuentin.makeMove(Colour.BLACK, in(3, 1));
        customQuentin.makeMove(Colour.WHITE, in(2, 3));
        customQuentin.makeMove(Colour.BLACK, in(3, 3));
        customQuentin.makeMove(Colour.WHITE, in(2, 2));
        assertTrue(gameState.getWinner().isEmpty());
        customQuentin.makeMove(Colour.BLACK, in(3, 2));
        assertTrue(gameState.getWinner().map(winnerColor -> winnerColor == Colour.BLACK).orElse(false));
    }

    @Test
    public void provideCorrectWinnerMergeChainsFeature() {
        Quentin<ConsoleInputHandler, ConsoleOutputHandler> customQuentin =
                new ConsoleQuentin(4, new ConsoleInputHandler(), new ConsoleOutputHandler());
        GameState gameState = customQuentin.gameState;
        customQuentin.makeMove(Colour.BLACK, in(1, 1));
        customQuentin.makeMove(Colour.WHITE, in(1, 2));
        customQuentin.makeMove(Colour.BLACK, in(4, 4));
        assertTrue(gameState.getWinner().isEmpty());
        customQuentin.makeMove(Colour.WHITE, in(1, 3));
        customQuentin.makeMove(Colour.BLACK, in(2, 1));
        customQuentin.makeMove(Colour.WHITE, in(1, 4));
        customQuentin.makeMove(Colour.BLACK, in(3, 4));
        customQuentin.makeMove(Colour.WHITE, in(2, 4));
        assertTrue(gameState.getWinner().isEmpty());
        customQuentin.makeMove(Colour.BLACK, in(3, 1));
        customQuentin.makeMove(Colour.WHITE, in(2, 3));
        customQuentin.makeMove(Colour.BLACK, in(3, 3));
        customQuentin.makeMove(Colour.WHITE, in(2, 2));
        assertTrue(gameState.getWinner().isEmpty());
        customQuentin.makeMove(Colour.BLACK, in(3, 2));
        assertTrue(gameState.getWinner().map(winnerColor -> winnerColor == Colour.BLACK).orElse(false));
    }
}
