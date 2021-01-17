package sdmExam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static sdmExam.Position.in;

public class Board {
    protected static final int DEFAULT_BOARD_SIZE = 13;
    private final int BOARD_SIZE;
    private final List<Intersection> intersections = new ArrayList<>();
    private final RegionContainer regionsContainer = RegionContainer.getRegionsContainer();
    private final Set<Edge> edges = EnumSet.of(Edge.BOTTOM, Edge.TOP, Edge.LEFT, Edge.RIGHT);
    private final Map<Stone, Chain> chainsContainer = new HashMap<>() {{
        put(Stone.BLACK, new Chain());
        put(Stone.WHITE, new Chain());
    }};

    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    private Board(int boardSize) {
        this.BOARD_SIZE =  boardSize;
        Edge.setBoardSize(boardSize);
        this.edges.forEach(Edge::initialiseEdge);

        for (int row = 1; row <= this.BOARD_SIZE; row++) {
            for (int column = 1; column <= this.BOARD_SIZE; column++) {
                this.intersections.add(Intersection.empty(in(row, column)));
            }
        }

        regionsContainer.createGraph(this.intersections, boardSize);
    }

    protected static Board buildTestBoard(int size) {
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

    public boolean isOccupied(Position position) throws NoSuchElementException {
        return intersectionAt(position).isOccupied();
    }

    public boolean existsOrthogonallyAdjacentWithStone(Intersection intersection, Stone stone) {
        return intersections.stream()
                .anyMatch(otherIntersection ->
                        otherIntersection.isOrthogonalTo(intersection) && otherIntersection.hasStone(stone)
                );
    }

    public boolean existsDiagonallyAdjacentWithStone(Intersection intersection, Stone stone) {
        return intersections.stream()
                .anyMatch(otherIntersection ->
                        otherIntersection.isDiagonalTo(intersection) && otherIntersection.hasStone(stone)
                );
    }

    private List<Edge> getEdgesOfColor(Stone color) {
        return edges.stream().filter(edge -> edge.hasColor(color)).collect(Collectors.toList());
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
    public List<Set<Intersection>> getTerritories() {
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

    public List<Intersection> getOrthogonalAdjacencyIntersections(Intersection intersection) {
        return intersections.stream()
                .filter(otherIntersection -> otherIntersection.isOrthogonalTo(intersection))
                .collect(Collectors.toList());
    }

    public void fillTerritory(List<Intersection> territory, Stone lastplay){


        int i;
        int j;

        List<Intersection> neutralList = new ArrayList<>();
        List<Intersection> listAdj;

        for(i=0; i<territory.size(); i++) {
            listAdj = getOrthogonalAdjacencyIntersections(territory.get(i));
            for(j=0;j<getOrthogonalAdjacencyIntersections(territory.get(i)).size();j++){
                neutralList.add(listAdj.get(j));
            }
            listAdj.clear();
        }
        int counterBlackStone = 0;
        int counterWhiteStone = 0;
        for(i=0;i<neutralList.size();i++){
            if(neutralList.get(i).getStone()== Stone.BLACK)
                counterBlackStone++;
            else if(neutralList.get(i).getStone() == Stone.WHITE)
                counterWhiteStone++;
        }
        if(counterBlackStone>counterWhiteStone) {
            IntStream.range(1, territory.size()).forEach(index -> territory.get(index).setStone(Stone.BLACK));
        }else if(counterWhiteStone>counterBlackStone){
            IntStream.range(1, territory.size()).forEach(index -> territory.get(index).setStone(Stone.WHITE));
        }else {
            if (lastplay == Stone.BLACK)
                IntStream.range(1, territory.size()).forEach(index -> territory.get(index).setStone(Stone.WHITE));
            else //it means that lasPlay was performed by White
                IntStream.range(1, territory.size()).forEach(index -> territory.get(index).setStone(Stone.BLACK));
        }

    }

}