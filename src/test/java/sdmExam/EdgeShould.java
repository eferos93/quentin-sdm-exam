package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EdgeShould {
    @Test
    public void returnTrueIfAboveToaGivenPosition() {
        Edge edge = Edge.UP;
        assertTrue(edge.isAdjacentTo(Position.in(1, 4)));
    }

    @Test
    public void returnTrueIfBelowToAGivenPosition() {
        Edge edge = Edge.DOWN;
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

    @Test
    public void setCorrectlyThePosition(){
        Edge edge = Edge.RIGHT;
        edge.setPosition(5);
        assertEquals(edge.getPosition(), 5);
        assertTrue(edge.isAdjacentTo(Position.in(4,4)));
    }
}
