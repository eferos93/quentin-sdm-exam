package quentin.core;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static quentin.core.Position.in;

public class ChainContainerShould {

    @Test
    public void touchSidesIfItIsACompleteChain() {
        Stream<Intersection> intersectionStream = Stream.of(
                new Intersection(in(1, 1), Colour.BLACK),
                new Intersection(in(1, 2), Colour.BLACK),
                new Intersection(in(2, 2), Colour.BLACK),
                new Intersection(in(3, 2), Colour.BLACK)
        );

        ChainContainer chainContainer = new ChainContainer(3);
        intersectionStream.forEach(chainContainer::updateChain);
        assertTrue(chainContainer.getColorWithCompleteChain().map(chainColor -> chainColor == Colour.BLACK).orElse(false));
    }

    @Test
    public void notTouchSidesIfItIsNotACompleteChain() {
        Stream<Intersection> intersectionStream = Stream.of(
                new Intersection(in(1, 1), Colour.BLACK),
                new Intersection(in(1, 2), Colour.BLACK),
                new Intersection(in(2, 2), Colour.BLACK)
        );

        ChainContainer chainContainer = new ChainContainer(3);
        intersectionStream.forEach(chainContainer::updateChain);
        assertTrue(chainContainer.getColorWithCompleteChain().isEmpty());
    }

}
