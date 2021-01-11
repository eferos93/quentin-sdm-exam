package sdmExam;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Board {
    public static final int BOARD_SIZE = 13;
    private final List<Intersection> intersections = new ArrayList<>();
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

    public ArrayList<ArrayList<Intersection>> findRegions() {
        ArrayList<ArrayList<Intersection>> regions = new ArrayList<>();
        ArrayList<Intersection> region = new ArrayList<>();

        for(int col = 1; col < BOARD_SIZE; col++){
            Intersection intersection = new Intersection(Position.in(1, col), Stone.NONE);
            region.add(intersection);
        }

        regions.add(region);
        return regions;
    }

    public boolean findIntersectionInRegions(ArrayList<ArrayList<Intersection>> regions, Intersection intersection) {
        boolean intersection_not_found = true;
        int i = 0;

        if(regions.isEmpty()) return true;

        while(i < regions.size() && intersection_not_found){
            if(regions.get(i).contains(intersection))
                intersection_not_found = false;
            i++;
        }

        return intersection_not_found;
    }

    protected Stream<Intersection> stream() {
        return intersections.stream();
    }
}