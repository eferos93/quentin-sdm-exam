package quentin.core;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.stream.Collectors;

public class ChainContainer {
    private final EnumMap<Colour, Graph<Intersection, DefaultEdge>> chains = new EnumMap<>(Colour.class);
    private final Set<BoardSide> sides = EnumSet.allOf(BoardSide.class);

    ChainContainer(int boardSize) {
        chains.put(Colour.BLACK, new SimpleGraph<>(DefaultEdge.class));
        chains.put(Colour.WHITE, new SimpleGraph<>(DefaultEdge.class));
        BoardSide.setBoardSize(boardSize);
        sides.forEach(BoardSide::initialiseSide);
    }

    protected void updateChain(Intersection newIntersection) {
        Optional<Graph<Intersection, DefaultEdge>> chainsOfColor = Optional.ofNullable(chains.get(newIntersection.getColour().orElseThrow()));
        chainsOfColor.ifPresent(chainsOfSingleColor -> {
            chainsOfSingleColor.addVertex(newIntersection);
            chainsOfSingleColor.vertexSet().stream()
                    .filter(newIntersection::isOrthogonalTo)
                    .forEach(orthogonalIntersection -> chainsOfSingleColor.addEdge(orthogonalIntersection, newIntersection));
        });
    }

    protected void removeIntersection(Intersection intersection) {
        chains.get(intersection.getColour().orElseThrow()).removeVertex(intersection);
    }

    private boolean hasACompleteChain(Map.Entry<Colour, Graph<Intersection, DefaultEdge>> chainsOfAGivenColor) {
        return new ConnectivityInspector<>(chainsOfAGivenColor.getValue()).connectedSets().stream()
                .anyMatch(chain -> isChainConnectedToSameColorSides(chainsOfAGivenColor.getKey(), chain));
    }

    private boolean isChainConnectedToSameColorSides(Colour colour, Set<Intersection> chain) {
        return
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> getSidesOfColor(colour).get(0).isAdjacentTo(position))
                &&
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> getSidesOfColor(colour).get(1).isAdjacentTo(position));
    }

    private List<BoardSide> getSidesOfColor(Colour colour) {
        return sides.stream().filter(side -> side.hasColor(colour)).collect(Collectors.toList());
    }

    protected Optional<Colour> getColorWithCompleteChain() {
        return chains.entrySet().stream()
                .filter(this::hasACompleteChain)
                .map(Map.Entry::getKey)
                .findFirst();
    }
}
