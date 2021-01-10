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
                if (row == 0 && column == 0 || row == 0 && column == BOARD_SIZE || row == BOARD_SIZE && column == 0 || row == BOARD_SIZE && column == BOARD_SIZE) {
                    continue;
                }
                if (row == 0 || row == BOARD_SIZE) {
                    edges.add(new Intersection(Position.in(row, column), Stone.BLACK));
                } else if (column == 0 || column == BOARD_SIZE) {
                    edges.add(new Intersection(Position.in(row, column), Stone.WHITE));
                } else {
                    intersections.add(Intersection.empty(Position.in(row, column)));
                }
            }
        }
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
}