package quentin.core;

import java.util.*;
import java.util.stream.Stream;

import static quentin.core.Position.in;

public class Board {
    private final int BOARD_SIZE;
    private final List<Intersection> intersections = new ArrayList<>();
    private final RegionContainer regionsContainer;
    private final ChainContainer chainContainer;

    private Board(int boardSize) {
        this.BOARD_SIZE = boardSize;
        this.chainContainer = new ChainContainer(this.BOARD_SIZE);
        for (int row = 1; row <= this.BOARD_SIZE; row++) {
            for (int column = 1; column <= this.BOARD_SIZE; column++) {
                this.intersections.add(Intersection.empty(in(row, column)));
            }
        }
        regionsContainer = new RegionContainer(this.intersections, this.BOARD_SIZE);
    }

    public static Board buildBoard(int size) {
        return new Board(size);
    }

    public Intersection intersectionAt(Position position) throws NoSuchElementException {
        return intersections.stream().filter(intersection -> intersection.isAt(position)).findFirst().orElseThrow();
    }

    protected void addStoneAt(Stone stone, Position position) throws NoSuchElementException {
        Intersection intersection = intersectionAt(position);
        regionsContainer.removeNonEmptyIntersection(intersection);
        intersection.setStone(stone);
        chainContainer.updateChain(intersection);
    }

    protected boolean isOccupied(Position position) throws NoSuchElementException {
        return intersectionAt(position).isOccupied();
    }

    protected boolean existsOrthogonallyAdjacentWithStone(Intersection intersection, Stone stone) {
        return intersections.stream()
                .anyMatch(otherIntersection ->
                        otherIntersection.isOrthogonalTo(intersection) && otherIntersection.hasStone(stone)
                );
    }

    protected boolean existsDiagonallyAdjacentWithStone(Intersection intersection, Stone stone) {
        return intersections.stream()
                .anyMatch(otherIntersection ->
                        otherIntersection.isDiagonalTo(intersection) && otherIntersection.hasStone(stone)
                );
    }

    protected Stone colorWithCompleteChain() {
        return chainContainer.getColorWithCompleteChain();
    }

    protected Stream<Intersection> getEmptyIntersections() {
        return intersections.stream().filter(intersection -> !intersection.isOccupied());
    }

    protected void fillTerritories(Stone lastPlay) {
        getTerritoriesAndStones(lastPlay)
                        .forEach((territory, stone) -> territory.stream()
                        .map(Intersection::getPosition)
                        .forEach(emptyIntersectionPosition -> addStoneAt(stone, emptyIntersectionPosition))
                );
    }

    protected Map<Set<Intersection>, Stone> getTerritoriesAndStones(Stone lastPlay){
        return regionsContainer.getTerritoriesAndStonesToFill(lastPlay);
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }
}