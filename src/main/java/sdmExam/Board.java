package sdmExam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    private static final int DEFAULT_BOARD_SIZE = 13;
    private final int TOP_AND_LEFT_EDGE_INDEX = 0;
    private final int BOTTOM_AND_RIGHT_EDGE_INDEX;
    private final int BOARD_SIZE;
    private final List<Intersection> intersections = new ArrayList<>();
    private final Region region = Region.getRegion();
    private final Set<Edge> edges = EnumSet.of(Edge.BOTTOM, Edge.TOP, Edge.LEFT, Edge.RIGHT);
    private final Map<Stone, List<Set<Intersection>>> chainsContainers = new HashMap<>();

    public Set<Edge> getEnumSet() {
        return edges;
    }

    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    private Board(int boardSize) {
        this.BOARD_SIZE = boardSize;
        this.BOTTOM_AND_RIGHT_EDGE_INDEX = boardSize + 1;
        this.chainsContainers.put(Stone.BLACK, new ArrayList<>());
        this.chainsContainers.put(Stone.WHITE, new ArrayList<>());
        this.edges.forEach(edge -> edge.initialiseEdge(BOTTOM_AND_RIGHT_EDGE_INDEX));

        List<Intersection> tmp = new ArrayList<>();
        for (int row = 1; row <= this.BOARD_SIZE; row++) {
            for (int column = 1; column <= this.BOARD_SIZE; column++) {
                this.intersections.add(Intersection.empty(Position.in(row, column)));
                tmp.add(Intersection.empty(Position.in(row, column)));
            }
        }

        region.createGraph(tmp);
    }

    protected static Board buildTestBoard(int size) {
        return new Board(size);
    }

    public Region getRegion() {
        return this.region;
    }

    public Intersection intersectionAt(Position position) throws NoSuchElementException {
        return intersections.stream().filter(intersection -> intersection.isAt(position)).findFirst().orElseThrow();
    }

    public void addStoneAt(Stone stone, Position position) throws NoSuchElementException {
        Intersection intersection = intersectionAt(position);
        intersection.setStone(stone);
        updateChains(intersection);

        if(region.getGraph().vertexSet().stream().anyMatch(i -> i.isAt(position))){
            Intersection graphIntersection = region.getGraph().vertexSet().stream().filter(i -> i.isAt(position)).findFirst().get();
            region.removeVertex(graphIntersection); // we are sure that it is present
        }
    }

    private void updateChains(Intersection updatedIntersection) {
        List<Set<Intersection>> chainsOfGivenColor = chainsContainers.get(updatedIntersection.getStone());
        List<Set<Intersection>> oldChains = chainsOfGivenColor.stream()
                .filter(chain -> chain.stream().anyMatch(updatedIntersection::isOrthogonalTo))
                .collect(Collectors.toList());
        Set<Intersection> newChain = oldChains.stream().flatMap(Collection::stream).collect(Collectors.toSet());
        newChain.add(updatedIntersection);
        chainsOfGivenColor.removeAll(oldChains);
        chainsOfGivenColor.add(newChain);
    }

    public boolean isOccupied(Position position) throws NoSuchElementException {
        return intersectionAt(position).isOccupied();
    }

    public void pie() {
        edges.forEach(edgePart -> {
            if (edgePart.hasColor(Stone.BLACK)) {
                edgePart.setColor(Stone.WHITE);
            } else {
                edgePart.setColor(Stone.BLACK);
            }
        });
    }

    public boolean existsOrthogonallyAdjacentWithStone(Intersection intersection, Stone stone) {
        return intersections.stream()
                .filter(intersection::isOrthogonalTo)
                .anyMatch(orthogonalIntersection -> orthogonalIntersection.hasStone(stone));
    }

    public boolean existsDiagonallyAdjacentWithStone(Intersection intersection, Stone stone) {
        return intersections.stream()
                .filter(intersection::isDiagonalTo)
                .anyMatch(diagonalIntersection -> diagonalIntersection.hasStone(stone));
    }

    protected Stone colorWithCompleteChain() {
        return chainsContainers.entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .anyMatch(chain -> chain.stream()
                                .anyMatch(intersection -> isCloseToGivenEdge(intersection, TOP_AND_LEFT_EDGE_INDEX))
                                && chain.stream()
                                .anyMatch(intersection -> isCloseToGivenEdge(intersection, BOTTOM_AND_RIGHT_EDGE_INDEX))
                        )
                )
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(Stone.NONE);
    }

    private boolean isCloseToGivenEdge(Intersection intersection, int edgeIndex) {
        return edges.stream()
                .anyMatch(edge -> edge.hasColor(intersection.getStone())
                        && edge.getEdgeIndex() == edgeIndex
                        && edge.isAdjacentTo(intersection.getPosition())
                );
    }

    public List<Intersection> getOrthogonalAdjacencyIntersections(Intersection intersection) {
        return intersections.stream().filter(i -> i.isOrthogonalTo(intersection)).collect(Collectors.toList());
    }

    public List<Optional<Intersection>> getColoredIntersections(List<Intersection> intersections) {
        return intersections.stream().filter(Intersection::isOccupied).map(Optional::of).collect(Collectors.toList());
    }

    public List<List<Intersection>> getTerritories() {
        List<List<Intersection>> regions = region.getConnectedComponents();
        List<List<Intersection>> territories = new ArrayList<>();

        // cannot remove from regions (ConcurrenctModidification not allowed)
        regions.forEach(i -> {
            if(i.stream().allMatch(j -> getColoredIntersections(getOrthogonalAdjacencyIntersections(j)).size() >= 2)){
                territories.add(i);
            }
        });

        return territories;
    }

    protected Stream<Intersection> getEmptyIntersections() {
        return intersections.stream().filter(intersection -> !intersection.isOccupied());
    }
}