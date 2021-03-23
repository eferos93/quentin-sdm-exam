package quentin.ui.console;

import org.junit.jupiter.api.Test;
import quentin.core.Board;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleUIShould {

    @Test
    public void printCorrectlyBoard() {
        int boardSize = 4;
        Board board = Board.buildBoard(boardSize);
        ConsoleOutputHandler outputHandler = new ConsoleOutputHandler();
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        String expectedOutput = String.format("      B  B  B  B%s" +
                        "1   W[ ][ ][ ][ ]W%s" +
                        "2   W[ ][ ][ ][ ]W%s" +
                        "3   W[ ][ ][ ][ ]W%s" +
                        "4   W[ ][ ][ ][ ]W%s" +
                        "      B  B  B  B%s" +
                        "      1  2  3  4%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        outputHandler.displayBoard(board);
        assertEquals(expectedOutput, fakeStandardOutput.toString());
    }
}
