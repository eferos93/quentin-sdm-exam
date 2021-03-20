package quentin.core;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import quentin.exceptions.OutsideOfBoardException;

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

    private static Stream<Arguments> provideIntersectionToMarkCorrectlyAnIntersection() throws OutsideOfBoardException {
        return Stream.of(
                Arguments.of(board.intersectionAt(in(5, 7)), Colour.BLACK),
                Arguments.of(board.intersectionAt(in(4, 3)), Colour.WHITE),
                Arguments.of(board.intersectionAt(in(9, 6)), Colour.WHITE)
        );
    }

    @ParameterizedTest
    @MethodSource({"provideIntersectionToMarkCorrectlyAnIntersection"})
    public void markCorrectlyAnIntersection(Intersection intersection, Colour colour) throws NoSuchElementException, OutsideOfBoardException {
        board.addStoneAt(Colour.BLACK, in(5, 7));
        board.addStoneAt(Colour.WHITE, in(4, 3));
        board.addStoneAt(Colour.WHITE, in(9, 6));
        assertTrue(intersection.getColour().map(intersectionColor -> intersectionColor == colour).orElse(false));
    }

    @Test
    public void fillTerritoryWithEqualNumberOfStoneOfTheSameColor() {
        int boardSize = 13;
        Board customBoard = Board.buildBoard(boardSize);
        IntStream.rangeClosed(1, boardSize).forEach(column -> {
                customBoard.addStoneAt(Colour.WHITE, in(7, column));
                customBoard.addStoneAt(Colour.BLACK, in(9, column));
        });
        customBoard.fillTerritories(Colour.BLACK);
        assertTrue(IntStream.rangeClosed(1, 13)
                .allMatch(column -> customBoard.intersectionAt(in(8, column)).hasStone(Colour.WHITE))
        );
    }

    @Test
    public void fillTerritoryWithDifferentNumberOfStone() {
        int boardSize = 13;
        Board customBoard = Board.buildBoard(boardSize);
        IntStream.rangeClosed(1, boardSize)
                .forEach(column -> {
                        if (column <= 6) {
                            customBoard.addStoneAt(Colour.WHITE, in(7, column));
                        } else {
                            customBoard.addStoneAt(Colour.BLACK, in(7, column));
                        }
                });

        IntStream.rangeClosed(1, boardSize)
                .forEach(column -> {
                        if (column <= 4) {
                            customBoard.addStoneAt(Colour.WHITE, in(9, column));
                        } else {
                            customBoard.addStoneAt(Colour.BLACK, in(9, column));
                        }
                });

        customBoard.fillTerritories(Colour.BLACK);
        assertTrue(IntStream.rangeClosed(1, boardSize)
                .allMatch(column -> customBoard.intersectionAt(in(8, column)).hasStone(Colour.BLACK))
        );
    }

    @Test
    public void updateTheChainsCorrectly() throws OutsideOfBoardException {
        Board customBoard = Board.buildBoard(3);
        customBoard.addStoneAt(Colour.BLACK, in(1, 1));
        customBoard.addStoneAt(Colour.BLACK, in(1, 2));
        customBoard.addStoneAt(Colour.BLACK, in(2, 2));
        customBoard.addStoneAt(Colour.BLACK, in(3, 2));
        assertTrue(customBoard.colorWithCompleteChain().map(chainColor -> chainColor == Colour.BLACK).orElse(false));
    }

    @TestFactory
    Collection<DynamicTest> provideCorrectColourAlikeDiagonallyAdjacentStones(){
        Board customBoard = Board.buildBoard(4);
        customBoard.addStoneAt(Colour.BLACK, in(2, 2));
        customBoard.addStoneAt(Colour.BLACK, in(1, 1));
        customBoard.addStoneAt(Colour.BLACK, in(3, 3));
        customBoard.addStoneAt(Colour.WHITE, in(1, 3));
        customBoard.addStoneAt(Colour.WHITE, in(3, 1));

        List<Intersection> blackIntersections = List.of(
                customBoard.intersectionAt(in(1, 1)),
                customBoard.intersectionAt(in(3, 3))
        );

        List<Intersection> whiteIntersections = List.of(
                customBoard.intersectionAt(in(1, 3)),
                customBoard.intersectionAt(in(3, 1))
        );

        Set<Intersection> colourAlikeDiagonallyAdjacentIntersections =
                customBoard.getDiagonallyAdjacentIntersectionsOfColour(customBoard.intersectionAt(in(2, 2)));

        return List.of(
                DynamicTest.dynamicTest("Black Diagonal Intersections",
                        () -> assertTrue(colourAlikeDiagonallyAdjacentIntersections.containsAll(blackIntersections))),
                DynamicTest.dynamicTest("White Diagonal Intersections",
                        () -> assertFalse(colourAlikeDiagonallyAdjacentIntersections.containsAll(whiteIntersections)))
        );
    }

    @TestFactory
    Collection<DynamicTest> provideCorrectColourAlikeOrthogonallyAdjacentStones() {
        Board customBoard = Board.buildBoard(4);
        customBoard.addStoneAt(Colour.WHITE, in(2, 2));
        customBoard.addStoneAt(Colour.BLACK, in(1, 2));
        customBoard.addStoneAt(Colour.BLACK, in(3, 2));
        customBoard.addStoneAt(Colour.WHITE, in(2, 1));
        customBoard.addStoneAt(Colour.WHITE, in (2, 3));

        List<Intersection> blackIntersections = List.of(
                customBoard.intersectionAt(in(1, 2)),
                customBoard.intersectionAt(in(3, 2))
        );
        List<Intersection> whiteIntersections = List.of(
                customBoard.intersectionAt(in(2, 1)),
                customBoard.intersectionAt(in(2, 3))
        );

        Set<Intersection> colourAlikeOrthogonallyAdjacentIntersections =
                customBoard.getOrthogonallyAdjacentIntersectionsOfColour(
                        customBoard.intersectionAt(in(2, 2))
                );

        return List.of(
                DynamicTest.dynamicTest("Black Diagonal Intersections",
                        () -> assertFalse(colourAlikeOrthogonallyAdjacentIntersections.containsAll(blackIntersections))),
                DynamicTest.dynamicTest("White Diagonal Intersections",
                        () -> assertTrue(colourAlikeOrthogonallyAdjacentIntersections.containsAll(whiteIntersections)))
        );

    }
}
