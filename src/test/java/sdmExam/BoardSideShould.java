package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import sdmExam.core.Board;
import sdmExam.core.BoardSide;
import sdmExam.core.Position;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardSideShould {
    private final Board board = new Board();


    @TestFactory
    Stream<DynamicTest> checkAdjacentPositionToSides() {

        Position firstPosition = Position.in(1, 4);
        Position secondPosition = Position.in(13, 4);
        Position thirdPosition = Position.in(13, 1);
        Position fourthPosition = Position.in(1, 13);

        List<BoardSide> sideList = List.of(BoardSide.TOP, BoardSide.BOTTOM, BoardSide.LEFT, BoardSide.RIGHT);
        List<Position> positionList = List.of(firstPosition, secondPosition, thirdPosition, fourthPosition);

        return sideList.stream()
                .map(side -> DynamicTest.dynamicTest("Checking Side " + side,
                        () -> {
                            int index = sideList.indexOf(side);
                            assertTrue(side.isAdjacentTo(positionList.get(index)));
                        })
                );
    }
}
