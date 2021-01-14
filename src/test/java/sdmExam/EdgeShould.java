package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EdgeShould {
    private final Board board = new Board();
    @Test
    public void returnTrueIfAboveToaGivenPosition() {
        Edge edge = Edge.TOP;
        assertEquals(1, edge.getEdgeIndex());
        assertTrue(edge.isAdjacentTo(Position.in(1, 4)));
    }

    @Test
    public void returnTrueIfBelowToAGivenPosition() {
        Edge edge = Edge.BOTTOM;
        assertTrue(edge.isAdjacentTo(Position.in(13, 4)));
    }

    @Test
    public void returnTrueIfOnTheLeftToAGivenPosition() {
        Edge edge = Edge.LEFT;
        assertTrue(edge.isAdjacentTo(Position.in(13, 1)));
    }

    @Test
    public void returnTrueIfOnTheRightToAGivenPosition() {
        Edge edge = Edge.RIGHT;
        assertTrue(edge.isAdjacentTo(Position.in(1, 13)));
    }
}
