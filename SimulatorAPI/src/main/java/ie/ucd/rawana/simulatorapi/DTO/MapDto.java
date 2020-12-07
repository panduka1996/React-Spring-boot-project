package ie.ucd.rawana.simulatorapi.DTO;

import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.Set;

public class MapDto {

    private Set<String> vertices;

    private List<String> edges;

    public MapDto() {
    }

    public List<String> getEdges() {
        return edges;
    }

    public void setEdges(List<String> edges) {
        this.edges = edges;
    }

    public Set<String> getVertices() {
        return vertices;
    }

    public void setVertices(Set<String> vertices) {
        this.vertices = vertices;
    }
}
