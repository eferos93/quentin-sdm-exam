package sdmExam;

import org.junit.jupiter.api.Test;

import static sdmExam.Position.in;
import static org.junit.jupiter.api.Assertions.*;

public class GameShould {
    private final Game game = new Game();

    @Test
    public void notAllowWhitePlaysFirst() {
        assertThrows(Exception.class, () -> game.makeMove(Stone.WHITE, in(4, 5)));
    }

    @Test
    public void notAllowPlayerToPlayTwiceInARow() {
        assertThrows(Exception.class,
                () -> {
                    game.makeMove(Stone.BLACK, in(2, 2));
                    game.makeMove(Stone.BLACK, in(2, 3));
                });
    }

    @Test
    public void notAllowStoneInOccupiedIntersection() {
        assertThrows(Exception.class,
                () -> {
                    game.makeMove(Stone.BLACK, in(2, 2));
                    game.makeMove(Stone.WHITE, in(2, 2));
                });
    }

    @Test
    public void notAllowStoneOutsideBoard() {
        assertThrows(Exception.class, () -> game.makeMove(Stone.BLACK, in(-5, -5)));
    }

    @Test
    public void notAllowStoneInDiagonalAdjacentIntersection() {
        assertThrows(Exception.class,
                () -> {
                    game.makeMove(Stone.BLACK, in(1, 1));
                    game.makeMove(Stone.WHITE, in(1, 2));
                    game.makeMove(Stone.BLACK, in(2, 2));
                });
    }

    @Test
    public void checkIfThereArePossibleLegalMoves() {
        Board testBoard = Board.buildTestBoard(2);
        testBoard.addStoneAt(Stone.WHITE, in(1, 1));
        testBoard.addStoneAt(Stone.WHITE, in(2, 2));
        testBoard.addStoneAt(Stone.BLACK, in(1, 2));
        Game testGame = Game.buildTestGame(testBoard);
        assertFalse(testGame.isPlayerAbleToMakeAMove(Stone.BLACK));
    }

    @Test
    public void provideNoWinner() throws Exception {
        game.makeMove(Stone.BLACK, in(1, 1));
        game.makeMove(Stone.WHITE, in(2, 1));
        assertEquals(Stone.NONE, game.getWinner());
    }

    @Test
    public void provideCorrectWinner() throws Exception {
        Game customGame = Game.buildTestGame(4);
        customGame.makeMove(Stone.BLACK, in(1, 1));
        customGame.makeMove(Stone.WHITE, in(2, 2));
        customGame.makeMove(Stone.BLACK, in(2, 1));
        customGame.makeMove(Stone.WHITE, in(2, 3));
        customGame.makeMove(Stone.BLACK, in(3, 1));
        customGame.makeMove(Stone.WHITE, in(4, 1));
        customGame.makeMove(Stone.BLACK, in(3, 2));
        customGame.makeMove(Stone.WHITE, in(2, 4));
        customGame.makeMove(Stone.BLACK, in(4, 2));
        assertEquals(Stone.BLACK, customGame.getWinner());
    }

    @Test
    public void provideCorrectWinnerMergeChainsFeature() throws Exception {
        Game customGame = Game.buildTestGame(4);
        customGame.makeMove(Stone.BLACK, in(1, 1));
        customGame.makeMove(Stone.WHITE, in(1, 2));
        customGame.makeMove(Stone.BLACK, in(4, 4));
        assertEquals(Stone.NONE, customGame.getWinner());
        customGame.makeMove(Stone.WHITE, in(1, 3));
        customGame.makeMove(Stone.BLACK, in(2, 1));
        customGame.makeMove(Stone.WHITE, in(1, 4));
        customGame.makeMove(Stone.BLACK, in(3, 4));
        customGame.makeMove(Stone.WHITE, in(2, 4));
        assertEquals(Stone.NONE, customGame.getWinner());
        customGame.makeMove(Stone.BLACK, in(3, 1));
        customGame.makeMove(Stone.WHITE, in(2, 3));
        customGame.makeMove(Stone.BLACK, in(3, 3));
        customGame.makeMove(Stone.WHITE, in(2, 2));
        assertEquals(Stone.NONE, customGame.getWinner());
        customGame.makeMove(Stone.BLACK, in(3, 2));
        assertEquals(Stone.BLACK, customGame.getWinner());
    }

    @Test
    public void provideCorrectWinnerWithPieRule() throws Exception {
        Game customGame = Game.buildTestGame(4);
        customGame.makeMove(Stone.BLACK, in(1, 1));
        customGame.applyPieRule();
        customGame.makeMove(Stone.WHITE, in(1, 2));
        customGame.makeMove(Stone.BLACK, in(4, 4));
        assertEquals(Stone.NONE, customGame.getWinner());
        customGame.makeMove(Stone.WHITE, in(1, 3));
        customGame.makeMove(Stone.BLACK, in(2, 1));
        customGame.makeMove(Stone.WHITE, in(1, 4));
        customGame.makeMove(Stone.BLACK, in(3, 4));
        customGame.makeMove(Stone.WHITE, in(2, 4));
        assertEquals(Stone.NONE, customGame.getWinner());
        customGame.makeMove(Stone.BLACK, in(3, 1));
        customGame.makeMove(Stone.WHITE, in(2, 3));
        customGame.makeMove(Stone.BLACK, in(3, 3));
        customGame.makeMove(Stone.WHITE, in(2, 2));
        assertEquals(Stone.NONE, customGame.getWinner());
        customGame.makeMove(Stone.BLACK, in(3, 2));
        assertEquals(Stone.BLACK, customGame.getWinner());
    }

    @Test
    public void modifyBoardAccordingTwoTerritoriesInSamePlay() throws Exception {
        Game customGame = Game.buildTestGame(5);
        customGame.makeMove(Stone.BLACK, in(2, 5));
        customGame.makeMove(Stone.WHITE, in(2, 3));
        customGame.makeMove(Stone.BLACK, in(2, 4));
        customGame.makeMove(Stone.WHITE, in(2, 2));
        customGame.makeMove(Stone.BLACK, in(4, 2));
        customGame.makeMove(Stone.WHITE, in(2, 1));
        customGame.makeMove(Stone.BLACK, in(4, 1));
        customGame.makeMove(Stone.WHITE, in(3, 1));
        customGame.makeMove(Stone.BLACK, in(5, 4));
        customGame.makeMove(Stone.WHITE, in(4, 3));
        customGame.makeMove(Stone.BLACK, in(5, 5));
        customGame.makeMove(Stone.WHITE, in(4, 4));
        customGame.makeMove(Stone.BLACK, in(3, 4));
        assertEquals(Stone.NONE, customGame.getWinner());
    }
}
