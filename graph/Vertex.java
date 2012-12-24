package graph;

/** A vertex in a graph.
 *  @author Andrea Wu
 */
public class Vertex<VLabel> {

    /** The label on this vertex. */
    private final VLabel _label;
    /** The previous vertex in the path. */
    private Vertex<VLabel> _back;

    /** A new vertex with LABEL as the value of getLabel(). */
    protected Vertex(VLabel label) {
        _label = label;
    }

    /** Returns the label on this vertex. */
    public VLabel getLabel() {
        return _label;
    }

    /** Keeps track of which vertex V THIS came from in computing
     * shortest path. */
    public void back(Vertex<VLabel> v) {
        _back = v;
    }

    /** Returns the preceeding vertex from V in computing
     * shortest path. */
    public Vertex<VLabel> getBack() {
        return _back;
    }

    @Override
    public String toString() {
        return String.valueOf(_label);
    }

}
