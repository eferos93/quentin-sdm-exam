package sdmExam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}
