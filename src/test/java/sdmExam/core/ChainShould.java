package sdmExam.core;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static sdmExam.core.Position.in;

public class ChainShould {

    @Test
    public void touchEdgesIfItIsACompleteChain() {
        Stream<Intersection> intersectionStream = Stream.of(
                new Intersection(in(1, 1), Stone.BLACK),
                new Intersection(in(1, 2), Stone.BLACK),
                new Intersection(in(2, 2), Stone.BLACK),
                new Intersection(in(3, 2), Stone.BLACK)
        );

        Chain chain = new Chain();
        intersectionStream.forEach(chain::updateChain);

        List<BoardSide> listOfBoardSides = List.of(BoardSide.BOTTOM, BoardSide.TOP);

        BoardSide.setBoardSize(3);
        listOfBoardSides.forEach(BoardSide::initialiseSide);

        assertTrue(chain.hasACompleteChain(listOfBoardSides));
    }

    @Test
    public void notTouchEdgesIfItIsNotACompleteChain() {
        Stream<Intersection> intersectionStream = Stream.of(
                new Intersection(in(1, 1), Stone.BLACK),
                new Intersection(in(1, 2), Stone.BLACK),
                new Intersection(in(2, 2), Stone.BLACK)
        );

        Chain chain = new Chain();
        intersectionStream.forEach(chain::updateChain);

        List<BoardSide> listOfBoardSides = List.of(BoardSide.BOTTOM, BoardSide.TOP);

        BoardSide.setBoardSize(3);
        listOfBoardSides.forEach(BoardSide::initialiseSide);

        assertFalse(chain.hasACompleteChain(listOfBoardSides));
    }

}
