package quentin.UI.console;

import org.junit.jupiter.api.Test;
import quentin.UI.console.ConsoleOutputHandler;
import quentin.core.Board;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleUIShould {

    @Test
    public void printCorrectlyBoard() {
        int boardSize = 4;
        Board board = Board.buildBoard(boardSize);
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
        ConsoleOutputHandler.printBoard(board);
        assertEquals(expectedOutput, fakeStandardOutput.toString());
    }
}
