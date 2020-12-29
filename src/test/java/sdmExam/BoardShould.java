package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BoardShould {
    Board board = new Board();
    @Test
    public void beEqualtoCell(){
        Cell cell = new Cell(new Position(5,7),Mark.NONE);
        assertEquals(board.cellAt(new Position(5,7)),cell);
    }

    @Test
    public void cellOutsideofBoard(){
        assertNull(board.cellAt(new Position(14,14)), (String) null);
    }
}