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

    @Test
    public void checkIfThereArePossibleLegalMoves() {
        Board testBoard = Board.buildTestBoard(2);
        testBoard.addStoneAt(Stone.WHITE,Position.in(1,1));
        testBoard.addStoneAt(Stone.WHITE,Position.in(2,2));
        testBoard.addStoneAt(Stone.BLACK,Position.in(1,2));
        Game testGame = Game.buildTestGame(testBoard);
        assertFalse(testGame.isPlayerAbleToMakeAMove(Stone.BLACK));
    }
}
