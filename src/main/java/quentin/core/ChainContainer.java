package quentin.core;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.stream.Collectors;

public class ChainContainer {
    private final EnumMap<Color, Graph<Intersection, DefaultEdge>> chains = new EnumMap<>(Color.class);
    private final Set<BoardSide> sides = EnumSet.allOf(BoardSide.class);

    ChainContainer(int boardSize) {
        chains.put(Color.BLACK, new SimpleGraph<>(DefaultEdge.class));
        chains.put(Color.WHITE, new SimpleGraph<>(DefaultEdge.class));
        BoardSide.setBoardSize(boardSize);
        sides.forEach(BoardSide::initialiseSide);
    }

    protected void updateChain(Intersection newIntersection) {
        Optional<Graph<Intersection, DefaultEdge>> chainsOfColor = Optional.ofNullable(chains.get(newIntersection.getColor().orElseThrow()));
        chainsOfColor.ifPresent(chainsOfSingleColor -> {
            chainsOfSingleColor.addVertex(newIntersection);
            chainsOfSingleColor.vertexSet().stream()
                    .filter(newIntersection::isOrthogonalTo)
                    .forEach(orthogonalIntersection -> chainsOfSingleColor.addEdge(orthogonalIntersection, newIntersection));
        });
    }

    protected void removeIntersection(Intersection intersection) {
        chains.get(intersection.getColor().orElseThrow()).removeVertex(intersection);
    }

    private boolean hasACompleteChain(Map.Entry<Color, Graph<Intersection, DefaultEdge>> chainsOfAGivenColor) {
        return new ConnectivityInspector<>(chainsOfAGivenColor.getValue()).connectedSets().stream()
                .anyMatch(chain -> isChainConnectedToSameColorSides(chainsOfAGivenColor.getKey(), chain));
    }

    private boolean isChainConnectedToSameColorSides(Color color, Set<Intersection> chain) {
        return
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> getSidesOfColor(color).get(0).isAdjacentTo(position))
                &&
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> getSidesOfColor(color).get(1).isAdjacentTo(position));
    }

    private List<BoardSide> getSidesOfColor(Color color) {
        return sides.stream().filter(side -> side.hasColor(color)).collect(Collectors.toList());
    }

    protected Optional<Color> getColorWithCompleteChain() {
        return chains.entrySet().stream()
                .filter(this::hasACompleteChain)
                .map(Map.Entry::getKey)
                .findFirst();
    }
}
