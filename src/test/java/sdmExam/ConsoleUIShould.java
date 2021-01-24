package sdmExam;

import org.junit.jupiter.api.Test;
import sdmExam.UI.PrintBoard;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleUIShould {

    @Test
    public void printCorrectlyIntersection() {
        Intersection intersection = new Intersection(Position.in(1, 1), Stone.BLACK);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        PrintBoard.displayStone(intersection.getStone());
        assertEquals("[B]", fakeStandardOutput.toString());
    }
}
