package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameShould {
    private final Game game = new Game();

    @Test
    public void notAllowWhitePlaysFirst() {
        assertThrows(Exception.class, () -> game.play(Stone.WHITE, Position.in(4, 5)));
    }

    @Test
    public void notAllowPlayerToPlayTwiceInARow() {
        assertThrows(Exception.class,
                () -> {
                    game.play(Stone.BLACK, Position.in(2, 2));
                    game.play(Stone.BLACK, Position.in(2, 3));
                });
    }

    @Test
    public void notAllowStoneInOccupiedIntersection() {
        assertThrows(Exception.class,
                () -> {
                    game.play(Stone.BLACK, Position.in(2, 2));
                    game.play(Stone.WHITE, Position.in(2, 2));
                });
    }

    @Test
    public void notAllowStoneOutsideBoard() {
        assertThrows(Exception.class, () -> game.play(Stone.BLACK, Position.in(-5, -5)));
    }

    @Test
    public void notAllowStoneInDiagonalAdjacentIntersection() {
        assertThrows(Exception.class,
                () -> {
                    game.play(Stone.BLACK, Position.in(1, 1));
                    game.play(Stone.WHITE, Position.in(1, 2));
                    game.play(Stone.BLACK, Position.in(2, 2));
                });
    }

    //TODO: maybe try to implement a test where there are no possible moves for the player?
    @Test
    public void checkIfThereArePossibleLegalMoves() {
        assertTrue(game.isPlayerAbleToMakeAMove(Stone.BLACK));
    }

    @Test
    public void provideNoWinner() throws Exception {
        game.play(Stone.BLACK, Position.in(1, 1));
        assertEquals(Stone.NONE, game.getWinner());
    }
}
