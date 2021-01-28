package quentin.core;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static quentin.core.Position.in;

public class Board {
    private final int BOARD_SIZE;
    private final List<Intersection> intersections = new ArrayList<>();
    private final RegionContainer regionsContainer = RegionContainer.getRegionsContainer();
    private final Set<BoardSide> sides = EnumSet.of(BoardSide.BOTTOM, BoardSide.TOP, BoardSide.LEFT, BoardSide.RIGHT);
    private final Map<Stone, ChainContainer> chainsContainer = new HashMap<>() {{
        put(Stone.BLACK, new ChainContainer());
        put(Stone.WHITE, new ChainContainer());
    }};

    private Board(int boardSize) {
        this.BOARD_SIZE = boardSize;
        BoardSide.setBoardSize(boardSize);
        this.sides.forEach(BoardSide::initialiseSide);
        for (int row = 1; row <= this.BOARD_SIZE; row++) {
            for (int column = 1; column <= this.BOARD_SIZE; column++) {
                this.intersections.add(Intersection.empty(in(row, column)));
            }
        }
        regionsContainer.createGraph(this.intersections, boardSize);
    }

    public static Board buildBoard(int size) {
        return new Board(size);
    }

    public Intersection intersectionAt(Position position) throws NoSuchElementException {
        return intersections.stream().filter(intersection -> intersection.isAt(position)).findFirst().orElseThrow();
    }

    public void addStoneAt(Stone stone, Position position) throws NoSuchElementException {
        Intersection intersection = intersectionAt(position);
        regionsContainer.removeNonEmptyIntersection(intersection);
        intersection.setStone(stone);
        chainsContainer.get(stone).updateChain(intersection);
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

    private List<BoardSide> getSidesOfColor(Stone color) {
        return sides.stream().filter(edge -> edge.hasColor(color)).collect(Collectors.toList());
    }

    protected Stone colorWithCompleteChain() {
        return chainsContainer.entrySet().stream()
                .filter(entry -> entry.getValue().hasACompleteChain(getSidesOfColor(entry.getKey())))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(Stone.NONE);
    }

    protected Stream<Intersection> getEmptyIntersections() {
        return intersections.stream().filter(intersection -> !intersection.isOccupied());
    }

    protected void fillTerritories(Stone lastPlay) {
        regionsContainer.getTerritoriesAndStonesToFill(intersections, lastPlay)
                .forEach((territory, stone) -> territory.stream()
                        .map(Intersection::getPosition)
                        .forEach(emptyIntersectionPosition -> addStoneAt(stone, emptyIntersectionPosition))
                );
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }
}