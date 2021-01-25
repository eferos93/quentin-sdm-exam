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
    private final Map<Stone, Chain> chainsContainer = new HashMap<>() {{
        put(Stone.BLACK, new Chain());
        put(Stone.WHITE, new Chain());
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
        regionsContainer.updateRegionContainer(intersection);
        intersection.setStone(stone);
        updateChains(intersection);
    }

    private void updateChains(Intersection updatedIntersection) {
        chainsContainer.get(updatedIntersection.getStone()).updateChain(updatedIntersection);
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

    boolean existsDiagonallyAdjacentWithStone(Intersection intersection, Stone stone) {
        return intersections.stream()
                .anyMatch(otherIntersection ->
                        otherIntersection.isDiagonalTo(intersection) && otherIntersection.hasStone(stone)
                );
    }

    private List<BoardSide> getEdgesOfColor(Stone color) {
        return sides.stream().filter(edge -> edge.hasColor(color)).collect(Collectors.toList());
    }

    protected Stone colorWithCompleteChain() {
        return chainsContainer.entrySet().stream()
                .filter(entry -> entry.getValue().hasACompleteChain(getEdgesOfColor(entry.getKey())))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(Stone.NONE);
    }

    protected Stream<Intersection> getEmptyIntersections() {
        return intersections.stream().filter(intersection -> !intersection.isOccupied());
    }

    //TODO: don't know if it's useful to get the territories or just act on them
    protected List<Set<Intersection>> getTerritories() {
        return regionsContainer.getRegions().stream()
                .filter(region -> region.stream().allMatch(this::isOrthogonalToAtLeastTwoStones))
                .collect(Collectors.toList());
    }

    private boolean isOrthogonalToAtLeastTwoStones(Intersection intersection) {
        return intersections.stream()
                .filter(intersection::isOrthogonalTo)
                .filter(Intersection::isOccupied)
                .count() >= 2;
    }

    protected List<Intersection> getOrthogonalAdjacencyIntersections(Intersection intersection) {
        return intersections.stream()
                .filter(otherIntersection -> otherIntersection.isOrthogonalTo(intersection))
                .collect(Collectors.toList());
    }

    //TODO: code smell long method, need to refactor
    protected Stone getStoneToFillTerritory(Set<Intersection> territory, Stone lastPlay) {
        Set<Intersection> intersectionsSurroundingTerritory = territory.stream()
                .flatMap(intersection -> getOrthogonalAdjacencyIntersections(intersection).stream())
                .filter(Intersection::isOccupied)
                .collect(Collectors.toSet());

        long countOfWhiteStones = countIntersectionsOfColor(intersectionsSurroundingTerritory, Stone.WHITE);
        long countOfBlackStones = countIntersectionsOfColor(intersectionsSurroundingTerritory, Stone.BLACK);

        Stone stone;

        if (countOfWhiteStones != countOfBlackStones) {
            stone = (countOfWhiteStones < countOfBlackStones) ? Stone.BLACK : Stone.WHITE;
        } else {
            stone = lastPlay.getOppositeColor();
        }
        return stone;
    }

    private long countIntersectionsOfColor(Set<Intersection> intersections, Stone color) {
        return intersections.stream()
                .filter(intersection -> intersection.hasStone(color))
                .count();
    }

    protected void fillTerritory(Set<Intersection> territory, Stone lastPlay) {
        Stone territoryStoneColor = getStoneToFillTerritory(territory, lastPlay);
        territory.forEach(intersection -> this.addStoneAt(territoryStoneColor, intersection.getPosition()));
    }

    private void searchAndFillTerritories(Stone lastPlay) {
        getTerritories().forEach(territory -> fillTerritory(territory, lastPlay));
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }
}