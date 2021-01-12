package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;

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

    @TestFactory
    Collection<DynamicTest> checkPositions() {
        Position firstPosition = new Position(1, 2);
        Position secondPosition = new Position(3, 4);
        Position thirdPosition = new Position(3, 4);
        Position fourthPosition = new Position(5, 6);
        Position fifthPosition = new Position(7, 12);

        return Arrays.asList(
                DynamicTest.dynamicTest("Above Position",
                        () -> assertTrue(firstPosition.isAboveWithRespectTo(Position.in(2,2)))),
                DynamicTest.dynamicTest("Not Below Position",
                        () -> assertFalse(secondPosition.isBelowWithRespectTo(Position.in(2,2)))),
                DynamicTest.dynamicTest("Left Position",
                        () -> assertTrue(thirdPosition.isOnTheLeftWithRespectTo(Position.in(3,5)))),
                DynamicTest.dynamicTest("Up Right Position",
                        () -> assertTrue(fourthPosition.isUpRightRespectTo(Position.in(6,5)))),
                DynamicTest.dynamicTest("Down Left Position",
                        () -> assertTrue(fifthPosition.isDownLeftRespectTo(Position.in(6,13))))
        );

    }




}
