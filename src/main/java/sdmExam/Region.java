package sdmExam;

import org.jgrapht.Graph;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.util.List;
import java.util.function.Supplier;

import static java.lang.Math.sqrt;

public class Region {
    private static final Region region = new Region();
    private Graph<Intersection, DefaultEdge> graph  = new SimpleGraph<>(DefaultEdge.class);

    private Region(){}

    public static Region getRegion(){
        return region;
    }

    public Graph<Intersection, DefaultEdge> getGraph() {
        return this.graph;
    }

    public void createGraph(List<Intersection> emptyIntersections) {

        Supplier<Intersection> v_supplier = new Supplier<>() {
            private int index = 0;

            @Override
            public Intersection get() {
                return emptyIntersections.get(index++);
            }
        };

        int board_size = (int) sqrt(emptyIntersections.size());
        graph = new SimpleGraph<>(v_supplier, SupplierUtil.createDefaultEdgeSupplier(), false);

        GridGraphGenerator<Intersection, DefaultEdge> generator = new GridGraphGenerator<>(board_size, board_size);
        generator.generateGraph(graph, null);
    }
}