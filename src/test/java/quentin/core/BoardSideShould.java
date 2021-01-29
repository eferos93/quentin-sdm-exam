package quentin.core;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardSideShould {
    @TestFactory
    Stream<DynamicTest> checkAdjacentPositionToSides() {
        List<Position> positionList = List.of(
                Position.in(1, 4),
                Position.in(13, 4),
                Position.in(13, 1),
                Position.in(1, 13)
        );
        List<BoardSide> sideList = List.of(BoardSide.TOP, BoardSide.BOTTOM, BoardSide.LEFT, BoardSide.RIGHT);
        BoardSide.setBoardSize(13);
        sideList.forEach(BoardSide::initialiseSide);

        return sideList.stream()
                .map(side -> DynamicTest.dynamicTest("Checking Side " + side,
                        () -> {
                            int index = sideList.indexOf(side);
                            assertTrue(side.isAdjacentTo(positionList.get(index)));
                        })
                );
    }
}
