package quentin.core;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ChainContainer {
    private final Map<Stone, Graph<Intersection, DefaultEdge>> chains = new HashMap<>() {{
        put(Stone.BLACK, new SimpleGraph<>(DefaultEdge.class));
        put(Stone.WHITE, new SimpleGraph<>(DefaultEdge.class));
    }};

    static ChainContainer getChainContainer() {
        return new ChainContainer();
    }

    protected void updateChain(Intersection newIntersection) {
        Graph<Intersection, DefaultEdge> chainsOfColor = chains.get(newIntersection.getStone());
        chainsOfColor.addVertex(newIntersection);
        chainsOfColor.vertexSet().stream()
                .filter(newIntersection::isOrthogonalTo)
                .forEach(orthogonalIntersection -> chainsOfColor.addEdge(orthogonalIntersection, newIntersection));
    }

    private boolean hasACompleteChain(Graph<Intersection, DefaultEdge> chains, List<BoardSide> sameColorSides) {
        return new ConnectivityInspector<>(chains).connectedSets().stream()
                .anyMatch(chain -> isChainConnectedToSameColorSides(sameColorSides, chain));
    }

    private boolean isChainConnectedToSameColorSides(List<BoardSide> sameColorSides, Set<Intersection> chain) {
        return
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> sameColorSides.get(0).isAdjacentTo(position))
                &&
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> sameColorSides.get(1).isAdjacentTo(position));
    }

    private List<BoardSide> getSidesOfColor(Set<BoardSide> sides, Stone color) {
        return sides.stream().filter(side -> side.hasColor(color)).collect(Collectors.toList());
    }

    public Stone getColorWithCompleteChain(Set<BoardSide> sides) {
        return chains.entrySet().stream()
                .filter(chains -> hasACompleteChain(chains.getValue(), getSidesOfColor(sides, chains.getKey())))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(Stone.NONE);
    }
}
