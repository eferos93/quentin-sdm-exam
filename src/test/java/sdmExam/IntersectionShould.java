package sdmExam;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

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
    public void checkChain(){
        Intersection intersection1 = new Intersection(Position.in(3,4),Stone.BLACK);
        Intersection intersection2 = new Intersection(Position.in(4,4),Stone.BLACK);
        List<Intersection> intersections = Arrays.asList(intersection1, intersection2);
        assertTrue(Intersection.isChain(intersections));
    }

    @Test
    public void checkNoChain() {
        Intersection intersection1 = new Intersection(Position.in(5,12),Stone.WHITE);
        Intersection intersection2 = new Intersection(Position.in(7,7),Stone.WHITE);
        Intersection intersection3 = new Intersection(Position.in(7,8),Stone.WHITE);
        List<Intersection> intersections = Arrays.asList(intersection1, intersection2, intersection3);
        assertFalse(Intersection.isChain(intersections));
    }

}
