package sdmExam;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.List;
import java.util.Set;

public class Chain {
    private final Graph<Intersection, DefaultEdge> chains;

    public Chain() {
        chains = new SimpleGraph<>(DefaultEdge.class);
    }

    protected void updateChain(Intersection newIntersection) {
        chains.addVertex(newIntersection);
        chains.vertexSet().stream()
                .filter(newIntersection::isOrthogonalTo)
                .forEach(orthogonalIntersection -> chains.addEdge(orthogonalIntersection, newIntersection));
    }

    protected boolean hasACompleteChain(List<Edge> sameColorEdges) {
        return new ConnectivityInspector<>(chains).connectedSets().stream()
                .anyMatch(chain -> isChainConnectedToSameColorEdges(sameColorEdges, chain));
    }

    private boolean isChainConnectedToSameColorEdges(List<Edge> sameColorEdges, Set<Intersection> chain) {
        return
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> sameColorEdges.get(0).isAdjacentTo(position))
                &&
                chain.stream()
                        .map(Intersection::getPosition)
                        .anyMatch(position -> sameColorEdges.get(1).isAdjacentTo(position));
    }
}
