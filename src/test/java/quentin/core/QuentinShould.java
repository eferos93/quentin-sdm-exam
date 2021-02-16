package quentin.core;

import org.junit.jupiter.api.Test;
import quentin.ConsoleQuentin;
import quentin.UI.console.ConsoleInputHandler;
import quentin.UI.console.ConsoleOutputHandler;

import static quentin.core.Position.in;
import static org.junit.jupiter.api.Assertions.*;

public class QuentinShould {
    private final Quentin<ConsoleInputHandler, ConsoleOutputHandler> quentin =
            new ConsoleQuentin(13, new ConsoleInputHandler(), new ConsoleOutputHandler());

    @Test
    public void notAllowWhitePlaysFirst() {
        assertThrows(Exception.class, () -> quentin.makeMove(Stone.WHITE, in(4, 5)));
    }

    @Test
    public void notAllowPlayerToPlayTwiceInARow() {
        assertThrows(Exception.class,
                () -> {
                    quentin.makeMove(Stone.BLACK, in(2, 2));
                    quentin.makeMove(Stone.BLACK, in(2, 3));
                });
    }

    @Test
    public void notAllowStoneInOccupiedIntersection() {
        assertThrows(Exception.class,
                () -> {
                    quentin.makeMove(Stone.BLACK, in(2, 2));
                    quentin.makeMove(Stone.WHITE, in(2, 2));
                });
    }

    @Test
    public void notAllowStoneOutsideBoard() {
        assertThrows(Exception.class, () -> quentin.makeMove(Stone.BLACK, in(-5, -5)));
    }

    @Test
    public void notAllowStoneInDiagonalAdjacentIntersection() {
        assertThrows(Exception.class,
                () -> {
                    quentin.makeMove(Stone.BLACK, in(1, 1));
                    quentin.makeMove(Stone.WHITE, in(1, 2));
                    quentin.makeMove(Stone.BLACK, in(2, 2));
                });
    }

    @Test
    public void provideNoWinner() throws Exception {
        quentin.makeMove(Stone.BLACK, in(1, 1));
        quentin.makeMove(Stone.WHITE, in(2, 1));
        assertEquals(Stone.NONE, quentin.getWinner());
    }

    @Test
    public void provideCorrectWinner() throws Exception {
        Quentin<ConsoleInputHandler, ConsoleOutputHandler> customQuentin =
                new ConsoleQuentin(4, new ConsoleInputHandler(), new ConsoleOutputHandler());
        customQuentin.makeMove(Stone.BLACK, in(1, 1));
        customQuentin.makeMove(Stone.WHITE, in(2, 2));
        customQuentin.makeMove(Stone.BLACK, in(2, 1));
        customQuentin.makeMove(Stone.WHITE, in(2, 3));
        customQuentin.makeMove(Stone.BLACK, in(3, 1));
        customQuentin.makeMove(Stone.WHITE, in(4, 1));
        customQuentin.makeMove(Stone.BLACK, in(3, 2));
        customQuentin.makeMove(Stone.WHITE, in(2, 4));
        customQuentin.makeMove(Stone.BLACK, in(4, 2));
        assertEquals(Stone.BLACK, customQuentin.getWinner());
    }

    @Test
    public void provideCorrectWinnerWithPieRule() throws Exception {
        Quentin<ConsoleInputHandler, ConsoleOutputHandler> customQuentin =
                new ConsoleQuentin(4, new ConsoleInputHandler(), new ConsoleOutputHandler());
        customQuentin.makeMove(Stone.BLACK, in(1, 1));
        customQuentin.applyPieRule();
        customQuentin.makeMove(Stone.WHITE, in(1, 2));
        customQuentin.makeMove(Stone.BLACK, in(4, 4));
        assertEquals(Stone.NONE, customQuentin.getWinner());
        customQuentin.makeMove(Stone.WHITE, in(1, 3));
        customQuentin.makeMove(Stone.BLACK, in(2, 1));
        customQuentin.makeMove(Stone.WHITE, in(1, 4));
        customQuentin.makeMove(Stone.BLACK, in(3, 4));
        customQuentin.makeMove(Stone.WHITE, in(2, 4));
        assertEquals(Stone.NONE, customQuentin.getWinner());
        customQuentin.makeMove(Stone.BLACK, in(3, 1));
        customQuentin.makeMove(Stone.WHITE, in(2, 3));
        customQuentin.makeMove(Stone.BLACK, in(3, 3));
        customQuentin.makeMove(Stone.WHITE, in(2, 2));
        assertEquals(Stone.NONE, customQuentin.getWinner());
        customQuentin.makeMove(Stone.BLACK, in(3, 2));
        assertEquals(Stone.BLACK, customQuentin.getWinner());
    }

    @Test
    public void provideCorrectWinnerMergeChainsFeature() throws Exception {
        Quentin<ConsoleInputHandler, ConsoleOutputHandler> customQuentin =
                new ConsoleQuentin(4, new ConsoleInputHandler(), new ConsoleOutputHandler());
        customQuentin.makeMove(Stone.BLACK, in(1, 1));
        customQuentin.makeMove(Stone.WHITE, in(1, 2));
        customQuentin.makeMove(Stone.BLACK, in(4, 4));
        assertEquals(Stone.NONE, customQuentin.getWinner());
        customQuentin.makeMove(Stone.WHITE, in(1, 3));
        customQuentin.makeMove(Stone.BLACK, in(2, 1));
        customQuentin.makeMove(Stone.WHITE, in(1, 4));
        customQuentin.makeMove(Stone.BLACK, in(3, 4));
        customQuentin.makeMove(Stone.WHITE, in(2, 4));
        assertEquals(Stone.NONE, customQuentin.getWinner());
        customQuentin.makeMove(Stone.BLACK, in(3, 1));
        customQuentin.makeMove(Stone.WHITE, in(2, 3));
        customQuentin.makeMove(Stone.BLACK, in(3, 3));
        customQuentin.makeMove(Stone.WHITE, in(2, 2));
        assertEquals(Stone.NONE, customQuentin.getWinner());
        customQuentin.makeMove(Stone.BLACK, in(3, 2));
        assertEquals(Stone.BLACK, customQuentin.getWinner());
    }
}
