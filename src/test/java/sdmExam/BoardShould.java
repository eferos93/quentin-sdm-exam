package sdmExam;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardShould {
    private final Board board = new Board();

    @Test
    public void getCorrectIntersectionGivenAPosition() {
        assertTrue(board.intersectionAt(Position.in(5, 7)).isPresent());
    }

    @Test
    public void intersectionOutsideBoard() {
        assertTrue(board.intersectionAt(Position.in(14, 14)).isEmpty());
    }

    @Test
    public void markCorrectlyAnIntersection() {
        Intersection intersection = board.intersectionAt(Position.in(5,7)).get();
        board.addMarkAt(Mark.BLACK, Position.in(5,7));
        assertEquals(intersection.getMark(), Mark.BLACK);
    }

}
