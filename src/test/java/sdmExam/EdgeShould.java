package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EdgeShould {
    private final Board board = new Board();


    @TestFactory
    Stream<DynamicTest> checkEdges() {

        Position firstPosition = Position.in(1, 4);
        Position secondPosition = Position.in(13, 4);
        Position thirdPosition = Position.in(13, 1);
        Position fourthPosition = Position.in(1, 13);

        List<Edge> edgesList = Arrays.asList(Edge.TOP,Edge.BOTTOM,Edge.LEFT,Edge.RIGHT);
        List<Position> positionList = Arrays.asList(firstPosition,secondPosition,thirdPosition,fourthPosition);

        return edgesList.stream()
                .map(edge -> DynamicTest.dynamicTest("Checking Edge "+ edge,
                        () -> {
                            int index = edgesList.indexOf(edge);
                            assertTrue(edge.isAdjacentTo(positionList.get(index)));
                        }));
    }
}
