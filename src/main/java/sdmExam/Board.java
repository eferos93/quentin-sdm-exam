package sdmExam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    private final static int DEFAULT_BOARD_SIZE = 13;
    private final int TOP_AND_LEFT_EDGE_INDEX = 0;
    private final int BOTTOM_AND_RIGHT_EDGE_INDEX;
    private final int BOARD_SIZE;
    private final List<Intersection> intersections = new ArrayList<>();
    private final Set<Edge> edges = EnumSet.of(Edge.DOWN, Edge.UP, Edge.LEFT, Edge.RIGHT);
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
        chainsContainers.put(Stone.BLACK, new ArrayList<>());
        chainsContainers.put(Stone.WHITE, new ArrayList<>());
        edges.forEach(edge -> {
            switch (edge) {
                case UP, DOWN -> edge.setColor(Stone.BLACK);
                case LEFT, RIGHT -> edge.setColor(Stone.WHITE);
            }
        });
        for (int row = 1; row <= this.BOARD_SIZE; row++) {
            for (int column = 1; column <= this.BOARD_SIZE; column++) {
                intersections.add(Intersection.empty(Position.in(row, column)));
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
                        .anyMatch(chain -> chain.stream().anyMatch(this::isCloseToFirstEdgeOfSameColor)
                                && chain.stream().anyMatch(this::isCloseToSecondEdgeOfSameColor)))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(Stone.NONE);
    }

    private boolean isCloseToSecondEdgeOfSameColor(Intersection intersection) {
        return edges.stream()
                .anyMatch(edge ->
                        edge.isAdjacentTo(intersection.getPosition(), BOTTOM_AND_RIGHT_EDGE_INDEX)
                                && edge.hasColor(intersection.getStone()));
    }

    private boolean isCloseToFirstEdgeOfSameColor(Intersection intersection) {
        return edges.stream()
                .anyMatch(edge ->
                        edge.isAdjacentTo(intersection.getPosition(), TOP_AND_LEFT_EDGE_INDEX)
                                && edge.hasColor(intersection.getStone()));
    }

    protected Stream<Intersection> getEmptyIntersections() {
        return intersections.stream().filter(intersection -> !intersection.isOccupied());
    }
}