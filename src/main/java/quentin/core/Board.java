package quentin.core;

import quentin.exceptions.OutsideOfBoardException;

import java.util.*;
import java.util.stream.Collectors;
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

    public Intersection intersectionAt(Position position) throws OutsideOfBoardException {
        return intersections.stream().filter(intersection -> intersection.isAt(position)).findFirst().orElseThrow(
                () -> new OutsideOfBoardException(position)
        );
    }

    protected void addStoneAt(Colour colour, Position position) throws OutsideOfBoardException {
        Intersection intersection = intersectionAt(position);
        regionsContainer.removeIntersection(intersection);
        intersection.setStone(colour);
        chainContainer.updateChain(intersection);
    }

    protected boolean isOccupied(Position position) throws OutsideOfBoardException {
        return intersectionAt(position).isOccupied();
    }

    protected Set<Intersection> getDiagonallyAdjacentIntersectionsOfColour(Intersection intersection) {
        return intersections.stream()
                .filter(intersection::isDiagonalTo)
                .filter(diagonalIntersection -> diagonalIntersection.hasStone(intersection.getColour().orElseThrow()))
                .collect(Collectors.toUnmodifiableSet());
    }

    protected Set<Intersection> getOrthogonallyAdjacentIntersectionsOfColour(Intersection intersection) {
        return intersections.stream()
                .filter(intersection::isOrthogonalTo)
                .filter(orthogonalIntersection -> orthogonalIntersection.hasStone(intersection.getColour().orElseThrow()))
                .collect(Collectors.toUnmodifiableSet());
    }

    protected Optional<Colour> colorWithCompleteChain() {
        return chainContainer.getColorWithCompleteChain();
    }

    protected Stream<Intersection> getEmptyIntersections() {
        return intersections.stream().filter(Intersection::isEmpty);
    }

    public Stream<Intersection> getNonEmptyIntersections() {
        return intersections.stream().filter(Intersection::isOccupied);
    }

    protected Set<Position> fillTerritories(Colour lastPlay) {
        Map<Set<Intersection>, Optional<Colour>> territoriesToFill =
                regionsContainer.getTerritoriesAndStonesToFill(lastPlay);
        territoriesToFill
                .forEach((territory, stone) ->
                                territory.stream()
                                        .map(Intersection::getPosition)
                                        .forEach(emptyIntersectionPosition -> addStoneAt(stone.orElseThrow(), emptyIntersectionPosition))
                );
        return territoriesToFill.entrySet().stream()
                .flatMap(entry -> entry.getKey().stream())
                .map(Intersection::getPosition)
                .collect(Collectors.toSet());
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    protected void revertForIntersectionAt(Position position) {
        Intersection intersection = intersectionAt(position);
        chainContainer.removeIntersection(intersection);
        intersection.setStone(null);
        regionsContainer.addIntersection(intersection);
    }
}