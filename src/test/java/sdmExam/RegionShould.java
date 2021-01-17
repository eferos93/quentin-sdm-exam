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

public class RegionShould {

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
//
//    private static final PrintStream STDOUT = System.out;
//    private static final String GRAPH_2_PER_2 = "Vertex Position{row=1, column=1} is connected to: [(Intersection{position=Position{row=1, column=1}, stone=NONE} : Intersection{position=Position{row=1, column=2}, stone=NONE}), (Intersection{position=Position{row=1, column=1}, stone=NONE} : Intersection{position=Position{row=2, column=1}, stone=NONE})]\n" +
//"Vertex Position{row=2, column=1} is connected to: [(Intersection{position=Position{row=1, column=1}, stone=NONE} : Intersection{position=Position{row=2, column=1}, stone=NONE}), (Intersection{position=Position{row=2, column=1}, stone=NONE} : Intersection{position=Position{row=2, column=2}, stone=NONE})]\n" +
//"Vertex Position{row=2, column=2} is connected to: [(Intersection{position=Position{row=1, column=2}, stone=NONE} : Intersection{position=Position{row=2, column=2}, stone=NONE}), (Intersection{position=Position{row=2, column=1}, stone=NONE} : Intersection{position=Position{row=2, column=2}, stone=NONE})]\n" +
//"Vertex Position{row=1, column=2} is connected to: [(Intersection{position=Position{row=1, column=1}, stone=NONE} : Intersection{position=Position{row=1, column=2}, stone=NONE}), (Intersection{position=Position{row=1, column=2}, stone=NONE} : Intersection{position=Position{row=2, column=2}, stone=NONE})]\n";
//
//    @Test
//    void correctlyPrintRegion() {
//        List<Intersection> intersections = new ArrayList<>();
//        for (int row = 1; row <= 2; row++) {
//            for (int column = 1; column <= 2; column++) {
//                intersections.add(Intersection.empty(Position.in(row, column)));
//            }
//        }
//
//        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(fakeStandardOutput));
//
//        Region region = Region.getRegions();
//        region.createGraph(intersections, 2);
//        region.printRegion();
//
//        assertEquals(GRAPH_2_PER_2, fakeStandardOutput.toString());
//    }
//
//    @Test
//    public void dropNodeAccordingAddedStone() {
//
//        Intersection intersection1 = new Intersection(Position.in(1, 1), Stone.BLACK);
//        Intersection intersection2 = new Intersection(Position.in(1, 2), Stone.BLACK);
//        Intersection intersection3 = new Intersection(Position.in(2, 1), Stone.BLACK);
//        Intersection intersection4 = new Intersection(Position.in(2, 2), Stone.BLACK);
//
//        List<Intersection> intersections = new ArrayList<>();
//        intersections.add(intersection1);
//        intersections.add(intersection2);
//        intersections.add(intersection3);
//        intersections.add(intersection4);
//
//        Region expected_region = Region.getRegions();
//        expected_region.createGraph(intersections, 2);
//        expected_region.getGraph().removeVertex(intersection4);
//
//        Region region = Region.getRegions();
//        region.createGraph(intersections, 2);
//        region.removeVertex(intersection4);
//
//        assertEquals(expected_region, region);
//    }
//
//    @Test
//    public void provideConnectedComponents() {
//        List<Intersection> intersections = new ArrayList<>();
//        List<Intersection> intersectionsToDelete = new ArrayList<>();
//        List<Intersection> expected_region1 = new ArrayList<>();
//        List<Intersection> expected_region2 = new ArrayList<>();
//
//        // TODO: less hardcoded
//        for(int row = 1; row <= 3; row++){
//            for(int column = 1; column <= 3; column++){
//                Intersection intersection = Intersection.empty(Position.in(row, column));
//                intersections.add(intersection);
//                if(column == 2) {
//                    intersectionsToDelete.add(intersection);
//                }else if (column == 1){
//                    expected_region1.add(intersection);
//                }else{
//                    expected_region2.add(intersection);
//                }
//            }
//        }
//
//        Region region = Region.getRegions();
//        region.createGraph(intersections, 3);
//
//        region.removeVertex(intersectionsToDelete.get(0));
//        region.removeVertex(intersectionsToDelete.get(1));
//        region.removeVertex(intersectionsToDelete.get(2));
//
//        /*call Region::getConnectedComponents()*/
//        List<Set<Intersection>> actualRegions = region.getConnectedComponents();
//        boolean matchRegion1, matchRegion2;
//
//        // TODO: refactor to make the comparison more readable
//
//        /*compare first region with expected first region*/
//        matchRegion1 = actualRegions.get(0).containsAll(expected_region1) &&
//                expected_region1.containsAll(actualRegions.get(0));
//        /*compare second region with expected second region*/
//        matchRegion2 = actualRegions.get(1).containsAll(expected_region2) &&
//                expected_region2.containsAll(actualRegions.get(1));
//
//        assertTrue(matchRegion1 && matchRegion2);
//    }
//
//    @AfterAll
//    public static void resetStandardOut() {
//        System.setOut(STDOUT);
//    }
}