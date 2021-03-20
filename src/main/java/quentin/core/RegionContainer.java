package quentin.core;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RegionContainer {
    private final Graph<Intersection, DefaultEdge> graph;
    private final List<Intersection> intersections;

    protected RegionContainer(List<Intersection> allEmptyIntersections, int boardSize) {
        this.intersections = allEmptyIntersections;
        Supplier<Intersection> vertexSupplier = new Supplier<>() {
            private int index = 0;

            @Override
            public Intersection get() {
                return allEmptyIntersections.get(index++);
            }
        };

        graph = new SimpleGraph<>(vertexSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);
        new GridGraphGenerator<Intersection, DefaultEdge>(boardSize, boardSize).generateGraph(graph, null);
    }

    protected void removeIntersection(Intersection nonEmptyIntersection) {
        graph.removeVertex(nonEmptyIntersection);
    }

    private List<Set<Intersection>> getRegions() {
        return new ConnectivityInspector<>(graph).connectedSets();
    }

    private List<Set<Intersection>> getTerritories() {
        return getRegions().stream()
                .filter(region -> region.stream()
                        .allMatch(this::isOrthogonalToAtLeastTwoStones)
                ).collect(Collectors.toList());
    }

    private boolean isOrthogonalToAtLeastTwoStones(Intersection emptyIntersection) {
        return this.intersections.stream()
                .filter(emptyIntersection::isOrthogonalTo)
                .filter(Intersection::isOccupied)
                .count() >= 2;
    }

    private Set<Intersection> getOrthogonalAdjacencyIntersections(Intersection intersection) {
        return this.intersections.stream()
                .filter(otherIntersection -> otherIntersection.isOrthogonalTo(intersection))
                .collect(Collectors.toSet());
    }

    private long countIntersectionsOfColor(final Set<Intersection> intersectionsSurroundingTerritory, Colour colour) {
        return intersectionsSurroundingTerritory.stream()
                .filter(intersection -> intersection.hasStone(colour))
                .count();
    }

    private Set<Intersection> getIntersectionsThatSurroundsTheTerritory(Set<Intersection> territory) {
        return territory.stream()
                .flatMap(territoryIntersection ->
                        getOrthogonalAdjacencyIntersections(territoryIntersection).stream()
                )
                .filter(Intersection::isOccupied)
                .collect(Collectors.toSet());
    }


    private Optional<Colour> getColorToFillTerritory(Set<Intersection> territory, Colour lastPlay) {
        Set<Intersection> intersectionsSurroundingTerritory =
                getIntersectionsThatSurroundsTheTerritory(territory);
        long countOfWhiteStones = countIntersectionsOfColor(intersectionsSurroundingTerritory, Colour.WHITE);
        long countOfBlackStones = countIntersectionsOfColor(intersectionsSurroundingTerritory, Colour.BLACK);

        if (countOfWhiteStones != countOfBlackStones) {
            return Optional.of((countOfWhiteStones < countOfBlackStones) ? Colour.BLACK : Colour.WHITE);
        } else {
            return Optional.ofNullable(lastPlay).map(Colour::getOppositeColor);
        }
    }

    protected Map<Set<Intersection>, Optional<Colour>> getTerritoriesAndStonesToFill(Colour lastPlay) {
        return getTerritories().stream()
                .map(territory -> {
                    Optional<Colour> color = getColorToFillTerritory(territory, lastPlay);
                    return Map.entry(territory, color);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected void addIntersection(Intersection intersection) {
        graph.addVertex(intersection);
        graph.vertexSet().stream()
                .filter(intersection::isOrthogonalTo)
                .forEach(orthogonalIntersection -> graph.addEdge(orthogonalIntersection, intersection));
    }
}