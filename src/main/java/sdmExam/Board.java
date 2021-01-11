package sdmExam;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    private final static int DEFAULT_PLAYABLE_BOARD_SIZE = 13;
    private final int boardSize;
    private final List<Intersection> intersections = new ArrayList<>();
    private final List<Intersection> edges = new ArrayList<>();
    private Map<Stone, Optional<List<Set<Intersection>>>> chainsContainers = new HashMap<>();

    public Board() {
        this(DEFAULT_PLAYABLE_BOARD_SIZE);
    }

    private Board(int playableBoardSize) {
        this.boardSize = playableBoardSize + 1;
        chainsContainers.put(Stone.BLACK, Optional.empty());
        chainsContainers.put(Stone.WHITE, Optional.empty());
        for (int row = 0; row <= boardSize; row++) {
            for (int column = 0; column <= boardSize; column++) {
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

    protected static Board buildTestBoard(int size) {
        return new Board(size);
    }

    private boolean isPartOfLeftOrRightEdge(Position position) {
        return position.getColumn() == 0 || position.getColumn() == boardSize;
    }

    private boolean isPartOfLowerOrUpperEdge(Position position) {
        return position.getRow() == 0 || position.getRow() == boardSize;
    }

    private boolean isACorner(Position position) {
        return (isPartOfLowerOrUpperEdge(position))
                && (isPartOfLeftOrRightEdge(position));
    }

    public Intersection intersectionAt(Position position) throws NoSuchElementException {
        return intersections.stream().filter(intersection -> intersection.isAt(position)).findFirst().orElseThrow();
    }

    public void addStoneAt(Stone stone, Position position) throws NoSuchElementException {
        Intersection intersection = intersectionAt(position);
        intersection.setStone(stone);
        chainsContainers.get(stone).ifPresentOrElse(chainsOfGivenColor -> {
            List<Set<Intersection>> oldChains = chainsOfGivenColor.stream()
                    .filter(chain -> chain.stream().anyMatch(intersection::isOrthogonalTo)).collect(Collectors.toList());
            Set<Intersection> newChain = oldChains.stream().flatMap(Collection::stream).collect(Collectors.toSet());
            newChain.add(intersection);
            chainsOfGivenColor.removeAll(oldChains);
            chainsOfGivenColor.add(newChain);
        }, () -> {
            List<Set<Intersection>> listOfChains = new ArrayList<>();
            Set<Intersection> newChain = new HashSet<>();
            newChain.add(intersection);
            listOfChains.add(newChain);
            chainsContainers.put(stone, Optional.of(listOfChains));
        });
    }

    public boolean isOccupied(Position position) throws NoSuchElementException {
        return intersectionAt(position).isOccupied();
    }

    public void pie() {
        edges.forEach(edgePart -> {
            if (edgePart.hasStone(Stone.BLACK)) {
                edgePart.setStone(Stone.WHITE);
            } else {
                edgePart.setStone(Stone.BLACK);
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

    protected Stream<Intersection> stream() {
        return intersections.stream();
    }

    protected Stone colorWithCompleteChain() {
        for (Map.Entry<Stone, Optional<List<Set<Intersection>>>> entry : chainsContainers.entrySet()) {
            for (Set<Intersection> chain : entry.getValue().get()) {
                AtomicBoolean isCloseToFirstEdgeOfGivenColor = new AtomicBoolean(false);
                AtomicBoolean isCloseToSecondEdgeOfGivenColor = new AtomicBoolean(false);
                chain.forEach(intersection -> {
                    if (isCloseToFirstEdgeOfSameColor(intersection)) {
                        isCloseToFirstEdgeOfGivenColor.set(true);
                    } else if (isCloseToSecondEdgeOfSameColor(intersection)) {
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

    private boolean isCloseToSecondEdgeOfSameColor(Intersection intersection) {
        return isCloseToColorAlikeEdgeFromSide(intersection,
                edgePart -> edgePart.getPosition().isOnTheRightWithRespectTo(intersection.getPosition()))
                ||
                isCloseToColorAlikeEdgeFromSide(intersection,
                        edgePart -> edgePart.getPosition().isBelowWithRespectTo(intersection.getPosition()));
    }

    private boolean isCloseToFirstEdgeOfSameColor(Intersection intersection) {
        return isCloseToColorAlikeEdgeFromSide(intersection,
                edgePart -> edgePart.getPosition().isOnTheLeftWithRespectTo(intersection.getPosition()))
                ||
                isCloseToColorAlikeEdgeFromSide(intersection,
                    edgePart -> edgePart.getPosition().isAboveWithRespectTo(intersection.getPosition()));
    }

    private Boolean isCloseToColorAlikeEdgeFromSide(Intersection intersection, Predicate<Intersection> condition) {
        return edges.stream()
                .filter(condition)
                .findFirst()
                .map(foundEdgePart -> foundEdgePart.hasStone(intersection.getStone()))
                .orElse(false);
    }

    public Stone edgeColorAt(Position position) throws NoSuchElementException {
        return edges.stream()
                .filter(edgeElement -> edgeElement.isAt(position))
                .findFirst()
                .map(Intersection::getStone)
                .orElseThrow();
    }
}