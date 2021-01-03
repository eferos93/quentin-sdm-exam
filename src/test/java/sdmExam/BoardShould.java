package sdmExam;


import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardShould {
    private final Board board = new Board();

    @Test
    public void getCorrectIntersectionGivenAPosition() {
        Position position = Position.in(5, 7);
        assertDoesNotThrow(() -> board.intersectionAt(position));
    }

    @Test
    public void intersectionOutsideBoard() {
        assertThrows(Exception.class, () -> board.intersectionAt(Position.in(14, 14)));
    }

    @Test
    public void markCorrectlyAnIntersection() throws NoSuchElementException {
        Position position = new Position(5, 7);

        Intersection intersection = board.intersectionAt(position);
        board.addStoneAt(Stone.BLACK, position);
        assertEquals(intersection.getStone(), Stone.BLACK);
    }

}
