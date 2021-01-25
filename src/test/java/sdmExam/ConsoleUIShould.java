package sdmExam;

import org.junit.jupiter.api.Test;
import sdmExam.UI.BoardPrinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleUIShould {

    @Test
    public void printCorrectlyBoard() {
        int boardSize = 4;
        Board board = Board.buildTestBoard(boardSize);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        String expectedOutput = """
                      B  B  B  B
                1\tW[ ][ ][ ][ ]W
                2\tW[ ][ ][ ][ ]W
                3\tW[ ][ ][ ][ ]W
                4\tW[ ][ ][ ][ ]W
                      B  B  B  B
                \t  1  2  3  4
                """;
        BoardPrinter.printBoard(board);

        assertEquals(expectedOutput, fakeStandardOutput.toString());
    }
}
