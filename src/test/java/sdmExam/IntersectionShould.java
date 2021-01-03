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
    public void closeToEdge() {
        Intersection firstIntersection = new Intersection(Position.in(1, 1), Stone.WHITE);
        assertTrue(firstIntersection.isCloseToEdge());
    }
    @ParameterizedTest
    @CsvSource({"13, true" })
    public void closeToEdge(int columnPosition, boolean expected ){
        Intersection firstIntersection = new Intersection(Position.in(2,columnPosition), Stone.WHITE);
        assertTrue(firstIntersection.isCloseToEdge());
    }

}
