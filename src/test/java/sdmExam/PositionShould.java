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
    public void stoneAbove() {
        Position firstPosition = new Position(1, 2);
        Position secondPosition = new Position(2, 2);
        assertTrue(firstPosition.isAboveWithRespectTo(secondPosition));
    }

    @Test
    public void stoneBelow() {
        Position firstPosition = new Position(3, 4);
        Position secondPosition = new Position(2, 2);
        assertFalse(firstPosition.isBelowWithRespectTo(secondPosition));
    }

    @Test
    public void stoneLeft() {
        Position firstPosition = new Position(3, 4);
        Position secondPosition = new Position(3, 5);
        assertTrue(firstPosition.isOnTheLeftWithRespectTo(secondPosition));
    }

    @Test
    public void stoneUpRight() {
        Position firstPosition = new Position(5, 6);
        Position secondPosition = new Position(6, 5);
        assertTrue(firstPosition.isUpRightRespectTo(secondPosition));
    }

    @Test
    public void stoneDownLeft() {
        Position firstPosition = new Position(7, 12);
        Position secondPosition = new Position(6, 13);
        assertTrue(firstPosition.isDownLeftRespectTo(secondPosition));
    }


}
