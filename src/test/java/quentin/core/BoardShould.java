package quentin.core;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static quentin.core.Position.in;

public class BoardShould {
    private final static Board board = Board.buildBoard(13);

    private static Stream<Arguments> providePositionToGetCorrectIntersectionGivenAPosition() {
        return Stream.of(
                Arguments.of(in(1, 1)),
                Arguments.of(in(7, 2)),
                Arguments.of(in(13, 13))
        );
    }

    @ParameterizedTest
    @MethodSource({"providePositionToGetCorrectIntersectionGivenAPosition"})
    public void getCorrectIntersectionGivenAPosition(Position position) {
        assertDoesNotThrow(() -> board.intersectionAt(position));
    }

    private static Stream<Arguments> providePositionForIntersectionOutsideBoard() {
        return Stream.of(
                Arguments.of(in(14, 14)),
                Arguments.of(in(0, 0)),
                Arguments.of(in(-1, 16))
        );
    }

    @ParameterizedTest
    @MethodSource({"providePositionForIntersectionOutsideBoard"})
    public void intersectionOutsideBoard(Position position) {
        assertThrows(Exception.class, () -> board.intersectionAt(position));
    }

    private static Stream<Arguments> provideIntersectionToMarkCorrectlyAnIntersection() {
        return Stream.of(
                Arguments.of(board.intersectionAt(in(5, 7)), Stone.BLACK),
                Arguments.of(board.intersectionAt(in(4, 3)), Stone.WHITE),
                Arguments.of(board.intersectionAt(in(9, 6)), Stone.WHITE)
        );
    }

    @ParameterizedTest
    @MethodSource({"provideIntersectionToMarkCorrectlyAnIntersection"})
    public void markCorrectlyAnIntersection(Intersection intersection, Stone stone) throws NoSuchElementException {
        board.addStoneAt(Stone.BLACK, in(5, 7));
        board.addStoneAt(Stone.WHITE, in(4, 3));
        board.addStoneAt(Stone.WHITE, in(9, 6));
        assertEquals(intersection.getStone(), stone);
    }

    @TestFactory
    Stream<DynamicTest> checkOrthogonalAdjacent() {
        Board customBoard = Board.buildBoard(13);
        customBoard.addStoneAt(Stone.WHITE, in(7, 9));
        customBoard.addStoneAt(Stone.WHITE, in(3, 4));
        customBoard.addStoneAt(Stone.WHITE, in(4, 4));
        customBoard.addStoneAt(Stone.WHITE, in(12, 5));
        customBoard.addStoneAt(Stone.WHITE, in(13, 5));

        List<Intersection> inputList = List.of(
                customBoard.intersectionAt(in(7, 9)),
                customBoard.intersectionAt(in(3, 4)),
                customBoard.intersectionAt(in(12, 5))
        );

        List<Boolean> outputList = List.of(false, true, true);

        return inputList.stream()
                .map(intersection -> DynamicTest.dynamicTest("Checking Orthogonal Adjacent of " + intersection,
                        () -> {
                            int index = inputList.indexOf(intersection);
                            assertEquals(outputList.get(index), customBoard.existsOrthogonallyAdjacentWithStone(intersection, Stone.WHITE));
                        })
                );
    }


    @TestFactory
    Stream<DynamicTest> checkDiagonalAdjacent() {
        Board customBoard = Board.buildBoard(13);
        customBoard.addStoneAt(Stone.WHITE, in(7, 9));
        customBoard.addStoneAt(Stone.WHITE, in(3, 4));
        customBoard.addStoneAt(Stone.WHITE, in(2, 5));
        customBoard.addStoneAt(Stone.WHITE, in(12, 5));
        customBoard.addStoneAt(Stone.WHITE, in(13, 4));

        List<Intersection> inputList = List.of(
                customBoard.intersectionAt(in(7, 9)),
                customBoard.intersectionAt(in(3, 4)),
                customBoard.intersectionAt(in(12, 5))
        );

        List<Boolean> outputList = List.of(false, true, true);

        return inputList.stream()
                .map(intersection -> DynamicTest.dynamicTest("Checking Diagonal Adjacent of " + intersection,
                        () -> {
                            int index = inputList.indexOf(intersection);
                            assertEquals(outputList.get(index), customBoard.existsDiagonallyAdjacentWithStone(intersection, Stone.WHITE));
                        })
                );
    }

    @Test
    public void fillTerritoryWithEqualNumberOfStoneOfTheSameColor() {
        int boardSize = 13;
        Board customBoard = Board.buildBoard(boardSize);
        IntStream.rangeClosed(1, boardSize).forEach(column -> {
            customBoard.addStoneAt(Stone.WHITE, in(7, column));
            customBoard.addStoneAt(Stone.BLACK, in(9, column));
        });
        customBoard.fillTerritories(Stone.BLACK);
        assertTrue(IntStream.rangeClosed(1, 13)
                .allMatch(column -> customBoard.intersectionAt(in(8, column))
                        .hasStone(Stone.WHITE)
                )
        );
    }

    @Test
    public void fillTerritoryWithDifferentNumberOfStone() {
        int boardSize = 13;
        Board customBoard = Board.buildBoard(boardSize);
        IntStream.rangeClosed(1, boardSize)
                .forEach(column -> {
                    if (column <= 6) { customBoard.addStoneAt(Stone.WHITE, in(7, column)); }
                    else { customBoard.addStoneAt(Stone.BLACK, in(7, column)); }
                });

        IntStream.rangeClosed(1, boardSize)
                .forEach(column -> {
                    if (column <= 4) { customBoard.addStoneAt(Stone.WHITE, in(9, column)); }
                    else { customBoard.addStoneAt(Stone.BLACK, in(9, column)); }
                });

        customBoard.fillTerritories(Stone.BLACK);
        assertTrue(IntStream.rangeClosed(1, boardSize)
                .allMatch(column -> customBoard.intersectionAt(in(8, column))
                        .hasStone(Stone.BLACK)
                )
        );
    }

    @Test
    public void updateTheChainsCorrectly() {
        Board customBoard = Board.buildBoard(3);
        customBoard.addStoneAt(Stone.BLACK, in(1, 1));
        customBoard.addStoneAt(Stone.BLACK, in(1, 2));
        customBoard.addStoneAt(Stone.BLACK, in(2, 2));
        customBoard.addStoneAt(Stone.BLACK, in(3, 2));
        assertEquals(Stone.BLACK, customBoard.colorWithCompleteChain());
    }
}
