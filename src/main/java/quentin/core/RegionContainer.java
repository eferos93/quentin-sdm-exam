package quentin.core;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RegionContainer {
    private static final RegionContainer regionContainer = new RegionContainer();
    private Graph<Intersection, DefaultEdge> graph;

    private RegionContainer() {}

    public static RegionContainer getRegionsContainer() {
        return regionContainer;
    }

    protected void createGraph(List<Intersection> emptyIntersections, int boardSize) {
        Supplier<Intersection> vertexSupplier = new Supplier<>() {
            private int index = 0;

            @Override
            public Intersection get() {
                return emptyIntersections.get(index++);
            }
        };

        graph = new SimpleGraph<>(vertexSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);
        new GridGraphGenerator<Intersection, DefaultEdge>(boardSize, boardSize).generateGraph(graph, null);
    }

    protected void updateRegionContainer(Intersection intersection) {
        graph.removeVertex(intersection);
    }

    protected List<Set<Intersection>> getRegions() {
        return new ConnectivityInspector<>(graph).connectedSets();
    }

    protected List<Set<Intersection>> getTerritories(final List<Intersection> intersections) {
        return getRegions().stream()
                .filter(region -> region.stream()
                        .allMatch(intersection -> isOrthogonalToAtLeastTwoStones(intersection, intersections))
                ).collect(Collectors.toList());

    }

    private boolean isOrthogonalToAtLeastTwoStones(Intersection emptyIntersection, final List<Intersection> intersections) {
        return intersections.stream()
                .filter(emptyIntersection::isOrthogonalTo)
                .filter(Intersection::isOccupied)
                .count() >= 2;
    }
}