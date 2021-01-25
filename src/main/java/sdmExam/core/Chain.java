package sdmExam.core;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.List;
import java.util.Set;

public class Chain {
    private final Graph<Intersection, DefaultEdge> chains = new SimpleGraph<>(DefaultEdge.class);

    public void updateChain(Intersection newIntersection) {
        chains.addVertex(newIntersection);
        chains.vertexSet().stream()
                .filter(newIntersection::isOrthogonalTo)
                .forEach(orthogonalIntersection -> chains.addEdge(orthogonalIntersection, newIntersection));
    }

    public boolean hasACompleteChain(List<BoardSide> sameColorSides) {
        return new ConnectivityInspector<>(chains).connectedSets().stream()
                .anyMatch(chain -> isChainConnectedToSameColorEdges(sameColorSides, chain));
    }

    private boolean isChainConnectedToSameColorEdges(List<BoardSide> sameColorSides, Set<Intersection> chain) {
        return
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> sameColorSides.get(0).isAdjacentTo(position))
                &&
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> sameColorSides.get(1).isAdjacentTo(position));
    }
}
