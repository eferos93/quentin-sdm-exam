package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class IntersectionShould {
    @Test
    public void beEqualToAnotherIntersectionWithEqualFields() {
        Intersection firstIntersection = new Intersection(Position.in(3, 3), Stone.WHITE);
        Intersection secondIntersection = new Intersection(Position.in(3, 3), Stone.WHITE);
        assertEquals(firstIntersection, secondIntersection);
    }
    @Test
    public void closeToLeftEdge() {
        Intersection firstIntersection = new Intersection(Position.in(1, 1), Stone.WHITE);
        assertTrue(firstIntersection.isCloseToEdge());
    }

    @Test
    public void closeToRightEdge(){
        Intersection firstIntersection = new Intersection(Position.in(2, 13), Stone.WHITE);
        assertTrue(firstIntersection.isCloseToEdge());
    }

    @Test
    public void orthogonalIntersections(){
        Intersection firstIntersection = new Intersection(Position.in(8,5), Stone.BLACK);
        assertTrue(firstIntersection.isOrthogonalTo(new Intersection(Position.in(9,5),Stone.BLACK)));
    }

    @Test
    public void diagonalIntersections(){
        Intersection firstIntersection = new Intersection(Position.in(7,9), Stone.BLACK);
        assertTrue(firstIntersection.isDiagonalTo(new Intersection(Position.in(6,10),Stone.BLACK)));
    }

    @Test
    public void nonOrthogonalIntersections(){
        Intersection firstIntersection = new Intersection(Position.in(4,4), Stone.WHITE);
        assertFalse(firstIntersection.isOrthogonalTo(new Intersection(Position.in(7,3),Stone.WHITE)));
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

