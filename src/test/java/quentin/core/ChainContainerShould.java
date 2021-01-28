package quentin.core;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static quentin.core.Position.in;

public class ChainContainerShould {

    @Test
    public void touchSidesIfItIsACompleteChain() {
        Stream<Intersection> intersectionStream = Stream.of(
                new Intersection(in(1, 1), Stone.BLACK),
                new Intersection(in(1, 2), Stone.BLACK),
                new Intersection(in(2, 2), Stone.BLACK),
                new Intersection(in(3, 2), Stone.BLACK)
        );

        ChainContainer chainContainer = new ChainContainer(3);
        intersectionStream.forEach(chainContainer::updateChain);
        assertEquals(Stone.BLACK, chainContainer.getColorWithCompleteChain());
    }

    @Test
    public void notTouchSidesIfItIsNotACompleteChain() {
        Stream<Intersection> intersectionStream = Stream.of(
                new Intersection(in(1, 1), Stone.BLACK),
                new Intersection(in(1, 2), Stone.BLACK),
                new Intersection(in(2, 2), Stone.BLACK)
        );

        ChainContainer chainContainer = new ChainContainer(3);
        intersectionStream.forEach(chainContainer::updateChain);
        assertEquals(Stone.NONE, chainContainer.getColorWithCompleteChain());
    }

}
