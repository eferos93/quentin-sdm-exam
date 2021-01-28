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
//TODO: some methods have long parameter list: evaluate if it worth to be refactored
public class RegionContainer {
    private Graph<Intersection, DefaultEdge> graph;

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

    private List<Set<Intersection>> getRegions() {
        return new ConnectivityInspector<>(graph).connectedSets();
    }

    private List<Set<Intersection>> getTerritories(final List<Intersection> allIntersections) {
        return getRegions().stream()
                .filter(region -> region.stream()
                        .allMatch(emptyIntersection -> isOrthogonalToAtLeastTwoStones(emptyIntersection, allIntersections))
                ).collect(Collectors.toList());
    }

    private boolean isOrthogonalToAtLeastTwoStones(Intersection emptyIntersection, final List<Intersection> intersections) {
        return intersections.stream()
                .filter(emptyIntersection::isOrthogonalTo)
                .filter(Intersection::isOccupied)
                .count() >= 2;
    }

    private Set<Intersection> getOrthogonalAdjacencyIntersections(Intersection intersection,
                                                                  final List<Intersection> allIntersections) {
        return allIntersections.stream()
                .filter(otherIntersection -> otherIntersection.isOrthogonalTo(intersection))
                .collect(Collectors.toSet());
    }

    private long countIntersectionsOfColor(Set<Intersection> intersections, Stone color) {
        return intersections.stream()
                .filter(intersection -> intersection.hasStone(color))
                .count();
    }

    private Set<Intersection> getIntersectionsThatSurroundsTheTerritory(Set<Intersection> territory,
                                                                        final List<Intersection> allIntersections) {
        return territory.stream()
                .flatMap(intersection -> getOrthogonalAdjacencyIntersections(intersection, allIntersections).stream())
                .filter(Intersection::isOccupied)
                .collect(Collectors.toSet());
    }


    private Stone getStoneToFillTerritory(Set<Intersection> territory,
                                          final List<Intersection> allIntersections,
                                          Stone lastPlay) {
        Set<Intersection> intersectionsSurroundingTerritory = getIntersectionsThatSurroundsTheTerritory(territory, allIntersections);
        long countOfWhiteStones = countIntersectionsOfColor(intersectionsSurroundingTerritory, Stone.WHITE);
        long countOfBlackStones = countIntersectionsOfColor(intersectionsSurroundingTerritory, Stone.BLACK);

        if (countOfWhiteStones != countOfBlackStones) {
            return (countOfWhiteStones < countOfBlackStones) ? Stone.BLACK : Stone.WHITE;
        } else {
            return lastPlay.getOppositeColor();
        }
    }

    protected Map<Set<Intersection>, Stone> getTerritoriesAndStonesToFill(final List<Intersection> allIntersections,
                                                                          Stone lastPlay) {
        return getTerritories(allIntersections).stream()
                .map(territory -> {
                    Stone stone = getStoneToFillTerritory(territory, allIntersections, lastPlay);
                    return Map.entry(territory, stone);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}