package sdmExam;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.BiconnectivityInspector;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.util.SupplierUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import static java.lang.Math.sqrt;

public class Region {
    private static final Region region = new Region();
    private Graph<Intersection, DefaultEdge> graph;

    private Region(){}

    public static Region getRegion(){
        return region;
    }

    public Graph<Intersection, DefaultEdge> getGraph() {
        return this.graph;
    }

    public void createGraph(List<Intersection> emptyIntersections) {

        Supplier<Intersection> vertexSupplier = new Supplier<>() {
            private int index = 0;

            @Override
            public Intersection get() {
                return emptyIntersections.get(index++);
            }
        };

        int boardSize = (int) sqrt(emptyIntersections.size());
        graph = new SimpleGraph<>(vertexSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);

        new GridGraphGenerator<Intersection, DefaultEdge>(boardSize, boardSize).generateGraph(graph, null);
    }

    public void removeVertex(Intersection intersection) {
        graph.removeVertex(intersection);
    }

    public List<List<Intersection>> getConnectedComponents() {
        BiconnectivityInspector<Intersection, DefaultEdge> biconnectivityInspector = new BiconnectivityInspector<>(graph);
        List<Intersection> emptyIntersections = new ArrayList<>(graph.vertexSet());
        List<List<Intersection>> regions = new ArrayList<>();

        // TODO: try to use BiconnectivityInspector::getConnectedComponents
        emptyIntersections.forEach(i -> {
            if(regions.stream().noneMatch(j -> j.contains(i))) {
                List<Intersection> region = new ArrayList<>(biconnectivityInspector.getConnectedComponent(i).vertexSet());
                regions.add(region);
            }
        });

        return regions;
    }

    public void printRegion() {

        Iterator<Intersection> iter = new DepthFirstIterator<>(this.graph);
        while (iter.hasNext()) {
            Intersection vertex = iter.next();
            System.out.println(
                            "Vertex " + vertex.getPosition().toString() + " is connected to: "
                                    + this.graph.edgesOf(vertex).toString());
        }
    }
}