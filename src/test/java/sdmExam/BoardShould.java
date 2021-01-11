package sdmExam;


import org.junit.jupiter.api.Test;


import java.util.ArrayList;
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
        Intersection intersection = board.intersectionAt(Position.in(5,7));
        board.addStoneAt(Stone.BLACK, Position.in(5,7));
        assertEquals(intersection.getStone(), Stone.BLACK);
    }

    @Test
    public void noOrthogonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, Position.in(7, 9));
        Intersection intersection = board.intersectionAt(Position.in(7, 9));
        assertFalse(board.existsOrthogonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void rightOrthogonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, Position.in(3, 4));
        board.addStoneAt(Stone.WHITE, Position.in(4, 4));
        Intersection intersection = board.intersectionAt(Position.in(3, 4));
        assertTrue(board.existsOrthogonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void topOrthogonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, Position.in(12, 5));
        board.addStoneAt(Stone.WHITE, Position.in(13, 5));
        Intersection intersection = board.intersectionAt(Position.in(12, 5));
        assertTrue(board.existsOrthogonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void edgeColorsAfterPie() {
        board.pie();
        assertEquals(board.getLowerAndUpperEdgesColor(), Stone.WHITE);
        assertEquals(board.getLeftAndRightEdgesColor(), Stone.BLACK);
    }

    @Test
    public void noDiagonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, Position.in(7, 9));
        Intersection intersection = board.intersectionAt(Position.in(7, 9));
        assertFalse(board.existsDiagonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void upRightDiagonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, Position.in(3, 4));
        board.addStoneAt(Stone.WHITE, Position.in(2, 5));
        Intersection intersection = board.intersectionAt(Position.in(3, 4));
        assertTrue(board.existsDiagonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void downLeftDiagonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, Position.in(12, 5));
        board.addStoneAt(Stone.WHITE, Position.in(13, 4));
        Intersection intersection = board.intersectionAt(Position.in(12, 5));
        assertTrue(board.existsDiagonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void correctnessOfRegions() {

        for(int row = 2; row < Board.BOARD_SIZE -1; row++){
            for(int col = 1; col < Board.BOARD_SIZE -1; col++){
                board.addStoneAt(Stone.BLACK, Position.in(row, col));
            }
        }

        ArrayList<ArrayList<Intersection>> regions_expected = new ArrayList<>();
        ArrayList<Intersection> region = new ArrayList<>();

        for(int col = 1; col < Board.BOARD_SIZE; col++){
            Intersection intersection = new Intersection(Position.in(1, col), Stone.NONE);
            region.add(intersection);
        }

        regions_expected.add(region);
        assertEquals(regions_expected, board.findRegions());
    }

    @Test
    public void intersectionAlreadyInsideRegion() {
        ArrayList<ArrayList<Intersection>> regions = new ArrayList<>();
        ArrayList<Intersection> region = new ArrayList<>();
        Intersection intersection1 = board.intersectionAt(Position.in(1, 1));
        Intersection intersection2 = board.intersectionAt(Position.in(1, 2));
        Intersection intersection3 = board.intersectionAt(Position.in(1, 3));

        region.add(intersection1);
        region.add(intersection2);
        region.add(intersection3);
        regions.add(region);

        assertTrue(board.findIntersectionInRegions(regions, intersection2));
    }

}
