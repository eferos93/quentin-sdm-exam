package sdmExam;

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
import static sdmExam.Position.in;

public class BoardShould {
    private final static Board board = new Board();

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
        board.addStoneAt(Stone.WHITE, in(7, 9));
        board.addStoneAt(Stone.WHITE, in(3, 4));
        board.addStoneAt(Stone.WHITE, in(4, 4));
        board.addStoneAt(Stone.WHITE, in(12, 5));
        board.addStoneAt(Stone.WHITE, in(13, 5));

        Intersection firstIntersection = board.intersectionAt(in(7, 9));
        Intersection secondIntersection = board.intersectionAt(in(3, 4));
        Intersection thirdIntersection = board.intersectionAt(in(12, 5));

        List<Intersection> inputList = Arrays.asList(firstIntersection, secondIntersection, thirdIntersection);
        List<Boolean> outputList = Arrays.asList(false, true, true);

        return inputList.stream()
                .map(intersection -> DynamicTest.dynamicTest("Checking Orthogonal Adjacent of " + intersection,
                        () -> {
                            int index = inputList.indexOf(intersection);
                            assertEquals(outputList.get(index), board.existsOrthogonallyAdjacentWithStone(intersection, Stone.WHITE));
                        })
                );
    }


    @TestFactory
    Stream<DynamicTest> checkDiagonalAdjacent() {
        board.addStoneAt(Stone.WHITE, in(7, 9));
        board.addStoneAt(Stone.WHITE, in(3, 4));
        board.addStoneAt(Stone.WHITE, in(2, 5));
        board.addStoneAt(Stone.WHITE, in(12, 5));
        board.addStoneAt(Stone.WHITE, in(13, 4));

        Intersection firstIntersection = board.intersectionAt(in(7, 9));
        Intersection secondIntersection = board.intersectionAt(in(3, 4));
        Intersection thirdIntersection = board.intersectionAt(in(12, 5));

        List<Intersection> inputList = Arrays.asList(firstIntersection, secondIntersection, thirdIntersection);
        List<Boolean> outputList = Arrays.asList(false, true, true);

        return inputList.stream()
                .map(intersection -> DynamicTest.dynamicTest("Checking Diagonal Adjacent of " + intersection,
                        () -> {
                            int index = inputList.indexOf(intersection);
                            assertEquals(outputList.get(index), board.existsDiagonallyAdjacentWithStone(intersection, Stone.WHITE));
                        })
                );
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

    @Test
    public void getCorrectTerritories() {
        Board customBoard = Board.buildTestBoard(4);
        customBoard.addStoneAt(Stone.BLACK, in(1, 2));
        customBoard.addStoneAt(Stone.WHITE, in(2, 1));
        List<Set<Intersection>> territories = customBoard.getTerritories();
        assertEquals(1, territories.size());
        assertTrue(territories.stream()
                .anyMatch(territory -> territory.stream()
                        .allMatch(intersection -> intersection.getPosition().equals(in(1, 1)))
                )
        );
    }

    @Test
    public void provideTerritories() {
        int boardSize = 9;
        Board customBoard = Board.buildTestBoard(boardSize);
        Set<Intersection> expectedTerritory = new HashSet<>();

        IntStream.rangeClosed(1, boardSize)
                .forEach(column -> {
                    customBoard.addStoneAt(Stone.WHITE, in(6, column));
                    customBoard.addStoneAt(Stone.WHITE, in(8, column));
                    expectedTerritory.add(new Intersection(in(7, column), Stone.NONE));
                });

        List<Set<Intersection>> territories = customBoard.getTerritories();
        assertTrue(territories.stream()
                .allMatch(expectedTerritory::containsAll)
        );
    }

    @Test
    public void fillTerritoryWithEqualNumberOfStoneOfTheSameColor() {
        int boardSize = 13;
        Board customBoard = Board.buildTestBoard(boardSize);
        IntStream.rangeClosed(1, 13).forEach(column -> {
            customBoard.addStoneAt(Stone.WHITE, in(7, column));
            customBoard.addStoneAt(Stone.BLACK, in(9, column));
        });
        customBoard.fillTerritory(customBoard.getTerritories().get(0), Stone.BLACK);
        assertTrue(IntStream.rangeClosed(1, 13)
                .allMatch(column -> customBoard.intersectionAt(in(8, column))
                        .hasStone(Stone.WHITE)
                )
        );
    }

    @Test
    public void fillTerritoryWithDifferentNumberOfStone() {
        int boardSize = 13;
        Board customBoard = Board.buildTestBoard(boardSize);
        IntStream.rangeClosed(1, 6)
                .forEach(column -> customBoard.addStoneAt(Stone.WHITE, in(7, column)));
        IntStream.rangeClosed(7, boardSize)
                .forEach(column -> customBoard.addStoneAt(Stone.BLACK, in(7, column)));
        IntStream.rangeClosed(1, 4)
                .forEach(column -> customBoard.addStoneAt(Stone.WHITE, in(9, column)));
        IntStream.rangeClosed(5, boardSize)
                .forEach(column -> customBoard.addStoneAt(Stone.BLACK, in(9, column)));

        Stone lastPlay = Stone.BLACK;

        customBoard.fillTerritory(customBoard.getTerritories().get(0), lastPlay);
        assertTrue(IntStream.rangeClosed(1, boardSize)
                .allMatch(column -> customBoard.intersectionAt(in(8, column))
                        .hasStone(Stone.BLACK)
                )
        );
    }
}
