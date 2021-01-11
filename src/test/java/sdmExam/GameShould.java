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
        game.play(Stone.WHITE, Position.in(2, 1));
        assertEquals(Stone.NONE, game.getWinner());
    }

    @Test
    public void provideCorrectWinner() throws Exception {
        Game customGame = Game.buildTestGame(4);
        customGame.play(Stone.BLACK, Position.in(1, 1));
        customGame.play(Stone.WHITE, Position.in(2, 2));
        customGame.play(Stone.BLACK, Position.in(2, 1));
        customGame.play(Stone.WHITE, Position.in(2, 3));
        customGame.play(Stone.BLACK, Position.in(3, 1));
        customGame.play(Stone.WHITE, Position.in(4, 1));
        customGame.play(Stone.BLACK, Position.in(3, 2));
        customGame.play(Stone.WHITE, Position.in(2, 4));
        customGame.play(Stone.BLACK, Position.in(4, 2));
        assertEquals(Stone.BLACK, customGame.getWinner());
    }

    @Test
    public void provideCorrectWinnerMergeChainsFeature() throws Exception {
        Game customGame = Game.buildTestGame(4);
        customGame.play(Stone.BLACK, Position.in(1,1));
        customGame.play(Stone.WHITE, Position.in(1,2));
        customGame.play(Stone.BLACK, Position.in(4,4));
        assertEquals(customGame.getWinner(), Stone.NONE);
        customGame.play(Stone.WHITE, Position.in(1,3));
        customGame.play(Stone.BLACK, Position.in(2,1));
        customGame.play(Stone.WHITE, Position.in(1,4));
        customGame.play(Stone.BLACK, Position.in(3,4));
        customGame.play(Stone.WHITE, Position.in(2,4));
        assertEquals(customGame.getWinner(), Stone.NONE);
        customGame.play(Stone.BLACK, Position.in(3,1));
        customGame.play(Stone.WHITE, Position.in(2,3));
        customGame.play(Stone.BLACK, Position.in(3,3));
        customGame.play(Stone.WHITE, Position.in(2,2));
        assertEquals(customGame.getWinner(), Stone.NONE);
        customGame.play(Stone.BLACK, Position.in(3,2));
        assertEquals(customGame.getWinner(), Stone.BLACK);
    }

    @Test
    public void provideCorrectWinnerWithPieRule() throws Exception {
        Game customGame = Game.buildTestGame(4);
        customGame.play(Stone.BLACK, Position.in(1,1));
        customGame.applyPieRule();
        customGame.play(Stone.WHITE, Position.in(1,2));
        customGame.play(Stone.BLACK, Position.in(4,4));
        assertEquals(customGame.getWinner(), Stone.NONE);
        customGame.play(Stone.WHITE, Position.in(1,3));
        customGame.play(Stone.BLACK, Position.in(2,1));
        customGame.play(Stone.WHITE, Position.in(1,4));
        customGame.play(Stone.BLACK, Position.in(3,4));
        customGame.play(Stone.WHITE, Position.in(2,4));
        assertEquals(customGame.getWinner(), Stone.NONE);
        customGame.play(Stone.BLACK, Position.in(3,1));
        customGame.play(Stone.WHITE, Position.in(2,3));
        customGame.play(Stone.BLACK, Position.in(3,3));
        customGame.play(Stone.WHITE, Position.in(2,2));
        assertEquals(customGame.getWinner(), Stone.NONE);
        customGame.play(Stone.BLACK, Position.in(3,2));
        assertEquals(customGame.getWinner(), Stone.BLACK);
    }

    @Test
    public void provideNoWinnerWithPieRule() throws Exception {
        Game customGame = Game.buildTestGame(4);
        customGame.play(Stone.BLACK, Position.in(1,2));
        customGame.applyPieRule();
        customGame.play(Stone.WHITE, Position.in(1,3));
        customGame.play(Stone.BLACK, Position.in(2,2));
        customGame.play(Stone.WHITE, Position.in(1,4));
        customGame.play(Stone.BLACK, Position.in(3,2));
        customGame.play(Stone.WHITE, Position.in(2,4));
        customGame.play(Stone.BLACK, Position.in(4,2));
        assertEquals(customGame.getWinner(), Stone.NONE);
    }
}
