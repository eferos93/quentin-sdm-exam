package quentin.UI.console;

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
        String expectedOutput = """
                      B  B  B  B
                1   W[ ][ ][ ][ ]W
                2   W[ ][ ][ ][ ]W
                3   W[ ][ ][ ][ ]W
                4   W[ ][ ][ ][ ]W
                      B  B  B  B
                      1  2  3  4
                """;
        outputHandler.displayBoard(board);
        assertEquals(expectedOutput, fakeStandardOutput.toString());
    }
}
