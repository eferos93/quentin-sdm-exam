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
    private final Set<Edge> edges = EnumSet.of(Edge.BOTTOM, Edge.TOP, Edge.LEFT, Edge.RIGHT);
//    private final Map<Stone, List<Set<Intersection>>> chainsContainers = new HashMap<>();
    private final Map<Stone, Chain> chainsContainer = new HashMap<>();

    public Set<Edge> getEnumSet() {
        return edges;
    }

    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    private Board(int boardSize) {
        this.BOARD_SIZE = boardSize;
        this.BOTTOM_AND_RIGHT_EDGE_INDEX = boardSize + 1;
        this.chainsContainer.put(Stone.BLACK, new Chain());
        this.chainsContainer.put(Stone.WHITE, new Chain());
        this.edges.forEach(edge -> edge.initialiseEdge(BOTTOM_AND_RIGHT_EDGE_INDEX));
        for (int row = 1; row <= this.BOARD_SIZE; row++) {
            for (int column = 1; column <= this.BOARD_SIZE; column++) {
                this.intersections.add(Intersection.empty(Position.in(row, column)));
            }
        }
    }

    protected static Board buildTestBoard(int size) {
        return new Board(size);
    }

    public Intersection intersectionAt(Position position) throws NoSuchElementException {
        return intersections.stream().filter(intersection -> intersection.isAt(position)).findFirst().orElseThrow();
    }

    public void addStoneAt(Stone stone, Position position) throws NoSuchElementException {
        Intersection intersection = intersectionAt(position);
        intersection.setStone(stone);
        updateChains(intersection);
    }

    private void updateChains(Intersection updatedIntersection) {
        chainsContainer.get(updatedIntersection.getStone()).updateChain(updatedIntersection);
//        List<Set<Intersection>> chainsOfGivenColor = chainsContainer.get(updatedIntersection.getStone());
//        List<Set<Intersection>> oldChains = chainsOfGivenColor.stream()
//                .filter(chain -> chain.stream().anyMatch(updatedIntersection::isOrthogonalTo))
//                .collect(Collectors.toList());
//        Set<Intersection> newChain = oldChains.stream().flatMap(Collection::stream).collect(Collectors.toSet());
//        newChain.add(updatedIntersection);
//        chainsOfGivenColor.removeAll(oldChains);
//        chainsOfGivenColor.add(newChain);
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

    private List<Edge> getEdgesOfColor(Stone color) {
        return edges.stream().filter(edge -> edge.hasColor(color)).collect(Collectors.toList());
    }

    protected Stone colorWithCompleteChain() {
        return chainsContainer.entrySet().stream()
                .filter(entry -> entry.getValue().hasACompleteChain(getEdgesOfColor(entry.getKey())))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(Stone.NONE);
//        return chainsContainers.entrySet().stream()
//                .filter(entry -> entry.getValue().stream()
//                        .anyMatch(chain -> chain.stream()
//                                .anyMatch(intersection -> isCloseToGivenEdge(intersection, TOP_AND_LEFT_EDGE_INDEX))
//                                && chain.stream()
//                                .anyMatch(intersection -> isCloseToGivenEdge(intersection, BOTTOM_AND_RIGHT_EDGE_INDEX))
//                        )
//                )
//                .map(Map.Entry::getKey)
//                .findFirst()
//                .orElse(Stone.NONE);
    }

    private boolean isCloseToGivenEdge(Intersection intersection, int edgeIndex) {
        return edges.stream()
                .anyMatch(edge -> edge.hasColor(intersection.getStone())
                        && edge.getEdgeIndex() == edgeIndex
                        && edge.isAdjacentTo(intersection.getPosition())
                );
    }

    protected Stream<Intersection> getEmptyIntersections() {
        return intersections.stream().filter(intersection -> !intersection.isOccupied());
    }
}