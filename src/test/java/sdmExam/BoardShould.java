package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BoardShould {
    private final static Board board = new Board();

    @ParameterizedTest
    @MethodSource({"providePositionForGetCorrectIntersectionGivenAPosition"})
    public void getCorrectIntersectionGivenAPosition(Position position) {
        assertDoesNotThrow(() -> board.intersectionAt(position));
    }

    private static Stream<Arguments> providePositionForGetCorrectIntersectionGivenAPosition(){
        return Stream.of(
                Arguments.of(Position.in(1,1)),
                Arguments.of(Position.in(7,2)),
                Arguments.of(Position.in(13,13))
        );
    }

    @ParameterizedTest
    @MethodSource({"providePositionForIntersectionOutsideBoard"})
    public void intersectionOutsideBoard(Position position) {
        assertThrows(Exception.class, () -> board.intersectionAt(position));
    }

    private static Stream<Arguments> providePositionForIntersectionOutsideBoard(){
        return Stream.of(
                Arguments.of(Position.in(14,14)),
                Arguments.of(Position.in(0,0)),
                Arguments.of(Position.in(-1,16))
        );
    }

    @ParameterizedTest
    @MethodSource({"provideIntersectionForMarkCorrectlyAnIntersection"})
    public void markCorrectlyAnIntersection(Intersection intersection, Stone stone) throws NoSuchElementException {
        board.addStoneAt(Stone.BLACK, Position.in(5,7));
        board.addStoneAt(Stone.WHITE, Position.in(4,3));
        board.addStoneAt(Stone.WHITE, Position.in(9,6));
        assertEquals(intersection.getStone(), stone);
    }

    private static Stream<Arguments> provideIntersectionForMarkCorrectlyAnIntersection(){
        return Stream.of(
                Arguments.of(board.intersectionAt(Position.in(5,7)),Stone.BLACK),
                Arguments.of(board.intersectionAt(Position.in(4,3)),Stone.WHITE),
                Arguments.of(board.intersectionAt(Position.in(9,6)),Stone.WHITE)
        );
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
                            int index = inputList.indexOf(intersection);
                            assertEquals(outputList.get(index),board.existsOrthogonallyAdjacentWithStone(intersection,Stone.WHITE));
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
                        int index = inputList.indexOf(intersection);
                        assertEquals(outputList.get(index),board.existsDiagonallyAdjacentWithStone(intersection,Stone.WHITE));
                        }));
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
}
