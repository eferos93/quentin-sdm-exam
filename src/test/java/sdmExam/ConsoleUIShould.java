package sdmExam;

import org.junit.jupiter.api.Test;
import sdmExam.UI.BoardPrinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleUIShould {

    @Test
    public void printCorrectlyBoard() {
        int boardSize = 5;
        Board board = Board.buildTestBoard(boardSize);

        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        StringBuilder expectedOutput = new StringBuilder("    " + "  B".repeat(boardSize) + System.lineSeparator());
        for(int rowIndex = 1; rowIndex <= boardSize; rowIndex++)
            expectedOutput.append(rowIndex)
                          .append("\tW").append("[ ]".repeat(boardSize))
                          .append("W").append(System.lineSeparator());

        expectedOutput.append("    ").append("  B".repeat(boardSize))
                      .append(System.lineSeparator()).append("\t ");

        for(int rowIndex = 1; rowIndex <= boardSize; rowIndex++)
            expectedOutput.append(String.format("%" + 3 + "s", rowIndex + " "));

        expectedOutput.append(System.lineSeparator());

        BoardPrinter.printBoard(board);

        assertEquals(expectedOutput.toString(), fakeStandardOutput.toString());
    }
}
