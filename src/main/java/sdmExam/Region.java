package sdmExam;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class Region {
    private static final Region regions = new Region();
    private Graph<Intersection, DefaultEdge> graph;

    private Region(){}

    public static Region getRegions(){
        return regions;
    }

    public Graph<Intersection, DefaultEdge> getGraph() {
        return this.graph;
    }

    public void createGraph(List<Intersection> emptyIntersections, int boardSize) {

        Supplier<Intersection> vertexSupplier = new Supplier<>() {
            private int index = 0;

            @Override
            public Intersection get() {
                return emptyIntersections.get(index++);
            }
        };

        graph = new SimpleGraph<>(vertexSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);
        new GridGraphGenerator<Intersection, DefaultEdge>(boardSize, boardSize).generateGraph(graph, null);
    }

    public void removeVertex(Intersection intersection) {
        graph.removeVertex(intersection);
    }

    public List<Set<Intersection>> getConnectedComponents() {
        return new ConnectivityInspector<>(graph).connectedSets();
    }

//    public void printRegion() {
//
//        Iterator<Intersection> iter = new DepthFirstIterator<>(this.graph);
//        while (iter.hasNext()) {
//            Intersection vertex = iter.next();
//            System.out.println(
//                            "Vertex " + vertex.getPosition().toString() + " is connected to: "
//                                    + this.graph.edgesOf(vertex).toString());
//        }
//    }

    @Override
    public String toString() {
        return "Region{" +
                "graph=" + graph +
                '}';
    }
}