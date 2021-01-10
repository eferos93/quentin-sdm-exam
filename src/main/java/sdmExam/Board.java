package sdmExam;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class Board {
    public static final int BOARD_SIZE = 14;
    private final List<Intersection> intersections = new ArrayList<>();
    private final List<Intersection> edges = new ArrayList<>();
    private Map<Stone, List<Set<Intersection>>> chainsContainers = new HashMap<>();
    private Stone lowerAndUpperEdgesColor = Stone.BLACK;
    private Stone leftAndRightEdgesColor = Stone.WHITE;

    public Board() {
        for (int row = 0; row <= BOARD_SIZE; row++) {
            for (int column = 0; column <= BOARD_SIZE; column++) {
                final Position position = Position.in(row, column);
                if (isACorner(position)) {
                    continue;
                }
                if (isPartOfLowerOrUpperEdge(position)) {
                    edges.add(new Intersection(position, Stone.BLACK));
                } else if (isPartOfLeftOrRightEdge(position)) {
                    edges.add(new Intersection(position, Stone.WHITE));
                } else {
                    intersections.add(Intersection.empty(position));
                }
            }
        }
    }

    private boolean isPartOfLeftOrRightEdge(Position position) {
        return position.getColumn() == 0 || position.getColumn() == BOARD_SIZE;
    }

    private boolean isPartOfLowerOrUpperEdge(Position position) {
        return position.getRow() == 0 || position.getRow() == BOARD_SIZE;
    }

    private boolean isACorner(Position position) {
        return (isPartOfLowerOrUpperEdge(position))
                && (isPartOfLeftOrRightEdge(position));
    }

    private Board(int size) {
        for (int row = 1; row <= size; row++) {
            for (int column = 1; column <= size; column++) {
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
        intersectionAt(position).setStone(stone);
    }

    public void setLowerAndUpperEdgesColor(Stone color) {
        this.lowerAndUpperEdgesColor = color;
    }

    public void setLeftAndRightEdgesColor(Stone color) {
        this.leftAndRightEdgesColor = color;
    }

    public Stone getLowerAndUpperEdgesColor() {
        return lowerAndUpperEdgesColor;
    }

    public Stone getLeftAndRightEdgesColor() {
        return leftAndRightEdgesColor;
    }

    public boolean isOccupied(Position position) throws NoSuchElementException {
        return intersectionAt(position).isOccupied();
    }

    public void pie() {
        setLowerAndUpperEdgesColor(Stone.WHITE);
        setLeftAndRightEdgesColor(Stone.BLACK);
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

    protected Stream<Intersection> stream() {
        return intersections.stream();
    }

    protected Stone colorWithCompleteChain() {
        for (Map.Entry<Stone, List<Set<Intersection>>> entry : chainsContainers.entrySet()) {
            for (Set<Intersection> chain : entry.getValue()) {
                AtomicBoolean isCloseToFirstEdgeOfGivenColor = new AtomicBoolean(false);
                AtomicBoolean isCloseToSecondEdgeOfGivenColor = new AtomicBoolean(false);
                chain.forEach(intersection -> {
                    if (isCloseToFirstEdgeOfColor(entry.getKey(), intersection)) {
                        isCloseToFirstEdgeOfGivenColor.set(true);
                    } else if (isCloseToSecondEdgeOfColor(entry.getKey(), intersection)) {
                        isCloseToSecondEdgeOfGivenColor.set(true);
                    }
                });
                if (isCloseToFirstEdgeOfGivenColor.get() && isCloseToSecondEdgeOfGivenColor.get()) {
                    return entry.getKey();
                }
            }
        }
        return Stone.NONE;
    }

    private boolean isCloseToSecondEdgeOfColor(Stone color, Intersection intersection) {
        return false;
    }

    private boolean isCloseToFirstEdgeOfColor(Stone color, Intersection intersection) {
        return false;
    }

    public Stone edgeColorAt(Position position) throws NoSuchElementException {
        return edges.stream()
                .filter(edgeElement -> edgeElement.isAt(position))
                .findFirst()
                .map(Intersection::getStone)
                .orElseThrow();
    }
}