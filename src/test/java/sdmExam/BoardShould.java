package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardShould {
    @Test
    public void beEqualtoCell(){
        Cell cell = new Cell(new Position(5,7),Mark.NONE);
        Board board = new Board();
        assertEquals(board.cellAt(new Position(5,7)),cell);
    }
}
