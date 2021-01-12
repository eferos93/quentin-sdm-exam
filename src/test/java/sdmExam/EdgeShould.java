package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EdgeShould {
    @Test
    public void returnTrueIfAboveToaGivenPosition() {
        Edge edge = new Edge(Stone.WHITE, 0);
        assertTrue(edge.isAboveWithRespectTo(Position.in(1, 4)));
    }

    @Test
    public void returnTrueIfBelowToAGivenPosition() {
        Edge edge = new Edge(Stone.WHITE, 14);
        assertTrue(edge.isBelowWithRespectTo(Position.in(13, 4)));
    }
}
