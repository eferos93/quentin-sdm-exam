package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EdgeShould {
    @Test
    public void returnTrueIfCloseToaGivenPosition() {
        Edge edge = new Edge(Stone.WHITE, 0);
        assertTrue(edge.isAboveWithRespectTo(Position.in(1, 4)));
    }
}
