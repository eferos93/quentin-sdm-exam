package quentin.core;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class IntersectionShould {

    private static Stream<Arguments> provideIntersection() {
        return Stream.of(
                Arguments.of(new Intersection(Position.in(3, 3), Colour.WHITE)
                        , new Intersection(Position.in(3, 3), Colour.WHITE)),
                Arguments.of(new Intersection(Position.in(13, 13), Colour.BLACK)
                        , new Intersection(Position.in(13, 13), Colour.BLACK)),
                Arguments.of(new Intersection(Position.in(5, 12), Colour.WHITE)
                        , new Intersection(Position.in(5, 12), Colour.WHITE))
        );
    }

    @ParameterizedTest
    @MethodSource("provideIntersection")
    public void beEqualToAnotherIntersectionWithEqualFields(Intersection firstIntersection, Intersection secondIntersection) {
        assertEquals(firstIntersection, secondIntersection);
    }

    @ParameterizedTest
    @MethodSource("provideIntersection")
    public void checkOccupiedIntersections(Intersection intersection) {
        assertTrue(intersection.isOccupied());
    }

    @TestFactory
    Stream<DynamicTest> checkOrthogonalAdjacency() {

        List<Intersection> inputList = List.of(
                new Intersection(Position.in(8, 5), Colour.BLACK),
                new Intersection(Position.in(7, 9), Colour.BLACK),
                new Intersection(Position.in(3, 12), Colour.BLACK)
        );

        List<Intersection> parameterList = List.of(
                new Intersection(Position.in(9, 5), Colour.WHITE),
                new Intersection(Position.in(7, 3), Colour.WHITE),
                new Intersection(Position.in(3, 12), Colour.WHITE)
        );

        List<Boolean> outputList = List.of(true, false, false);

        return inputList.stream()
                .map(intersection -> DynamicTest.dynamicTest("Checking Orthogonal Adjacent of " + intersection,
                        () -> {
                            int index = inputList.indexOf(intersection);
                            assertEquals(outputList.get(index), intersection.isOrthogonalTo(parameterList.get(index)));
                        })
                );
    }

    @TestFactory
    Stream<DynamicTest> checkDiagonalAdjacency() {

        List<Intersection> inputList = List.of(
                new Intersection(Position.in(7, 9), Colour.WHITE),
                new Intersection(Position.in(3, 3), Colour.WHITE),
                new Intersection(Position.in(3, 12), Colour.WHITE)
        );

        List<Intersection> parameterList = List.of(
                new Intersection(Position.in(6, 10), Colour.WHITE),
                new Intersection(Position.in(4, 4), Colour.WHITE),
                new Intersection(Position.in(5, 1), Colour.WHITE)
        );

        List<Boolean> outputList = List.of(true, true, false);

        return inputList.stream()
            .map(intersection -> DynamicTest.dynamicTest("Checking Diagonal Adjacent of " + intersection,
                    () -> {
                        int index = inputList.indexOf(intersection);
                        assertEquals(outputList.get(index), intersection.isDiagonalTo(parameterList.get(index)));
                    })
            );
    }
}

