import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import sdmExam.Intersection;
import sdmExam.Position;
import sdmExam.Region;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegionShould {

    @Test
    public void initialize2x2GridRegion(){

        List<Intersection> intersections = new ArrayList<>();
        for (int row = 1; row <= 2; row++) {
            for (int column = 1; column <= 2; column++) {
                intersections.add(Intersection.empty(Position.in(row, column)));
            }
        }

        Region expected_region = Region.getRegion();
        Graph<Intersection, DefaultEdge> graph = expected_region.getGraph();

        graph.addVertex(intersections.get(0));
        graph.addVertex(intersections.get(1));
        graph.addVertex(intersections.get(2));
        graph.addVertex(intersections.get(3));

        graph.addEdge(intersections.get(0), intersections.get(1));
        graph.addEdge(intersections.get(0), intersections.get(2));

        graph.addEdge(intersections.get(1), intersections.get(0));
        graph.addEdge(intersections.get(1), intersections.get(3));

        graph.addEdge(intersections.get(2), intersections.get(0));
        graph.addEdge(intersections.get(2), intersections.get(3));

        graph.addEdge(intersections.get(3), intersections.get(1));
        graph.addEdge(intersections.get(3), intersections.get(2));

        Region region = Region.getRegion();
        region.createGraph(intersections);

        assertEquals(expected_region, region);
    }

    private static final PrintStream STDOUT = System.out;
    private static final String GRAPH_2_PER_2 = """
       Vertex Position{row=1, column=1} is connected to: [(Position{row=1, column=1} : Position{row=1, column=2}), (Position{row=1, column=1} : Position{row=2, column=1})]
       Vertex Position{row=2, column=1} is connected to: [(Position{row=1, column=1} : Position{row=2, column=1}), (Position{row=2, column=1} : Position{row=2, column=2})]
       Vertex Position{row=2, column=2} is connected to: [(Position{row=1, column=2} : Position{row=2, column=2}), (Position{row=2, column=1} : Position{row=2, column=2})]
       Vertex Position{row=1, column=2} is connected to: [(Position{row=1, column=1} : Position{row=1, column=2}), (Position{row=1, column=2} : Position{row=2, column=2})]
       """;

    @Test
    void correctlyPrintRegion() {
        List<Intersection> intersections = new ArrayList<>();
        for (int row = 1; row <= 2; row++) {
            for (int column = 1; column <= 2; column++) {
                intersections.add(Intersection.empty(Position.in(row, column)));
            }
        }

        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        Region region = Region.getRegion();
        region.createGraph(intersections);
        region.printRegion();

        assertEquals(GRAPH_2_PER_2, fakeStandardOutput.toString());
    }

    @AfterAll
    public static void resetStandardOut() {
        System.setOut(STDOUT);
    }
}