package sdmExam;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    public void noOrthogonalAdjacencyOfIntersection(){
        board.addMarkAt(Mark.WHITE, Position.in(7,9));
        Intersection intersection = board.intersectionAt(Position.in(7,9)).get();
        assertFalse(board.existOrthogonallyAdjacent(intersection));
    }

    @Test
    public void rightOrthogonalAdjacencyOfIntersection(){
        board.addMarkAt(Mark.WHITE, Position.in(3,4));
        board.addMarkAt(Mark.WHITE, Position.in(4,4));
        Intersection intersection = board.intersectionAt(Position.in(3,4)).get();
        assertTrue(board.existOrthogonallyAdjacent(intersection));
    }

    @Test
    public void topOrthogonalAdjacencyOfIntersection(){
        board.addMarkAt(Mark.WHITE, Position.in(12,5));
        board.addMarkAt(Mark.WHITE, Position.in(13,5));
        Intersection intersection = board.intersectionAt(Position.in(12,5)).get();
        assertTrue(board.existOrthogonallyAdjacent(intersection));
    }

    @Test
    public void edgeColorsAfterPie() {
        board.pie();
        assertEquals(board.getLowerAndUpperEdgesColor(), Mark.WHITE);
        assertEquals(board.getLeftAndRightEdgesColor(), Mark.BLACK);
    }

}
