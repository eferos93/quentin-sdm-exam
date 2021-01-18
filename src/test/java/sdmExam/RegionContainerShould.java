package sdmExam;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegionContainerShould {

    @Test
    public void initialize2x2GridRegion(){

        int boardSize = 2;
        Board customBoard = Board.buildTestBoard(boardSize);
        List<Intersection> emptyIntersections = customBoard.getEmptyIntersections().collect(Collectors.toList());
        Graph<Intersection, DefaultEdge> graph = new SimpleGraph<>(new Supplier<>() {
            private int index = 0;

            @Override
            public Intersection get() {
                return emptyIntersections.get(index++);
            }
        }, SupplierUtil.createDefaultEdgeSupplier(), false);

        new GridGraphGenerator<Intersection, DefaultEdge>(boardSize, boardSize).generateGraph(graph, null);

        RegionContainer region = RegionContainer.getRegionsContainer();
        assertEquals(new ConnectivityInspector<>(graph).connectedSets(), region.getRegions());
    }

    @Test
    public void dropVertexCorrectly() {
        Board customBoard = Board.buildTestBoard(4);
        RegionContainer expectedRegion = RegionContainer.getRegionsContainer();
        Position position = Position.in(2, 2);
        customBoard.addStoneAt(Stone.BLACK, position);
        Intersection removedIntersection = customBoard.intersectionAt(position);
        assertTrue(expectedRegion.getRegions().stream().noneMatch(region -> region.contains(removedIntersection)));
    }

}