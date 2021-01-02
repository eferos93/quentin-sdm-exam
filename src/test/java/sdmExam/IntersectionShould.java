package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntersectionShould {
    @Test
    public void beEqualToAnotherIntersectionWithEqualFields() {
        Intersection firstIntersection = new Intersection(Position.in(3, 3), Mark.WHITE);
        Intersection secondIntersection = new Intersection(Position.in(3, 3), Mark.WHITE);
        assertEquals(firstIntersection, secondIntersection);
    }
    @Test
    public void closeToEdge() {
        Intersection firstIntersection = new Intersection(Position.in(1, 1), Mark.WHITE);
        assertTrue(firstIntersection.isCloseToEdge());
    }
}
