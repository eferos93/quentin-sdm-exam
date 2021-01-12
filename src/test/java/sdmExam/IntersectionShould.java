package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class IntersectionShould {
    @Test
    public void beEqualToAnotherIntersectionWithEqualFields() {
        Intersection firstIntersection = new Intersection(Position.in(3, 3), Stone.WHITE);
        Intersection secondIntersection = new Intersection(Position.in(3, 3), Stone.WHITE);
        assertEquals(firstIntersection, secondIntersection);
    }

    @TestFactory
    Stream<DynamicTest> checkEdges() {
        Intersection firstIntersection = new Intersection(Position.in(1, 1), Stone.WHITE);
        Intersection secondIntersection = new Intersection(Position.in(2, 13), Stone.WHITE);
        Intersection thirdIntersection = new Intersection(Position.in(2, 13), Stone.WHITE);

        List<Intersection> inputList = Arrays.asList(firstIntersection, secondIntersection, thirdIntersection);
        List<Boolean> outputList = Arrays.asList(true, true, false);

        return inputList.stream()
                .map(intersection -> DynamicTest.dynamicTest("Checking Intersection" + intersection,
                        () -> {
                        int id = inputList.indexOf(intersection);
                        assertEquals(outputList.get(id), intersection.isCloseToEdge());
                }));
    }

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

