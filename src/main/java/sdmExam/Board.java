package sdmExam;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
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

    public List<Intersection> getOrthogonalAdjacencyIntersections(Intersection intersection) {
        return intersections.stream().filter(i -> i.isOrthogonalTo(intersection)).collect(Collectors.toList());
    }

    public ArrayList<ArrayList<Intersection>> findTerritories() {
        ArrayList<ArrayList<Intersection>> regions = findRegions();
        regions.removeIf(region -> !isTerritory(region));
        return regions;
    }

    public boolean isTerritory(ArrayList<Intersection> region) {
        int index, i = 0, number_adjacency = 0;
        boolean adjacency = true;
        while(i < region.size() && adjacency){

            index = intersections.indexOf(region.get(i));

            if(!notExistLeftIntersection(index))
                if(!region.contains(intersections.get(index - 1)))
                    number_adjacency++;

            if(!notExistRightIntersection(index))
                if(!region.contains(intersections.get(index + 1)))
                    number_adjacency++;

            if(!notExistDownIntersection(index))
                if(!region.contains(intersections.get(index - BOARD_SIZE)))
                    number_adjacency++;

            if(!notExistUpIntersection(index))
                if(!region.contains(intersections.get(index + BOARD_SIZE)))
                    number_adjacency++;

            if(number_adjacency < 2) adjacency = false;

            i++;
        }

        return adjacency;
    }

    public ArrayList<ArrayList<Intersection>> findRegions() {
        ArrayList<ArrayList<Intersection>> regions = new ArrayList<>();

        int h = intersections.size();
        if (h == 0)
            return regions;

        for (int i = 0; i < h; i++) {
            if (!intersections.get(i).isOccupied() && findIntersectionInRegions(regions, intersections.get(i))) {
                ArrayList<Intersection> region = new ArrayList<>();
                DFS(i, region);
                regions.add(region);
            }
        }
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

    public void DFS(int index, ArrayList<Intersection> region) {
        boolean go_left = true, go_right = true,
                go_up = true, go_down = true;

        System.out.print("Index: " + index + " ");

        if (region.contains(intersections.get(index)) ||
                intersections.get(index).isOccupied()) {
            System.out.println("intersection is not accectable");
            return;
        }

        System.out.print(intersections.get(index).getPosition().toString());
        System.out.println(" " + intersections.get(index).getStone());
        region.add(intersections.get(index));

        if(notExistLeftIntersection(index)) go_left = false;
        if(notExistRightIntersection(index)) go_right = false;

        if(notExistUpIntersection(index)) go_up = false;
        if(notExistDownIntersection(index)) go_down = false;

        System.out.print("L:" + go_left + " ");
        System.out.print("R:" + go_right + " ");
        System.out.print("U:" + go_up + " ");
        System.out.println("D:" + go_down);

        if(go_left && go_right){
            System.out.println("going right " + (index + 1) +" and ...");
            DFS(index + 1, region); //go right
            System.out.println("... going left " + (index - 1));
            DFS(index - 1, region); // go left
        }else if(go_left && !go_right){
            System.out.println("going left " + (index - 1));
            DFS(index - 1, region); //go left
        }else if(go_right && !go_left){
            System.out.println("going right " + (index + 1));
            DFS(index + 1, region); // go right
        }

        if(go_up && go_down){
            DFS(index + BOARD_SIZE, region); //go up
            DFS(index - BOARD_SIZE, region); // go down
        }else if(go_up && !go_down){
            DFS(index - BOARD_SIZE, region); // go down
        }else if(go_down && !go_up){
            DFS(index + BOARD_SIZE, region); //go up
        }

        System.out.println("ONE REGION REACHED");
    }

    private boolean notExistDownIntersection(int index) {
        return index/BOARD_SIZE == BOARD_SIZE;
    }

    private boolean notExistUpIntersection(int index) {
        return index/BOARD_SIZE == 0 && index + BOARD_SIZE != 0;
    }

    private boolean notExistLeftIntersection(int index) {
        return (index%BOARD_SIZE == 1 || index == 0) && index - 1 != 0;
    }

    private boolean notExistRightIntersection(int index) {
        return ((index%BOARD_SIZE == 0 && index != 0) ||
                index == BOARD_SIZE*BOARD_SIZE);
    }

    protected Stream<Intersection> stream() {
        return intersections.stream();
    }
}