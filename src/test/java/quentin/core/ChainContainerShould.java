package quentin.core;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static quentin.core.Position.in;

public class ChainContainerShould {

    @Test
    public void touchEdgesIfItIsACompleteChain() {
        Stream<Intersection> intersectionStream = Stream.of(
                new Intersection(in(1, 1), Stone.BLACK),
                new Intersection(in(1, 2), Stone.BLACK),
                new Intersection(in(2, 2), Stone.BLACK),
                new Intersection(in(3, 2), Stone.BLACK)
        );

        ChainContainer chainContainer = new ChainContainer();
        intersectionStream.forEach(chainContainer::updateChain);

        Set<BoardSide> listOfBoardSides = Set.of(BoardSide.BOTTOM, BoardSide.TOP);

        BoardSide.setBoardSize(3);
        listOfBoardSides.forEach(BoardSide::initialiseSide);

        assertEquals(Stone.BLACK, chainContainer.getColorWithCompleteChain(listOfBoardSides));
    }

    @Test
    public void notTouchEdgesIfItIsNotACompleteChain() {
        Stream<Intersection> intersectionStream = Stream.of(
                new Intersection(in(1, 1), Stone.BLACK),
                new Intersection(in(1, 2), Stone.BLACK),
                new Intersection(in(2, 2), Stone.BLACK)
        );

        ChainContainer chainContainer = new ChainContainer();
        intersectionStream.forEach(chainContainer::updateChain);

        Set<BoardSide> listOfBoardSides = Set.of(BoardSide.BOTTOM, BoardSide.TOP);

        BoardSide.setBoardSize(3);
        listOfBoardSides.forEach(BoardSide::initialiseSide);

        assertEquals(Stone.NONE, chainContainer.getColorWithCompleteChain(listOfBoardSides));
    }

}
