package sdmExam;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.NoSuchElementException;
import static sdmExam.Position.in;

import static org.junit.jupiter.api.Assertions.*;

public class BoardShould {
    private final Board board = new Board();

    @Test
    public void getCorrectIntersectionGivenAPosition() {
        Position position = in(5, 7);
        assertDoesNotThrow(() -> board.intersectionAt(position));
    }

    @Test
    public void intersectionOutsideBoard() {
        assertThrows(Exception.class, () -> board.intersectionAt(in(14, 14)));
    }

    @Test
    public void markCorrectlyAnIntersection() throws NoSuchElementException {
        Intersection intersection = board.intersectionAt(in(5,7));
        board.addStoneAt(Stone.BLACK, in(5,7));
        assertEquals(intersection.getStone(), Stone.BLACK);
    }

    @Test
    public void noOrthogonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, in(7, 9));
        Intersection intersection = board.intersectionAt(in(7, 9));
        assertFalse(board.existsOrthogonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void rightOrthogonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, in(3, 4));
        board.addStoneAt(Stone.WHITE, in(4, 4));
        Intersection intersection = board.intersectionAt(in(3, 4));
        assertTrue(board.existsOrthogonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void topOrthogonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, in(12, 5));
        board.addStoneAt(Stone.WHITE, in(13, 5));
        Intersection intersection = board.intersectionAt(in(12, 5));
        assertTrue(board.existsOrthogonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void noDiagonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, in(7, 9));
        Intersection intersection = board.intersectionAt(in(7, 9));
        assertFalse(board.existsDiagonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void upRightDiagonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, in(3, 4));
        board.addStoneAt(Stone.WHITE, in(2, 5));
        Intersection intersection = board.intersectionAt(in(3, 4));
        assertTrue(board.existsDiagonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void downLeftDiagonalAdjacencyOfIntersection() {
        board.addStoneAt(Stone.WHITE, in(12, 5));
        board.addStoneAt(Stone.WHITE, in(13, 4));
        Intersection intersection = board.intersectionAt(in(12, 5));
        assertTrue(board.existsDiagonallyAdjacentWithStone(intersection, Stone.WHITE));
    }

    @Test
    public void applyPieRuleCorrectly() {
        board.pie();
        assertTrue(board.getEnumSet().stream()
                .filter(edge -> edge.name().equals("LEFT"))
                .map(edge -> edge.hasColor(Stone.BLACK))
                .findFirst()
                .orElse(false));
    }

    @Test
    public void updateTheChainsCorrectly() {
        Board customBoard = Board.buildTestBoard(3);
        customBoard.addStoneAt(Stone.BLACK, in(1, 1));
        customBoard.addStoneAt(Stone.BLACK, in(1, 2));
        customBoard.addStoneAt(Stone.BLACK, in(2, 2));
        customBoard.addStoneAt(Stone.BLACK, in(3, 2));
        assertEquals(Stone.BLACK, customBoard.colorWithCompleteChain());
    }




}
