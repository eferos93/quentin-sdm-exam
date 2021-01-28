package quentin.core;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.stream.Collectors;

public class ChainContainer {
    private final Map<Stone, Graph<Intersection, DefaultEdge>> chains = new HashMap<>() {{
        put(Stone.BLACK, new SimpleGraph<>(DefaultEdge.class));
        put(Stone.WHITE, new SimpleGraph<>(DefaultEdge.class));
    }};
    private final Set<BoardSide> sides = EnumSet.allOf(BoardSide.class);

    ChainContainer(int boardSize) {
        BoardSide.setBoardSize(boardSize);
        sides.forEach(BoardSide::initialiseSide);
    }

    protected void updateChain(Intersection newIntersection) {
        Graph<Intersection, DefaultEdge> chainsOfColor = chains.get(newIntersection.getStone());
        chainsOfColor.addVertex(newIntersection);
        chainsOfColor.vertexSet().stream()
                .filter(newIntersection::isOrthogonalTo)
                .forEach(orthogonalIntersection -> chainsOfColor.addEdge(orthogonalIntersection, newIntersection));
    }

    private boolean hasACompleteChain(Map.Entry<Stone, Graph<Intersection, DefaultEdge>> chainsOfAGivenColor) {
        return new ConnectivityInspector<>(chainsOfAGivenColor.getValue()).connectedSets().stream()
                .anyMatch(chain -> isChainConnectedToSameColorSides(chainsOfAGivenColor.getKey(), chain));
    }

    private boolean isChainConnectedToSameColorSides(Stone color, Set<Intersection> chain) {
        return
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> getSidesOfColor(color).get(0).isAdjacentTo(position))
                &&
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> getSidesOfColor(color).get(1).isAdjacentTo(position));
    }

    private List<BoardSide> getSidesOfColor(Stone color) {
        return sides.stream().filter(side -> side.hasColor(color)).collect(Collectors.toList());
    }

    public Stone getColorWithCompleteChain() {
        return chains.entrySet().stream()
                .filter(this::hasACompleteChain)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(Stone.NONE);
    }
}
