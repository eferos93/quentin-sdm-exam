package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellShould {
    @Test
    public void beEqualToAnotherCellWithEqualFields() {
        Cell firstCell = new Cell(Position.in(3, 3), Mark.WHITE);
        Cell secondCell = new Cell(Position.in(3, 3), Mark.WHITE);
        assertEquals(firstCell, secondCell);
    }
}
