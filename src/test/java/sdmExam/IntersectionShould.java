package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class IntersectionShould {

    @ParameterizedTest
    @MethodSource("provideIntersection")
    public void beEqualToAnotherIntersectionWithEqualFields(Intersection firstIntersection, Intersection secondIntersection) {
        assertEquals(firstIntersection, secondIntersection);
    }

    private static Stream<Arguments> provideIntersection(){
        return Stream.of(
                Arguments.of(new Intersection(Position.in(3, 3), Stone.WHITE)
                        ,new Intersection(Position.in(3, 3), Stone.WHITE)),
                Arguments.of(new Intersection(Position.in(13, 13), Stone.BLACK)
                        ,new Intersection(Position.in(13, 13), Stone.BLACK)),
                Arguments.of(new Intersection(Position.in(5, 12), Stone.WHITE)
                        ,new Intersection(Position.in(5, 12), Stone.WHITE))
        );
    }

    //TODO Below test can be written with streams (by using object function tuples)
    @TestFactory
    Collection<DynamicTest> adjacencyCheck() {
        Intersection firstIntersection = new Intersection(Position.in(8,5), Stone.BLACK);
        Intersection secondIntersection = new Intersection(Position.in(7,9), Stone.BLACK);
        Intersection thirdIntersection = new Intersection(Position.in(7,9), Stone.BLACK);

        return Arrays.asList(
                DynamicTest.dynamicTest("Orthogonal Intersections",
                        () -> assertTrue(firstIntersection.isOrthogonalTo(new Intersection(Position.in(9,5),Stone.BLACK)))),
                DynamicTest.dynamicTest("Diagonal Intersections",
                        () -> assertTrue(secondIntersection.isDiagonalTo(new Intersection(Position.in(6,10),Stone.BLACK)))),
                DynamicTest.dynamicTest("Non Orthogonal Intersections",
                        () -> assertFalse(thirdIntersection.isOrthogonalTo(new Intersection(Position.in(7,3),Stone.WHITE))))
        );
    }
}

