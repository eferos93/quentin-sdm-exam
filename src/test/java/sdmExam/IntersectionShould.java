package sdmExam;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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

    @TestFactory
    Stream<DynamicTest> checkOrthogonalAdjacency(){
        Intersection firstIntersection = new Intersection(Position.in(8,5), Stone.BLACK);
        Intersection secondIntersection = new Intersection(Position.in(7,9), Stone.BLACK);
        Intersection thirdIntersection = new Intersection(Position.in(3,12), Stone.BLACK);

        Intersection firstParameter = new Intersection(Position.in(9,5), Stone.BLACK);
        Intersection secondParameter = new Intersection(Position.in(7,3), Stone.BLACK);
        Intersection thirdParameter = new Intersection(Position.in(3,12), Stone.BLACK);

        List<Intersection> inputList = Arrays.asList(firstIntersection, secondIntersection,thirdIntersection);
        List<Boolean> outputList = Arrays.asList(true, false, false);
        List<Intersection> parameterList = Arrays.asList(firstParameter, secondParameter, thirdParameter);

        return inputList.stream()
                .map(intersection -> DynamicTest.dynamicTest("Checking Orthogonal Adjacent of "+ intersection,
                        () -> {
                            int index = inputList.indexOf(intersection);
                            assertEquals(outputList.get(index),intersection.isOrthogonalTo(parameterList.get(index)));
                        }));
    }

    @TestFactory
    Stream<DynamicTest> checkDiagonalAdjacency(){
        Intersection firstIntersection = new Intersection(Position.in(7,9), Stone.WHITE);
        Intersection secondIntersection = new Intersection(Position.in(3,3), Stone.WHITE);
        Intersection thirdIntersection = new Intersection(Position.in(3,12), Stone.WHITE);

        Intersection firstParameter = new Intersection(Position.in(6,10), Stone.WHITE);
        Intersection secondParameter = new Intersection(Position.in(4,4), Stone.WHITE);
        Intersection thirdParameter = new Intersection(Position.in(5,1), Stone.WHITE);

        List<Intersection> inputList = Arrays.asList(firstIntersection, secondIntersection,thirdIntersection);
        List<Boolean> outputList = Arrays.asList(true, true, false);
        List<Intersection> parameterList = Arrays.asList(firstParameter, secondParameter, thirdParameter);

        return inputList.stream()
                .map(intersection -> DynamicTest.dynamicTest("Checking Diagonal Adjacent of "+ intersection,
                        () -> {
                            int index = inputList.indexOf(intersection);
                            assertEquals(outputList.get(index),intersection.isDiagonalTo(parameterList.get(index)));
                        }));
    }
}

