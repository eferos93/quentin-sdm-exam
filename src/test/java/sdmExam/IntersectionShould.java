package sdmExam;

import org.junit.jupiter.api.Test;
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

}
