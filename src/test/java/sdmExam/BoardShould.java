package sdmExam;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class BoardShould {
    Board board = new Board();
    private static final String expected ="You marked illegal cell";

    @Test
    public void beEqualtoCell(){
        Cell cell = new Cell(new Position(5,7),Mark.NONE);
        assertEquals(board.cellAt(new Position(5,7)),cell);
    }

    @Test
    public void cellOutsideofBoard(){
        assertNull(board.cellAt(new Position(14,14)), (String) null);
    }

    @Test
    public void markedCell(){
        Position position = new Position(5, 7);
        Cell cell = board.cellAt(position);
        board.AddMarkAt(Mark.BLACK, position);
        assertEquals(cell.getMark(), Mark.BLACK);
    }

    @Test
    public void illegalCell(){
        Position position = new Position(14, 14);
        Cell cell = board.cellAt(position);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        board.AddMarkAt(Mark.WHITE, position);
        assertEquals(expected,fakeStandardOutput.toString());

    }
}
