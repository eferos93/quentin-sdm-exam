package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionShould {
    @Test
    public void beEqualToAnotherPositionWithSameCoordinates() {
        Position firstPosition = new Position(3, 3);
        Position secondPosition = new Position(3, 3);
        assertEquals(firstPosition, secondPosition);
    }
}
