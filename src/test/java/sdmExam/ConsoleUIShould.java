package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import sdmExam.UI.PrintBoard;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleUIShould {

    @TestFactory
    Stream<DynamicTest> printCorrectlyIntersection() {

        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        List<Intersection> intersections = List.of(
                new Intersection(Position.in(1, 1), Stone.BLACK),
                new Intersection(Position.in(1, 2), Stone.WHITE),
                Intersection.empty(Position.in(2, 2))
        );

        List<String> expectedOutput = List.of("[B]", "[W]", "[ ]");

        return intersections.stream().map(intersection ->
                DynamicTest.dynamicTest("Checking " + intersection,
                () -> {
                    int index = intersections.indexOf(intersection);
                    PrintBoard.displayStone(intersection.getStone());
                    assertEquals(expectedOutput.get(index), fakeStandardOutput.toString());
                    fakeStandardOutput.reset();
                })
        );
    }

    @Test
    public void printCorrectlyLine() {
        Board board = Board.buildTestBoard(5);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        int rowIndex = 2;
        String expectedOutput = rowIndex + "\tW[ ][ ][ ][ ][ ]W" + System.lineSeparator();
        PrintBoard.printRow(board, rowIndex);

        assertEquals(expectedOutput, fakeStandardOutput.toString());
    }
}
