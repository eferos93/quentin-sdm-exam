package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EdgeShould {
    private Board board = new Board();

    @TestFactory
    Collection<DynamicTest> checkEdges() {

        Edge edgeTop = Edge.TOP;
        Edge edgeBottom = Edge.BOTTOM;
        Edge edgeLeft = Edge.LEFT;
        Edge edgeRight = Edge.RIGHT;

        return Arrays.asList(
                DynamicTest.dynamicTest("Return True If Above the Given Position",
                        () -> assertTrue(edgeTop.isAdjacentTo(Position.in(1, 4)))),
                DynamicTest.dynamicTest("Return True If Below the Given Position",
                        () -> assertTrue(edgeBottom.isAdjacentTo(Position.in(13, 4)))),
                DynamicTest.dynamicTest("Return True If Left the Given Position",
                        () -> assertTrue(edgeLeft.isAdjacentTo(Position.in(13, 1)))),
                DynamicTest.dynamicTest("Return True If Right the Given Position",
                        () -> assertTrue(edgeRight.isAdjacentTo(Position.in(1, 13))))
        );
    }
}
