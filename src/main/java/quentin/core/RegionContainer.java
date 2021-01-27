package quentin.core;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.List;
import java.util.Map;
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

    protected void removeNonEmptyIntersection(Intersection nonEmptyIntersection) {
        graph.removeVertex(nonEmptyIntersection);
    }

    protected List<Set<Intersection>> getRegions() {
        return new ConnectivityInspector<>(graph).connectedSets();
    }

    protected List<Set<Intersection>> getTerritories(final List<Intersection> intersections) {
        return getRegions().stream()
                .filter(region -> region.stream()
                        .allMatch(emptyIntersection -> isOrthogonalToAtLeastTwoStones(emptyIntersection, intersections))
                ).collect(Collectors.toList());
    }

    private boolean isOrthogonalToAtLeastTwoStones(Intersection emptyIntersection, final List<Intersection> intersections) {
        return intersections.stream()
                .filter(emptyIntersection::isOrthogonalTo)
                .filter(Intersection::isOccupied)
                .count() >= 2;
    }

    //TODO: code smell long method, need to refactor
    private Stone getStoneToFillTerritory(List<Intersection> intersections, Set<Intersection> territory, Stone lastPlay) {
        Set<Intersection> intersectionsSurroundingTerritory = territory.stream()
                .flatMap(intersection -> getOrthogonalAdjacencyIntersections(intersection, intersections).stream())
                .filter(Intersection::isOccupied)
                .collect(Collectors.toSet());

        long countOfWhiteStones = countIntersectionsOfColor(intersectionsSurroundingTerritory, Stone.WHITE);
        long countOfBlackStones = countIntersectionsOfColor(intersectionsSurroundingTerritory, Stone.BLACK);

        Stone stone;

        if (countOfWhiteStones != countOfBlackStones) {
            stone = (countOfWhiteStones < countOfBlackStones) ? Stone.BLACK : Stone.WHITE;
        } else {
            stone = lastPlay.getOppositeColor();
        }
        return stone;
    }

    private Set<Intersection> getOrthogonalAdjacencyIntersections(Intersection intersection, List<Intersection> intersections) {
        return intersections.stream()
                .filter(otherIntersection -> otherIntersection.isOrthogonalTo(intersection))
                .collect(Collectors.toSet());
    }

    public Map<Set<Intersection>, Stone> getTerritoriesAndStonesToFill(List<Intersection> intersections, Stone lastPlay) {
        return getTerritories(intersections).stream()
                .map(territory -> {
                    Stone stone = getStoneToFillTerritory(intersections, territory, lastPlay);
                    return Map.entry(territory, stone);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private long countIntersectionsOfColor(Set<Intersection> intersections, Stone color) {
        return intersections.stream()
                .filter(intersection -> intersection.hasStone(color))
                .count();
    }
}