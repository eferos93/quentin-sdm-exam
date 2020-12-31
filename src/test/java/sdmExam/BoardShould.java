package sdmExam;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardShould {
    private final Board board = new Board();

    @Test
    public void getCorrectCellGivenAPosition() {
        Position position = Position.in(5, 7);
        Cell cell = Cell.empty(position);
        assertTrue(board.cellAt(position).isPresent());
    }

    @Test
    public void cellOutsideBoard() {
        assertTrue(board.cellAt(Position.in(14, 14)).isEmpty());
    }

    @Test
    public void marksCorrectlyACell() {
        Position position = new Position(5, 7);
        Cell cell = board.cellAt(position).get();
        board.addMarkAt(Mark.BLACK, position);
        assertEquals(cell.getMark(), Mark.BLACK);
    }

}
