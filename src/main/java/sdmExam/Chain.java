package sdmExam;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chain {
    private Graph<Intersection, DefaultEdge> chains;
    private ConnectivityInspector<Intersection, DefaultEdge> connectivityInspector;

    public Chain() {
        chains = new SimpleGraph<>(DefaultEdge.class);
        //connectivityInspector = new ConnectivityInspector<>(chains);
    }

    protected void updateChain(Intersection newIntersection) {
        chains.addVertex(newIntersection);
        chains.vertexSet().stream()
                .filter(newIntersection::isOrthogonalTo)
                .forEach(orthogonalIntersection -> chains.addEdge(orthogonalIntersection, newIntersection));
    }

    public Optional<Edge> getAdjacentEdge(Intersection intersection, Set<Edge> edges) {
        return edges.stream()
                .filter(edge -> edge.isAdjacentTo(intersection.getPosition()))
                .findFirst();
    }

    protected boolean hasACompleteChain(List<Edge> boardEdges) {
        List<Set<Intersection>> sets = new ConnectivityInspector<>(chains).connectedSets();
        return sets.stream()
                .anyMatch(chain ->
                    chain.stream().map(Intersection::getPosition).anyMatch(position -> boardEdges.get(0).isAdjacentTo(position))
                    &&
                    chain.stream().map(Intersection::getPosition).anyMatch(position -> boardEdges.get(1).isAdjacentTo(position))
                );
    }
}
