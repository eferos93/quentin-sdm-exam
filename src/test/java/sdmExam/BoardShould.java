package sdmExam;


import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;


import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

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

    @TestFactory
    Stream<DynamicTest> checkOrthogonalAdjacent() {
        board.addStoneAt(Stone.WHITE, Position.in(7, 9));
        board.addStoneAt(Stone.WHITE, Position.in(3, 4));
        board.addStoneAt(Stone.WHITE, Position.in(4, 4));
        board.addStoneAt(Stone.WHITE, Position.in(12, 5));
        board.addStoneAt(Stone.WHITE, Position.in(13, 5));

        Intersection firstIntersection = board.intersectionAt(Position.in(7, 9));
        Intersection secondIntersection = board.intersectionAt(Position.in(3, 4));
        Intersection thirdIntersection = board.intersectionAt(Position.in(12, 5));

        List<Intersection> inputList = Arrays.asList(firstIntersection, secondIntersection, thirdIntersection);
        List<Boolean> outputList = Arrays.asList(false, true, true);

        return inputList.stream()
                .map(intersection -> DynamicTest.dynamicTest("Checking Orthogonal Adjacent of "+ intersection,
                        () -> {
                            int id = inputList.indexOf(intersection);
                            assertEquals(outputList.get(id),board.existsOrthogonallyAdjacentWithStone(intersection,Stone.WHITE));
                        }));
    }


    @TestFactory
    Stream<DynamicTest> checkDiagonalAdjacent() {
        board.addStoneAt(Stone.WHITE, Position.in(7, 9));
        board.addStoneAt(Stone.WHITE, Position.in(3, 4));
        board.addStoneAt(Stone.WHITE, Position.in(2, 5));
        board.addStoneAt(Stone.WHITE, Position.in(12, 5));
        board.addStoneAt(Stone.WHITE, Position.in(13, 4));

        Intersection firstIntersection = board.intersectionAt(Position.in(7, 9));
        Intersection secondIntersection = board.intersectionAt(Position.in(3, 4));
        Intersection thirdIntersection = board.intersectionAt(Position.in(12, 5));

        List<Intersection> inputList = Arrays.asList(firstIntersection, secondIntersection, thirdIntersection);
        List<Boolean> outputList = Arrays.asList(false, true, true);

        return inputList.stream()
                .map(intersection -> DynamicTest.dynamicTest("Checking Diagonal Adjacent of "+ intersection,
                        () -> {
                        int id = inputList.indexOf(intersection);
                        assertEquals(outputList.get(id),board.existsDiagonallyAdjacentWithStone(intersection,Stone.WHITE));
                        }));
    }

}
