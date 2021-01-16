package sdmExam;


import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void verifyOrthogonalAdjacencyList() {
        ArrayList<Intersection> expected_list = new ArrayList<>();

        Intersection intersection1 = board.intersectionAt(Position.in(1, 1));
        Intersection intersection2 = board.intersectionAt(Position.in(1, 2));
        Intersection intersection3 = board.intersectionAt(Position.in(2, 1));

        expected_list.add(intersection2);
        expected_list.add(intersection3);

        assertEquals(expected_list, board.getOrthogonalAdjacencyIntersections(intersection1));
    }

    @Test
    public void verifyColoredOrthogonalAdjacencyList() {
        List<Optional<Intersection>> expected_list;
        ArrayList<Intersection> list = new ArrayList<>();
        ArrayList<Intersection> intersections = new ArrayList<>();

        // TODO: make it general
        Intersection intersection1 = board.intersectionAt(Position.in(1, 1));
        Intersection intersection2 = board.intersectionAt(Position.in(1, 2));
        Intersection intersection3 = board.intersectionAt(Position.in(2, 1));

        intersection1.setStone(Stone.NONE);
        intersection2.setStone(Stone.BLACK);
        intersection3.setStone(Stone.WHITE);

        list.add(intersection2);
        list.add(intersection3);

        // Board::getColoredIntersections returns a List<Optional<Intersection>>
        expected_list = list.stream().map(Optional::of).collect(Collectors.toList());

        intersections.add(intersection1);
        intersections.add(intersection2);
        intersections.add(intersection3);

        assertEquals(expected_list, board.getColoredIntersections(intersections));
    }
}