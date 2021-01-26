package quentin.core;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PositionShould {

    @ParameterizedTest
    @MethodSource("providePosition")
    public void beEqualToAnotherPositionWithSameCoordinates(Position firstPosition, Position secondPosition) {
        assertEquals(firstPosition, secondPosition);
    }

    private static Stream<Arguments> providePosition() {
        return Stream.of(
                Arguments.of(Position.in(3, 3), Position.in(3, 3)),
                Arguments.of(Position.in(13, 13), Position.in(13, 13)),
                Arguments.of(Position.in(1, 1), Position.in(1, 1))
        );

    }

    @TestFactory
    Collection<DynamicTest> checkPositions() {
        Position firstPosition = Position.in(1, 2);
        Position secondPosition = Position.in(3, 4);
        Position thirdPosition = Position.in(3, 4);
        Position fourthPosition = Position.in(5, 6);
        Position fifthPosition = Position.in(7, 12);

        return List.of(
                DynamicTest.dynamicTest("Above Position",
                        () -> assertTrue(firstPosition.isAboveWithRespectTo(Position.in(2, 2)))),
                DynamicTest.dynamicTest("Not Below Position",
                        () -> assertFalse(secondPosition.isBelowWithRespectTo(Position.in(2, 2)))),
                DynamicTest.dynamicTest("Left Position",
                        () -> assertTrue(thirdPosition.isOnTheLeftWithRespectTo(Position.in(3, 5)))),
                DynamicTest.dynamicTest("Up Right Position",
                        () -> assertTrue(fourthPosition.isUpRightRespectTo(Position.in(6, 5)))),
                DynamicTest.dynamicTest("Down Left Position",
                        () -> assertTrue(fifthPosition.isDownLeftRespectTo(Position.in(6, 13))))
        );

    }

}
