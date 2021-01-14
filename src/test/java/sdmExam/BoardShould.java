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

    //TODO Below tests can be parameterized
    @Test
    public void getCorrectIntersectionGivenAPosition() {
        Position position = Position.in(5, 7);
        assertDoesNotThrow(() -> board.intersectionAt(position));
    }

    @Test
    public void intersectionOutsideBoard() {
        assertThrows(Exception.class, () -> board.intersectionAt(Position.in(14, 14)));
    }

    @ParameterizedTest
    @MethodSource({"provideIntersectionAddStoneAt"})
    public void markCorrectlyAnIntersection(Intersection input, Stone expected) throws NoSuchElementException {
        board.addStoneAt(Stone.BLACK, Position.in(5,7));
        board.addStoneAt(Stone.WHITE, Position.in(4,3));
        board.addStoneAt(Stone.WHITE, Position.in(9,6));
        assertEquals(input.getStone(), expected);
    }


    public static Stream<Arguments> provideIntersectionAddStoneAt(){
        return Stream.of(
                Arguments.of(board.intersectionAt(Position.in(5,7)),Stone.BLACK),
                Arguments.of(board.intersectionAt(Position.in(4,3)),Stone.WHITE),
                Arguments.of(board.intersectionAt(Position.in(9,6)),Stone.WHITE)
        );
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
