package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionShould {
    @Test
    public void beEqualToAnotherPositionWithSameCoordinates() {
        Position firstPosition = new Position(3, 3);
        Position secondPosition = new Position(3, 3);
        assertEquals(firstPosition, secondPosition);
    }
    @Test
    public void stoneAbove(){
        Position firstPosition = new Position(1, 2);
        Position secondPosition = new Position (2,2);
        assertTrue(firstPosition.isAboveWithRespectTo(secondPosition));
    }

    @Test
    public void stoneBelow(){
        Position firstPosition = new Position(3, 4);
        Position secondPosition = new Position (2,2);
        assertFalse(firstPosition.isBelowWithRespectTo(secondPosition));
    }



}
