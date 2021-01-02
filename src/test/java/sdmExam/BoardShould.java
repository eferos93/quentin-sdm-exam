package sdmExam;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardShould {
    private final Board board = new Board();

    @Test
    public void getCorrectCellGivenAPosition() {
        Position position = Position.in(5, 7);
        assertTrue(board.cellAt(position).isPresent());
    }

    @Test
    public void cellOutsideBoard() {
        assertTrue(board.cellAt(Position.in(14, 14)).isEmpty());
    }

    @Test
    public void markCorrectlyACell() {
        Position position = new Position(5, 7);
        Intersection intersection = board.cellAt(position).get();
        board.addMarkAt(Mark.BLACK, position);
        assertEquals(intersection.getMark(), Mark.BLACK);
    }

}
