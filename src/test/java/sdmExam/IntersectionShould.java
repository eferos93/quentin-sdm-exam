package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntersectionShould {
    @Test
    public void beEqualToAnotherCellWithEqualFields() {
        Intersection firstIntersection = new Intersection(Position.in(3, 3), Mark.WHITE);
        Intersection secondIntersection = new Intersection(Position.in(3, 3), Mark.WHITE);
        assertEquals(firstIntersection, secondIntersection);
    }
}
