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
        ArrayList<Intersection> region1 = new ArrayList<>();
        ArrayList<Intersection> region2 = new ArrayList<>();
        Intersection intersection1 = board.intersectionAt(Position.in(1, 1));
        Intersection intersection2 = board.intersectionAt(Position.in(1, 2));
        Intersection intersection3 = board.intersectionAt(Position.in(1, 3));
        Intersection intersection4 = board.intersectionAt(Position.in(2, 1));
        Intersection intersection5 = board.intersectionAt(Position.in(2, 2));
        Intersection intersection6 = board.intersectionAt(Position.in(2, 3));

        region1.add(intersection1);
        region1.add(intersection2);
        region1.add(intersection3);
        region2.add(intersection4);
        region2.add(intersection5);
        region2.add(intersection6);
        regions.add(region1);
        regions.add(region2);

        assertFalse(board.findIntersectionInRegions(regions, intersection2));
    }

    @Test
    public void intersectionNotAlreadyInsideRegion() {
        ArrayList<ArrayList<Intersection>> regions = new ArrayList<>();
        ArrayList<Intersection> region1 = new ArrayList<>();
        ArrayList<Intersection> region2 = new ArrayList<>();
        Intersection intersection1 = board.intersectionAt(Position.in(1, 1));
        Intersection intersection2 = board.intersectionAt(Position.in(1, 2));
        Intersection intersection3 = board.intersectionAt(Position.in(1, 3));
        Intersection intersection4 = board.intersectionAt(Position.in(2, 1));
        Intersection intersection5 = board.intersectionAt(Position.in(2, 2));
        Intersection intersection6 = board.intersectionAt(Position.in(2, 3));

        region1.add(intersection1);
        region1.add(intersection2);
        region1.add(intersection3);
        region2.add(intersection4);
        region2.add(intersection6);
        regions.add(region1);
        regions.add(region2);

        assertTrue(board.findIntersectionInRegions(regions, intersection5));
    }

    @Test
    public void extendRegion() {
        for(int row = 2; row < Board.BOARD_SIZE -1; row++){
            for(int col = 1; col < Board.BOARD_SIZE -1; col++){
                board.addStoneAt(Stone.BLACK, Position.in(row, col));
            }
        }

        ArrayList<Intersection> region1 = new ArrayList<>();
        ArrayList<Intersection> region2 = new ArrayList<>();

        for(int col = 1; col < Board.BOARD_SIZE; col++){
            Intersection intersection = new Intersection(Position.in(1, col), Stone.NONE);
            region1.add(intersection);
        }

        board.DFS(0, region2);
        assertEquals(region1, region2);
    }

}
