package sdmExam;


import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(board.cellAt(new Position(14,14)), Optional.empty());
    }

    @Test
    public void marksCorrectlyACell() {
        Position position = new Position(5, 7);
        Cell cell = board.cellAt(position).get();
        board.addMarkAt(Mark.BLACK, position);
        assertEquals(cell.getMark(), Mark.BLACK);
    }

}
