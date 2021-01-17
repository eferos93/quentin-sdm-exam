package sdmExam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    protected static final int DEFAULT_BOARD_SIZE = 13;
    private final int BOARD_SIZE;
    private final List<Intersection> intersections = new ArrayList<>();
    private final Region region = Region.getRegions();
    private final Set<Edge> edges = EnumSet.of(Edge.BOTTOM, Edge.TOP, Edge.LEFT, Edge.RIGHT);
    private final Map<Stone, Chain> chainsContainer = new HashMap<>() {{
        put(Stone.BLACK, new Chain());
        put(Stone.WHITE, new Chain());
    }};

    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    private Board(int boardSize) {
        this.BOARD_SIZE =  boardSize;
        Edge.setBoardSize(boardSize);
        this.edges.forEach(Edge::initialiseEdge);
        List<Intersection> tmp = new ArrayList<>();

        for (int row = 1; row <= this.BOARD_SIZE; row++) {
            for (int column = 1; column <= this.BOARD_SIZE; column++) {
                this.intersections.add(Intersection.empty(Position.in(row, column)));
                tmp.add(Intersection.empty(Position.in(row, column)));
            }
        }

        region.createGraph(tmp, boardSize);
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
        chainsContainer.get(updatedIntersection.getStone()).updateChain(updatedIntersection);
    }

    public boolean isOccupied(Position position) throws NoSuchElementException {
        return intersectionAt(position).isOccupied();
    }

    public boolean existsOrthogonallyAdjacentWithStone(Intersection intersection, Stone stone) {
        return intersections.stream()
                .anyMatch(otherIntersection ->
                        otherIntersection.isOrthogonalTo(intersection) && otherIntersection.hasStone(stone)
                );
    }

    public boolean existsDiagonallyAdjacentWithStone(Intersection intersection, Stone stone) {
        return intersections.stream()
                .anyMatch(otherIntersection ->
                        otherIntersection.isDiagonalTo(intersection) && otherIntersection.hasStone(stone)
                );
    }

    private List<Edge> getEdgesOfColor(Stone color) {
        return edges.stream().filter(edge -> edge.hasColor(color)).collect(Collectors.toList());
    }

    protected Stone colorWithCompleteChain() {
        return chainsContainer.entrySet().stream()
                .filter(entry -> entry.getValue().hasACompleteChain(getEdgesOfColor(entry.getKey())))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(Stone.NONE);
    }

    protected Stream<Intersection> getEmptyIntersections() {
        return intersections.stream().filter(intersection -> !intersection.isOccupied());
    }

    public List<Optional<Intersection>> getColoredIntersections(List<Intersection> intersections) {
        return intersections.stream().filter(Intersection::isOccupied).map(Optional::of).collect(Collectors.toList());
    }

    public List<Set<Intersection>> getTerritories() {
        List<Set<Intersection>> regions = region.getConnectedComponents();
        List<Set<Intersection>> territories = new ArrayList<>();

        // cannot remove from regions (ConcurrenctModidification not allowed)
        regions.forEach(i -> {
            if(i.stream().allMatch(j -> getColoredIntersections(getOrthogonalAdjacencyIntersections(j)).size() >= 2)){
                territories.add(i);
            }
        });

        return territories;
    }

    public List<Intersection> getOrthogonalAdjacencyIntersections(Intersection intersection) {
        return intersections.stream().filter(i -> i.isOrthogonalTo(intersection)).collect(Collectors.toList());
    }
}