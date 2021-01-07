package sdmExam;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {
    public static final int BOARD_SIZE = 13;
    private final List<Intersection> intersections = new ArrayList<>();
    //private List<Set<Intersection>> blackChains = new ArrayList<>();
    //private List<Set<Intersection>> whiteChains = new ArrayList<>();
    private Map<Stone, List<Set<Intersection>>> chainsContainers = new HashMap<>();
    private Stone lowerAndUpperEdgesColor = Stone.BLACK;
    private Stone leftAndRightEdgesColor = Stone.WHITE;

    public Board() {
        for (int row = 1; row <= BOARD_SIZE; row++) {
            for (int column = 1; column <= BOARD_SIZE; column++) {
                intersections.add(Intersection.empty(Position.in(row, column)));
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

    public List<Intersection> findChains(Intersection intersection) {

        // Here something need to be discussed about the design:
        // are we gonna store chains or check in every turn?

        // Do we need to check starting from last Stone or do we need
        // to check the entire board?

        Intersection intersection1 = this.intersectionAt(Position.in(11, 5));
        Intersection intersection2 = this.intersectionAt(Position.in(12, 5));
        Intersection intersection3 = this.intersectionAt(Position.in(13, 5));

        return Arrays.asList(intersection1, intersection2, intersection3);
    }

    public boolean stoneWithCompleteChain() {
        return false;
    }
}