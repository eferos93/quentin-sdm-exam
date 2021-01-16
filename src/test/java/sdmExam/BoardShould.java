package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;
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

    @Test
    public void ModifyGridGraphAfterAddStone() {

        List<Intersection> expected_intersections = new ArrayList<>();

        // TODO: less hardcoded
        for (int row = 1; row <= 13; row++) {
            for (int column = 1; column <= 13; column++) {
                if(row != 1 || column > 4)
                    expected_intersections.add(Intersection.empty(Position.in(row, column)));
            }
        }

        board.addStoneAt(Stone.BLACK, Position.in(1, 1));
        board.addStoneAt(Stone.BLACK, Position.in(1, 2));
        board.addStoneAt(Stone.BLACK, Position.in(1, 3));
        board.addStoneAt(Stone.BLACK, Position.in(1, 4));

        List<Intersection> board_intersections =
                new ArrayList<>(board.getRegion().getGraph().vertexSet());

        assertEquals(expected_intersections, board_intersections);
    }

    @Test
    public void provideTerritories() {
        List<List<Intersection>> expected_territories = new ArrayList<>();
        List<Intersection> territory = new ArrayList<>();

        // TODO: refactor to avoid hardcoded
        for(int row = 1; row <= 13; row++){
            board.addStoneAt(Stone.WHITE, Position.in(row, 6));
            board.addStoneAt(Stone.WHITE, Position.in(row, 8));
            territory.add(board.intersectionAt(Position.in(row, 7)));
        }
        expected_territories.add(territory);

        // TODO: refactor assertion, need to perform it with list of lists
        assertTrue(expected_territories.get(0).containsAll(board.getTerritories().get(0)) &&
                board.getTerritories().get(0).containsAll(expected_territories.get(0)));
    }
}
